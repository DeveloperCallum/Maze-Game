package sample.maze;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import sample.grid.CELL_TYPE;
import sample.grid.Cell;
import sample.grid.SquareGrid;

import java.util.List;
import java.util.Random;

//Responsible for generating the maze
public class PrimsGeneration implements MazeAlgorithm{
    private final SquareGrid squareGrid;
    private int x;
    private int y;
    private Group root;

    private Cell finalCell = null;

    public PrimsGeneration(Group root, double size, int x, int y) {
        this.squareGrid = new SquareGrid(size, 41);
        this.x = x;
        this.y = y;
        this.root = root;

        squareGrid.generateGrid();
        maxIterations = squareGrid.getSize() * squareGrid.getSize();
    }

    public void generateMaze() {
        for (List<Cell> column : squareGrid.getGrid()) {
            Group columnGroup = new Group();
            for (Cell square : column) {
                columnGroup.getChildren().add(square);
            }

            root.getChildren().add(columnGroup);
        }

        //Get the start square
        Cell start = squareGrid.getSquare(x, y);

        assert start != null;

        start.setState(CELL_TYPE.CELL_PASSAGE);

        Platform.runLater(() -> {
            start.setFill(Color.GREEN);
        });

        process(squareGrid.getFrontiers(start), 0);

        Platform.runLater(() -> {
            finalCell.setFill(Color.YELLOW);
        });

        System.out.println("Generation Completed");
    }

    int maxIterations;
    public void process(List<Cell> frontiers, int itr) {
        if (frontiers.isEmpty()) {
            return;
        }

        if (itr > maxIterations){
            return;
        }

        Random random = new Random();

        int index = random.nextInt(frontiers.size());
        Cell chosenFrontier = frontiers.get(index);

        if (!chosenFrontier.isUsed() && chosenFrontier.getState() != CELL_TYPE.CELL_PASSAGE) {
            finalCell = chosenFrontier;
            chosenFrontier.setState(CELL_TYPE.CELL_PASSAGE);

            Platform.runLater(() -> {
                chosenFrontier.setFill(Color.RED);
            });

            List<Cell> paths = squareGrid.getPaths(chosenFrontier);

            if (paths.isEmpty()) {
                chosenFrontier.setFill(Color.WHITE);
                return;
            }

            Cell chosenPath = paths.get(random.nextInt(paths.size()));

            int pX = chosenPath.getRowNum();
            int pY = chosenPath.getSquareNum();

            int fX = chosenFrontier.getRowNum();
            int fY = chosenFrontier.getSquareNum();

            Cell inbetween = null;

            if (pX < fX && pY == fY) {
                inbetween = squareGrid.getSouth(chosenPath);
            }

            if (pX == fX && pY < fY) {
                inbetween = squareGrid.getEast(chosenPath);
            }

            if (pX == fX && pY > fY) {
                inbetween = squareGrid.getWest(chosenPath);
            }

            if (pX > fX && pY == fY) {
                inbetween = squareGrid.getNorth(chosenPath);
            }

            if (inbetween != null) {
                inbetween.setState(CELL_TYPE.CELL_PASSAGE);
                paths.add(inbetween);
                paths.add(chosenPath);

                List<Cell> chosenPathFrontiers = squareGrid.getFrontiers(chosenFrontier);

                if (!chosenPathFrontiers.isEmpty()) {
                    frontiers.remove(chosenFrontier);
                    chosenFrontier.setWasUsed(true);

                    frontiers.addAll(chosenPathFrontiers);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                frontiers.remove(chosenFrontier);
            }
        }else{
            frontiers.remove(chosenFrontier);
        }

        Platform.runLater(() -> {
            chosenFrontier.setFill(Color.WHITE);
        });

        process(frontiers, ++itr);
    }
}
