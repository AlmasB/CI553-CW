package clients.shopDisplay;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.OrderProcessing;



// File is complete but not optimal
//  Will force update of display every 2 seconds
//  Could be clever & only ask for an update of the display 
//   if it really has changed

/**
 * Implements the Model of the display client
 * @author  Mike Smith University of Brighton; adjusted by - Masroor Rahman UOB
 * @version 2.0 - 2.1
 */

public class DisplayModel extends Observable
{
  private OrderProcessing theOrder = null;
  private ScheduledExecutorService serviceExec; //replaces the use of threads; to schedule updates at periodic intervals 
  private Map<String, List<Integer>> prevOrderState;

  /**
   * Set up initial connection to the order processing system
   * @param mf Factory to return an object to access the order processing system
   * Masroor Rahman - attempt to implement an update on the display if it has really changed 
   */
  
  public DisplayModel( MiddleFactory mf  )
  {
    try                                           // 
    {      
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      // Serious error in system (Should not occure)
      DEBUG.error("ModelOfDisplay: " + e.getMessage() );
      throw new IllegalStateException(e); 
    }
    
    prevOrderState = Collections.emptyMap(); // begin with empty order state
    serviceExec = Executors.newSingleThreadScheduledExecutor();
    serviceExec.scheduleWithFixedDelay(this::backgroundRun, 0, 2, TimeUnit.SECONDS); 
    
    //new Thread( () -> backgroundRun() ).start(); - thread no longer needed
    
  }
  
 /**
   * Run as a thread in background to continually update the display
   */
  																			//old backgroundRun method
//public void backgroundRun()
//{
//  while ( true )                               // Forever                    
//  {
//   try
//    {
//      Thread.sleep(2000);
//      DEBUG.trace( "ModelOfDisplay call view" );
//      setChanged(); notifyObservers();
//    }
//    catch ( InterruptedException e )
//    {
//      DEBUG.error( "%s\n%s\n",
//                  "ModelOfDisplay.run()",
//                  e.getMessage() );
//    }
//  }
//}
  
  
//  improved backgroundRun method
  
  public void backgroundRun()
  {
	try {
		Map<String, List<Integer>> currOrderState = getOrderState(); //thread-safe, state sharing without distinct synchronisation
		if(!currOrderState.equals(prevOrderState)) {
			setChanged();
			notifyObservers(currOrderState);
			prevOrderState = currOrderState;
		}
	} catch (OrderException e) {
		handleOrderException(e);
	}
  }
  
  
 // Will be called by the viewOfDisplay
 //   when it is told that the view has changed
 public synchronized Map<String, List<Integer> > getOrderState()
       throws OrderException
 {
   return theOrder.getOrderState();
 }
 
 
 private void handleOrderException(OrderException e) {
	 //error handling, errors get logged
	 DEBUG.error("failure on obtaining order state: %s", e.getMessage());
 }
 
 //introduce shutdown method to terminate thread
 private void shutDownMethod() {
	 serviceExec.shutdown();
	 try {
		 if(!serviceExec.awaitTermination(1600, TimeUnit.MILLISECONDS)) {
			 serviceExec.shutdownNow();
		 }
		 //reduced catching interrupted exceptions and breaking loop
	 } catch (InterruptedException e){
		 Thread.currentThread().interrupt();
	 }
 }
 
 
}
