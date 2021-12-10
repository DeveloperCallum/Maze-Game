package sample.grid;

import java.util.*;

public class SquareGrid {
    private static double size;
    private static int amount;
    protected static Cell[][] grid;

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

    public void GenerateGrid() {
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

    public static Cell getSquare(int x, int y){
        if (x < 0 || y < 0 || x >= grid.length || y >= grid.length ){
            return null;
        }

        return SquareGrid.grid[x][y];
    }

    public static int getSize() {
        return grid.length;
    }
}
