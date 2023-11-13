package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.backDoor.BackDoorViewFX;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierViewFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
    	 MiddleFactory mlf = new LocalMiddleFactory();
         primaryStage.setTitle("Main JavaFX Application");

         BorderPane root = new BorderPane();
         root.setStyle("-fx-background-color: #F8C8DC");
         Scene mainScene = new Scene(root, 1000, 750);

        
         BackDoorModel backDoorModel = new BackDoorModel(mlf);
         BackDoorViewFX backDoorView = new BackDoorViewFX(mlf);
         BackDoorController backDoorController = new BackDoorController(backDoorModel, backDoorView);
         backDoorView.setController(backDoorController);
         backDoorModel.addObserver( backDoorView );
         VBox backDoorContent = new VBox();
         backDoorContent.getChildren().add(createExistingContent());
         backDoorContent.getChildren().add(backDoorView.getRoot());
         root.setTop(backDoorView.getRoot());
         
         CashierModel cashierModel = new CashierModel(mlf);
         CashierViewFX cashierView = new CashierViewFX(mlf);
         CashierController cashierController = new CashierController(cashierModel, cashierView);
         cashierView.setController(cashierController);
         cashierModel.addObserver( cashierView );
         VBox cashierContent = new VBox();
         cashierContent.getChildren().add(createExistingContent());
         cashierContent.getChildren().add(cashierView.getRoot());
         cashierContent.getStylesheets().add(App.class.getResource("style.css").toExternalForm());
         root.setBottom(cashierView.getRoot());
         
         
         

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



