package sample.grid;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import sample.maze.CELL_TYPE;

import java.util.LinkedList;
import java.util.List;

public class Cell extends Rectangle {
    private final int rowNum;
    private final int squareNum;
    private boolean wasUsed = false;

    private CELL_TYPE state;

    public Cell(double size, double posX, double posY, int rowNum, int squareNum) {
        super(size, size);
        this.rowNum = rowNum;
        this.squareNum = squareNum;

        setState(CELL_TYPE.CELL_BLOCKED);
        setX(posX);
        setY(posY);

        this.setOnMouseClicked(event -> {
            System.out.println(this.toString());
        });
    }

    public void setState(CELL_TYPE state) {
        if (state == CELL_TYPE.CELL_BLOCKED) {
            Platform.runLater(() -> {
                setFill(Color.BLACK);
            });

        }

        if (state == CELL_TYPE.CELL_PASSAGE) {
            Platform.runLater(() -> {
                setFill(Color.WHITE);
            });
        }

        this.state = state;
    }

    //SquareGrid.getSquare(4, 4);
    public List<Cell> getFrontiers() {
        List<Cell> cells = new LinkedList<>();
        Cell back = SquareGrid.getSquare(rowNum - 2, squareNum);

        if (back != null && back.state == CELL_TYPE.CELL_BLOCKED) {
            cells.add(back);
        }

        Cell front = SquareGrid.getSquare(rowNum + 2, squareNum);

        if (front != null && front.state == CELL_TYPE.CELL_BLOCKED) {
            cells.add(front);
        }

        Cell right = SquareGrid.getSquare(rowNum, squareNum + 2);

        if (right != null && right.state == CELL_TYPE.CELL_BLOCKED) {
            cells.add(right);
        }

        Cell left = SquareGrid.getSquare(rowNum, squareNum - 2);

        if (left != null && left.state == CELL_TYPE.CELL_BLOCKED) {
            cells.add(left);
        }

        return cells;
    }

    public List<Cell> getPaths() {
        List<Cell> cells = new LinkedList<>();

        Cell back = SquareGrid.getSquare(rowNum - 2, squareNum);

        if (back != null && back.state == CELL_TYPE.CELL_PASSAGE) {
            cells.add(back);
        }

        Cell front = SquareGrid.getSquare(rowNum + 2, squareNum);

        if (front != null && front.state == CELL_TYPE.CELL_PASSAGE) {
            cells.add(front);
        }

        Cell right = SquareGrid.getSquare(rowNum, squareNum + 2);

        if (right != null && right.state == CELL_TYPE.CELL_PASSAGE) {
            cells.add(right);
        }

        Cell left = SquareGrid.getSquare(rowNum, squareNum - 2);

        if (left != null && left.state == CELL_TYPE.CELL_PASSAGE) {
            cells.add(left);
        }

        return cells;
    }

    public Cell getNorth() {
        return SquareGrid.getSquare(rowNum - 1, squareNum);
    }

    public Cell getNorthEast() {
        return SquareGrid.getSquare(rowNum - 1, squareNum + 1);
    }

    public Cell getEast() {
        return SquareGrid.getSquare(rowNum, squareNum + 1);
    }

    public Cell getSouthEast() {
        return SquareGrid.getSquare(rowNum + 1, squareNum + 1);
    }

    public Cell getSouth() {
        return SquareGrid.getSquare(rowNum + 1, squareNum);
    }

    public Cell getSouthWest() {
        return SquareGrid.getSquare(rowNum + 1, squareNum - 1);
    }

    public Cell getWest() {
        return SquareGrid.getSquare(rowNum, squareNum - 1);
    }

    public Cell getNorthWest() {
        return SquareGrid.getSquare(rowNum - 1, squareNum - 1);
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getSquareNum() {
        return squareNum;
    }

    public boolean isUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }

    public CELL_TYPE getState() {
        return state;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rectangle[");

        String id = getId();
        if (id != null) {
            sb.append("id=").append(id).append(", ");
        }

        sb.append("Row=").append(getRowNum());
        sb.append(", Square=").append(getSquareNum());
        sb.append(", State=").append(getState());
        sb.append(", x=").append(getX());
        sb.append(", y=").append(getY());
        sb.append(", width=").append(getWidth());
        sb.append(", height=").append(getHeight());

        Paint color = getFill();
        sb.append(", fill=").append(color.equals(Color.BLACK) ? "Black" : color.equals(Color.WHITE) ? "White" : color.toString());

        Paint stroke = getStroke();
        if (stroke != null) {
            sb.append(", stroke=").append(stroke);
            sb.append(", strokeWidth=").append(getStrokeWidth());
        }

        return sb.append("]").toString();
    }
}
