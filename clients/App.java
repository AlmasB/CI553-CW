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

        
         BackDoorModel model = new BackDoorModel(mlf);
         BackDoorViewFX view = new BackDoorViewFX(mlf);
         BackDoorController controller = new BackDoorController(model, view);
         view.setController(controller);
         model.addObserver( view );
         
         VBox mainContent = new VBox();
         mainContent.getChildren().add(createExistingContent());
         mainContent.getChildren().add(view.getRoot());

         
         root.setLeft(view.getRoot());

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



