package sample.maze;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import sample.grid.Cell;
import sample.grid.SquareGrid;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class PrimsGeneration {
    private final SquareGrid gridController;
    private int x;
    private int y;
    private List<Cell> paths = new LinkedList<>();

    private Cell finalCell = null;

    public PrimsGeneration(SquareGrid squareGrid, int x, int y) {
        this.gridController = squareGrid;
        this.x = x;
        this.y = y;
    }

    public void generateMaze(Group root) {
        for (List<Cell> column : gridController.getGrid()) {
            Group columnGroup = new Group();
            for (Cell square : column) {
                columnGroup.getChildren().add(square);
            }

            root.getChildren().add(columnGroup);
        }

        //Get the start square
        Cell start = SquareGrid.getSquare(x, y);

        //Add the start square to the path
        paths.add(start);

        assert start != null;

        start.setState(CELL_TYPE.CELL_PASSAGE);

        Platform.runLater(() -> {
            start.setFill(Color.GREEN);
        });

        process(start.getFrontiers(), 0);

        Platform.runLater(() -> {
            finalCell.setFill(Color.YELLOW);
        });

        System.out.println("Generation Completed");
    }

    int maxIterations = SquareGrid.getSize() * SquareGrid.getSize();
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

            paths.add(chosenFrontier);

            List<Cell> paths = chosenFrontier.getPaths();

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
                inbetween = chosenPath.getSouth();
            }

            if (pX == fX && pY < fY) {
                inbetween = chosenPath.getEast();
            }

            if (pX == fX && pY > fY) {
                inbetween = chosenPath.getWest();
            }

            if (pX > fX && pY == fY) {
                inbetween = chosenPath.getNorth();
            }

            if (inbetween != null) {
                inbetween.setState(CELL_TYPE.CELL_PASSAGE);
                paths.add(inbetween);
                paths.add(chosenPath);

                List<Cell> chosenPathFrontiers = chosenFrontier.getFrontiers();

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
