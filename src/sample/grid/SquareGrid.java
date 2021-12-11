package sample.grid;

import java.util.*;

public class SquareGrid {
    private final double size;
    private final int amount;
    protected Cell[][] grid;

    /**
     * Create the grid used for a maze.
     *
     * @param size   Total area of the scene.
     * @param amount Total amount of squares on the grid.
     */

    public SquareGrid(double size, int amount) {
        this.size = size;
        this.amount = amount;
    }

    public void generateGrid() {
        double recSize = size / amount;

        if (recSize % 1 != 0){
            throw new RuntimeException("Pixels per square must be a whole number!");
        }

        System.out.println("Amount of pixels used per squares: " + recSize);
        double currentX = 0;
        double currentY = 0;

        grid = new Cell[amount][amount];

        for (int row = 0; row < amount; row++){
            for (int square = 0; square < amount; square++){
                grid[row][square] = new Cell(recSize, currentX, currentY, row, square);

                currentX += recSize;
            }

            currentX = 0;
            currentY += recSize;
        }
    }

    public List<List<Cell>> getGrid() {
        List<List<Cell>> gridData = new LinkedList<>();

        for (int x = 0; x < grid.length; x++) {
            if (grid[x] == null) {
                continue;
            }

            List<Cell> rowData = new LinkedList<>(Arrays.asList(grid[x]));

            gridData.add(rowData); //Returns the rows at column 0
        }

        return gridData;
    }

    public List<Cell> getFrontiers(Cell origin) {
        List<Cell> cells = new LinkedList<>();
        Cell back = getSquare(origin.getRowNum() - 2, origin.getSquareNum());

        if (back != null && back.getState() == CELL_TYPE.CELL_BLOCKED) {
            cells.add(back);
        }

        Cell front = getSquare(origin.getRowNum() + 2, origin.getSquareNum());

        if (front != null && front.getState() == CELL_TYPE.CELL_BLOCKED) {
            cells.add(front);
        }

        Cell right = getSquare(origin.getRowNum(), origin.getSquareNum() + 2);

        if (right != null && right.getState() == CELL_TYPE.CELL_BLOCKED) {
            cells.add(right);
        }

        Cell left = getSquare(origin.getRowNum(), origin.getSquareNum() - 2);

        if (left != null && left.getState() == CELL_TYPE.CELL_BLOCKED) {
            cells.add(left);
        }

        return cells;
    }

    public List<Cell> getPaths(Cell origin) {
        List<Cell> cells = new LinkedList<>();

        Cell back = getSquare(origin.getRowNum() - 2, origin.getSquareNum());

        if (back != null && back.getState() == CELL_TYPE.CELL_PASSAGE) {
            cells.add(back);
        }

        Cell front = getSquare(origin.getRowNum() + 2, origin.getSquareNum());

        if (front != null && front.getState() == CELL_TYPE.CELL_PASSAGE) {
            cells.add(front);
        }

        Cell right = getSquare(origin.getRowNum(), origin.getSquareNum() + 2);

        if (right != null && right.getState() == CELL_TYPE.CELL_PASSAGE) {
            cells.add(right);
        }

        Cell left = getSquare(origin.getRowNum(), origin.getSquareNum() - 2);

        if (left != null && left.getState() == CELL_TYPE.CELL_PASSAGE) {
            cells.add(left);
        }

        return cells;
    }

    public Cell getNorth(Cell origin) {
        return getSquare(origin.getRowNum() - 1, origin.getSquareNum());
    }

    public Cell getNorthEast(Cell origin) {
        return getSquare(origin.getRowNum() - 1, origin.getSquareNum() + 1);
    }

    public Cell getEast(Cell origin) {
        return getSquare(origin.getRowNum(), origin.getSquareNum() + 1);
    }

    public Cell getSouthEast(Cell origin) {
        return getSquare(origin.getRowNum() + 1, origin.getSquareNum() + 1);
    }

    public Cell getSouth(Cell origin) {
        return getSquare(origin.getRowNum() + 1, origin.getSquareNum());
    }

    public Cell getSouthWest(Cell origin) {
        return getSquare(origin.getRowNum() + 1, origin.getSquareNum() - 1);
    }

    public Cell getWest(Cell origin) {
        return getSquare(origin.getRowNum(), origin.getSquareNum() - 1);
    }

    public Cell getNorthWest(Cell origin) {
        return getSquare(origin.getRowNum() - 1, origin.getSquareNum() - 1);
    }

    public Cell getSquare(int x, int y){
        if (x < 0 || y < 0 || x >= grid.length || y >= grid.length ){
            return null;
        }

        return grid[x][y];
    }

    public int getSize() {
        return grid.length;
    }
}
