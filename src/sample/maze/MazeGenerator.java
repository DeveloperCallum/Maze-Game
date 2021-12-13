package sample.maze;

public class MazeGenerator {
    private static MazeAlgorithm mazeAlgorithm = null;

    public MazeGenerator(MazeAlgorithm mazeAlgorithm) {
        setMazeAlgorithm(mazeAlgorithm);
    }

    public static MazeAlgorithm getGenerator() {
        return mazeAlgorithm;
    }

    public static void setMazeAlgorithm(MazeAlgorithm mazeAlgorithm) {
        synchronized (MazeGenerator.class) {
            if (MazeGenerator.mazeAlgorithm != null) {
                throw new RuntimeException("Maze Algorithm already set");
            }

                MazeGenerator.mazeAlgorithm = mazeAlgorithm;
        }
    }
}
