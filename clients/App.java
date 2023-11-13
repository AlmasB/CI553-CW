package clients;



import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.backDoor.BackDoorViewFX;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierViewFX;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerViewFX;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import middle.LocalMiddleFactory;
import javafx.geometry.*;
import middle.MiddleFactory;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
    	 MiddleFactory mlf = new LocalMiddleFactory();
         primaryStage.setTitle("Meow");

         GridPane root = new GridPane();
         root.setHgap(5);
         root.setVgap(5);
         root.setPadding(new Insets(0, 5, 0, 5));
        // root.setStyle("-fx-background-color: #F8C8DC");
         
         GridPane groupCustomer = new GridPane();
         groupCustomer.setHgap(5);
         groupCustomer.setVgap(5);
         groupCustomer.setPadding(new Insets(0, 5, 0, 5));
         groupCustomer.setStyle("-fx-background-color: #F8C8DC");
         
         
         GridPane groupEmployee = new GridPane();
         groupEmployee.setHgap(5);
         groupEmployee.setVgap(5);
         groupEmployee.setPadding(new Insets(0, 5, 0, 5));
         groupEmployee.setStyle("-fx-background-color: #F8C8DC");
         
         Scene mainScene = new Scene(root, 1000, 750);
       
         Scene customerScene = new Scene(groupCustomer,1000,750);
        
         Scene employeeScene = new Scene(groupEmployee, 1000, 750);
         groupEmployee.setStyle("-fx-background-color: #F8C8DC");
         
         HBox changeSceneFromMain = new HBox();
         Button b1 = new Button();
         Button b2 = new Button();
         changeSceneFromMain.getChildren().add(b1);
         changeSceneFromMain.getChildren().add(b2);
         b2.setText("Customer View");
         b1.setText("Employee View");
         b1.setOnAction(e -> primaryStage.setScene(employeeScene));
         b2.setOnAction(e -> primaryStage.setScene(customerScene));
         root.add(changeSceneFromMain, 0, 0);
         
        
         
        
         BackDoorModel backDoorModel = new BackDoorModel(mlf);
         BackDoorViewFX backDoorView = new BackDoorViewFX(mlf);
         BackDoorController backDoorController = new BackDoorController(backDoorModel, backDoorView);
         backDoorView.setController(backDoorController);
         backDoorModel.addObserver( backDoorView );
         VBox backDoorContent = new VBox();
         backDoorContent.getChildren().add(createExistingContent());
         backDoorContent.getChildren().add(backDoorView.getRoot());
         groupEmployee.add(backDoorView.getRoot(),0,1);
         
         CashierModel cashierModel = new CashierModel(mlf);
         CashierViewFX cashierView = new CashierViewFX(mlf);
         CashierController cashierController = new CashierController(cashierModel, cashierView);
         cashierView.setController(cashierController);
         cashierModel.addObserver( cashierView );
         VBox cashierContent = new VBox();
         cashierContent.getChildren().add(new Pane());
         cashierContent.getChildren().add(cashierView.getRoot());
         cashierContent.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
         groupEmployee.add(cashierView.getRoot(), 1, 1);
         
         CustomerModel customerModel = new CustomerModel(mlf);
         CustomerViewFX customerView = new CustomerViewFX(mlf);
         CustomerController customerController = new CustomerController(customerModel, customerView);
         customerView.setController(customerController);
         customerModel.addObserver( customerView );
         VBox customerContent = new VBox();
         customerContent.getChildren().add(createExistingContent());
         customerContent.getChildren().add(customerView.getRoot());
         customerContent.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
         groupCustomer.add(customerView.getRoot(), 0, 2);
         
     

         primaryStage.setScene(mainScene);
         primaryStage.show();
    }
    
    private Pane createExistingContent() {
        Pane existingContent = new Pane();
        return existingContent;
    }
    
    

    public static void main(String[] args) {
        launch(args);
    }
}



