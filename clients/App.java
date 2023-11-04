package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.backDoor.BackDoorViewFX;
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
         Scene mainScene = new Scene(root, 800, 600);

         // Create and add your BackDoor client components to a subcomponent of the main scene
         BackDoorModel model = new BackDoorModel(mlf);
         BackDoorViewFX view = new BackDoorViewFX(mlf);
         BackDoorController controller = new BackDoorController(model, view);
         view.setController(controller);
         
         VBox mainContent = new VBox();
         mainContent.getChildren().add(createExistingContent()); // Add your existing content
         mainContent.getChildren().add(view.getRoot());

         // Add the BackDoorView's root to the center of the main scene
         root.setLeft(view.getRoot());

         primaryStage.setScene(mainScene);
         primaryStage.show();
    }
    
    private Pane createExistingContent() {
        // Implement your existing content here and return it as a Pane
        Pane existingContent = new Pane();
        // Add your UI components to existingContent
        return existingContent;
    }

    public static void main(String[] args) {
        launch(args);
    }
}



