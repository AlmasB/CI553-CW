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
import clients.warehousePick.PickController;
import clients.warehousePick.PickModel;
import clients.warehousePick.PickViewFX;
import clients.collection.*;
import clients.shopDisplay.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
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
		primaryStage.setResizable(false);

		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		root.setPadding(new Insets(0, 5, 0, 5));

		GridPane groupCustomer = new GridPane();
		groupCustomer.setHgap(5);
		groupCustomer.setVgap(5);
		groupCustomer.setPadding(new Insets(0, 5, 0, 5));
		

		GridPane groupEmployee = new GridPane();
		groupEmployee.setHgap(5);
		groupEmployee.setVgap(5);
		groupEmployee.setPadding(new Insets(0, 5, 0, 5));

		Scene mainScene = new Scene(root, GRID_WIDTH, GIRD_HEIGHT);

		Scene customerScene = new Scene(groupCustomer, GRID_WIDTH, GIRD_HEIGHT);

		Scene employeeScene = new Scene(groupEmployee, GRID_WIDTH, GIRD_HEIGHT);

		// Main view

		HBox changeSceneFromMain = new HBox();
		changeSceneFromMain.setPrefWidth(GRID_WIDTH);
		changeSceneFromMain.setAlignment(Pos.CENTER);
		changeSceneFromMain.getStyleClass().add("navigation-button");
		Button changeToCustomerView = new Button();
		Button changeToEmployeeView = new Button();
		Button stayOnMain = new Button();
		stayOnMain.getStyleClass().add("current-button");
		changeSceneFromMain.getChildren().add(changeToCustomerView);
		changeSceneFromMain.getChildren().add(stayOnMain);
		changeSceneFromMain.getChildren().add(changeToEmployeeView);
		
		changeSceneFromMain.setSpacing(10);
		changeToCustomerView.setText("Customer View");
		changeToEmployeeView.setText("Employee View");
		stayOnMain.setText("Home");
		changeToEmployeeView.setOnAction(e -> primaryStage.setScene(employeeScene));
		changeToCustomerView.setOnAction(e -> primaryStage.setScene(customerScene));
		root.getStylesheets().add(App.class.getResource("/clients/style.css").toExternalForm());
		root.add(changeSceneFromMain, 0, 0);

		// Main view end

		// Employee view

		// Models

		BackDoorModel backDoorModel = new BackDoorModel(mlf);
		BackDoorViewFX backDoorView = new BackDoorViewFX(mlf);
		BackDoorController backDoorController = new BackDoorController(backDoorModel, backDoorView);
		backDoorView.setController(backDoorController);
		backDoorModel.addObserver(backDoorView);
		VBox backDoorContent = new VBox();
		backDoorContent.getChildren().add(createExistingContent());
		backDoorContent.getChildren().add(backDoorView.getRoot());
		// groupEmployee.add(backDoorContent,2,1);

		CashierModel cashierModel = new CashierModel(mlf);
		CashierViewFX cashierView = new CashierViewFX(mlf);
		CashierController cashierController = new CashierController(cashierModel, cashierView);
		cashierView.setController(cashierController);
		cashierModel.addObserver(cashierView);
		VBox cashierContent = new VBox();
		cashierContent.getChildren().add(new Pane());
		cashierContent.getChildren().add(cashierView.getRoot());

		PickModel pickModel = new PickModel(mlf);
		PickViewFX pickView = new PickViewFX(mlf);
		PickController pickController = new PickController(pickModel, pickView);
		pickView.setController(pickController);
		pickModel.addObserver(pickView);
		VBox pickContent = new VBox();
		pickContent.getChildren().add(new Pane());
		pickContent.getChildren().add(pickView.getRoot());

		employeeScene.getStylesheets().add(App.class.getResource("/clients/style.css").toExternalForm());

		// Models end

		// Buttons start
		HBox rowOneEmployee = new HBox();
		rowOneEmployee.getChildren().addAll(cashierContent, backDoorContent, pickContent);

		groupEmployee.add(rowOneEmployee, 0, 1);
		HBox changeSceneFromEmployee = new HBox();
		// HBox.setMargin(changeSceneFromEmployee, new Insets(10,0,10,0));
		changeSceneFromEmployee.setPrefWidth(GRID_WIDTH);
		changeSceneFromEmployee.setAlignment(Pos.CENTER);
		changeSceneFromEmployee.getStyleClass().add("navigation-button");
		Button changeToCustomerView2 = new Button();
		Button changeToMainView = new Button();
		Button stayOnEmployee = new Button();
		stayOnEmployee.getStyleClass().add("current-button");
		changeSceneFromEmployee.getChildren().add(changeToCustomerView2);
		changeSceneFromEmployee.getChildren().add(changeToMainView);
		changeSceneFromEmployee.getChildren().add(stayOnEmployee);
		changeSceneFromEmployee.setSpacing(10);
		changeToCustomerView2.setText("Customer View");
		stayOnEmployee.setText("Employee View");
		changeToMainView.setText("Home");
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
		customerModel.addObserver(customerView);
		VBox customerContent = new VBox();
		customerContent.getChildren().add(createExistingContent());
		customerContent.getChildren().add(customerView.getRoot());
		
		CollectModel collectModel = new CollectModel(mlf);
		CollectViewFX collectView = new CollectViewFX(mlf);
		CollectController collectController = new CollectController(collectModel, collectView);
		collectView.setController(collectController);
		collectModel.addObserver(collectView);
		VBox collectContent = new VBox();
		collectContent.getChildren().add(createExistingContent());
		collectContent.getChildren().add(collectView.getRoot());
		
		DisplayModel displayModel = new DisplayModel(mlf);
		DisplayViewCustomerFX displayCustomerView = new DisplayViewCustomerFX(mlf);
		DisplayController displayController = new DisplayController(displayModel, displayCustomerView);
		displayCustomerView.setController(displayController);
		displayModel.addObserver(displayCustomerView);
		VBox displayContent = new VBox();
		displayContent.getChildren().add(createExistingContent());
		displayContent.getChildren().add(displayCustomerView.getRoot());
		
		HBox firstRowCustomer = new HBox();
		firstRowCustomer.getChildren().addAll(customerContent,collectContent,displayContent);
		
		
		groupCustomer.add(firstRowCustomer, 1, 2);
		// groupCustomer.add(cashierContent, 2, 2);

		// Styling HBOX
		HBox changeSceneFromCustomer = new HBox();
		changeSceneFromCustomer.setPrefWidth(GRID_WIDTH);
		changeSceneFromCustomer.setAlignment(Pos.CENTER);
		changeSceneFromCustomer.getStyleClass().add("navigation-button");
		customerScene.getStylesheets().add(App.class.getResource("/clients/style.css").toExternalForm());
		changeSceneFromCustomer.setAlignment(Pos.CENTER);
		Button changeToEmployeeView2 = new Button();
		Button changeToMainView2 = new Button();
		Button deadButton = new Button();
		deadButton.getStyleClass().add("current-button");
		changeSceneFromCustomer.setSpacing(10);
		changeSceneFromCustomer.getChildren().add(deadButton);
		changeSceneFromCustomer.getChildren().add(changeToMainView2);
		changeSceneFromCustomer.getChildren().add(changeToEmployeeView2);
		deadButton.setText("Customer View");
		changeToEmployeeView2.setText("Employee View");
		changeToMainView2.setText("Home");

		// Scene changer
		changeToMainView2.setOnAction(e -> primaryStage.setScene(mainScene));
		changeToEmployeeView2.setOnAction(e -> primaryStage.setScene(employeeScene));
		groupCustomer.add(changeSceneFromCustomer, 1, 0);

		// Customer view end
		// https://stackoverflow.com/questions/16977100/how-do-i-add-margin-to-a-javafx-element-using-css
// https://stackoverflow.com/questions/12153622/how-to-close-a-javafx-application-on-window-close
		primaryStage.setScene(mainScene);
		primaryStage.show();
		primaryStage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	private Pane createExistingContent() {
		Pane existingContent = new Pane();
		return existingContent;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
