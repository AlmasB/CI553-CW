package clients;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a root layout (StackPane)
        StackPane root = new StackPane();

        // Create a scene
        Scene scene = new Scene(root, 950, 675);

        // Apply the CSS file to the scene
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Set the title of the window
        primaryStage.setTitle("App");

        // Disable window resizing
        primaryStage.setResizable(false);

        // Create a button
        Button button = new Button("Styled Button");
        button.getStyleClass().add("button"); // Apply the CSS class

        // Add the button to the root layout
        root.getChildren().add(button);

        // Set the scene for the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



