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
	
	private int GRID_WIDTH = 1000;
	private int GIRD_HEIGHT = 750;

    @Override
    public void start(Stage primaryStage) {
    	 MiddleFactory mlf = new LocalMiddleFactory();
         primaryStage.setTitle("Meow");

         GridPane root = new GridPane();
         root.setHgap(5);
         root.setVgap(5);
         root.setPadding(new Insets(0, 5, 0, 5));
      //   root.setStyle("-fx-background-color: #F8C8DC");
         
         GridPane groupCustomer = new GridPane();
         groupCustomer.setHgap(5);
         groupCustomer.setVgap(5);
         groupCustomer.setPadding(new Insets(0, 5, 0, 5));
      //   groupCustomer.setStyle("-fx-background-color: #F8C8DC");
         
         
         GridPane groupEmployee = new GridPane();
         groupEmployee.setHgap(5);
         groupEmployee.setVgap(5);
         groupEmployee.setPadding(new Insets(0, 5, 0, 5));
      //   groupEmployee.setStyle("-fx-background-color: #F8C8DC");
         
         Scene mainScene = new Scene(root, GRID_WIDTH, GIRD_HEIGHT);
      //   mainScene.getStylesheets().add("style.css");
       
         Scene customerScene = new Scene(groupCustomer,GRID_WIDTH,GIRD_HEIGHT);
        
         Scene employeeScene = new Scene(groupEmployee, GRID_WIDTH, GIRD_HEIGHT);
        // groupEmployee.setStyle("-fx-background-color: #F8C8DC");
         
         // Main view
         
         HBox changeSceneFromMain = new HBox();
         changeSceneFromMain.setAlignment(Pos.CENTER);
         Button changeToCustomerView = new Button();
         Button changeToEmployeeView = new Button();
         changeSceneFromMain.getChildren().add(changeToCustomerView);
         changeSceneFromMain.getChildren().add(changeToEmployeeView);
         changeToCustomerView.setText("Customer View");
         changeToEmployeeView.setText("Employee View");
         changeToEmployeeView.setOnAction(e -> primaryStage.setScene(employeeScene));
         changeToCustomerView.setOnAction(e -> primaryStage.setScene(customerScene));
         root.add(changeSceneFromMain, 0, 0);
         
         // Main view end
        
         
         // Employee view
         
         	// Models
         
         BackDoorModel backDoorModel = new BackDoorModel(mlf);
         BackDoorViewFX backDoorView = new BackDoorViewFX(mlf);
         BackDoorController backDoorController = new BackDoorController(backDoorModel, backDoorView);
         backDoorView.setController(backDoorController);
         backDoorModel.addObserver( backDoorView );
         VBox backDoorContent = new VBox();
         backDoorContent.getChildren().add(createExistingContent());
         backDoorContent.getChildren().add(backDoorView.getRoot());
         groupEmployee.add(backDoorView.getRoot(),2,1);
         
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
         	
         	// Models end
         
         	// Buttons start
         
         HBox changeSceneFromEmployee= new HBox();
         changeSceneFromEmployee.setAlignment(Pos.CENTER);
         Button changeToCustomerView2 = new Button();
         Button changeToMainView = new Button();
         changeSceneFromEmployee.getChildren().add(changeToCustomerView2);
         changeSceneFromEmployee.getChildren().add(changeToMainView);
         changeToCustomerView2.setText("Customer View");
         changeToMainView.setText("Main View");
         changeToMainView.setOnAction(e -> primaryStage.setScene(mainScene));
         changeToCustomerView2.setOnAction(e -> primaryStage.setScene(customerScene));
         groupEmployee.add(changeSceneFromEmployee, 0, 0);
         
         	// Buttons start
         
         // Employee view end
         
         // Customer view
         	// Populating and model
         CustomerModel customerModel = new CustomerModel(mlf);
         CustomerViewFX customerView = new CustomerViewFX(mlf);
         CustomerController customerController = new CustomerController(customerModel, customerView);
         customerView.setController(customerController);
         customerModel.addObserver( customerView );
         VBox customerContent = new VBox();
         customerContent.getChildren().add(createExistingContent());
         customerContent.getChildren().add(customerView.getRoot());
         groupCustomer.add(customerView.getRoot(), 0, 2);
         
         	// Styling HBOX
         HBox changeSceneFromCustomer= new HBox();
         changeSceneFromCustomer.setAlignment(Pos.CENTER);
         changeSceneFromCustomer.getStyleClass().add("navigation-button");
         customerScene.getStylesheets().add(App.class.getResource("/clients/style.css").toExternalForm());
         changeSceneFromCustomer.setAlignment(Pos.CENTER);
         Button changeToEmployeeView2 = new Button();
         Button changeToMainView2 = new Button();
         changeSceneFromCustomer.setSpacing(10);
         changeSceneFromCustomer.getChildren().add(changeToEmployeeView2);
         changeSceneFromCustomer.getChildren().add(changeToMainView2);
         changeToEmployeeView2.setText("Employee View");
         changeToMainView2.setText("Main View");
         
         
         	// Scene changer
         changeToMainView2.setOnAction(e -> primaryStage.setScene(mainScene));
         changeToEmployeeView2.setOnAction(e -> primaryStage.setScene(employeeScene));
         groupCustomer.add(changeSceneFromCustomer, 3,0);
         
         // Customer view end
         	//https://stackoverflow.com/questions/16977100/how-do-i-add-margin-to-a-javafx-element-using-css

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



