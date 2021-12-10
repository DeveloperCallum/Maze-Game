package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.grid.SquareGrid;
import sample.maze.PrimsGeneration;

public class Main extends Application {
    int size = 820;

    @Override
    public void start(Stage stage) {
        System.out.println("Started");

        SquareGrid gridController = new SquareGrid(size, 41);
        gridController.GenerateGrid();
        Group root = new Group();

        new Thread(() -> {
            PrimsGeneration mazeProvider = new PrimsGeneration(gridController, 0, 0);
            mazeProvider.generateMaze(root);
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