package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.grid.SquareGrid;
import sample.maze.MazeGenerator;
import sample.maze.PrimsGeneration;

public class Main extends Application {
    int size = 820;

    @Override
    public void start(Stage stage) {
        System.out.println("Started");

        Group root = new Group();

        MazeGenerator.setMazeAlgorithm(new PrimsGeneration(root, size,0, 0));

        //Create a new thread so that they can process asynchronously.
        new Thread(() -> {
            MazeGenerator.getGenerator().generateMaze();
        }).start();

        //Creating a scene object
        Scene scene = new Scene(root, size, size);
        scene.setFill(Color.AQUA);

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}