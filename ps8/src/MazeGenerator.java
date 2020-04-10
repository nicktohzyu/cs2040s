import java.util.Random;

public class MazeGenerator {

    private MazeGenerator() {
    }

    // TODO: Feel free to modify the method parameters.
    public static Maze generateMaze(int rows, int columns) {
        return generateMaze(rows, columns, 0.5);
    }

    public static Maze generateMaze(int rows, int columns, double density) {
        boolean[][] horizontalWalls = new boolean[rows + 1][columns + 1];
        boolean[][] verticalWalls = new boolean[rows + 1][columns + 1];
        //external edges should be wall
        for (int i = 0; i < rows + 1; i++) {
            horizontalWalls[i][0] = true;
            horizontalWalls[i][columns] = true;
            verticalWalls[0][i] = true;
            verticalWalls[columns][i] = true;
        }
        for (int i = 0; i < columns + 1; i++) {
            verticalWalls[0][i] = true;
            verticalWalls[rows][i] = true;
        }
        //randomize each inner wall
        //horizontal walls
        Random RNJesus = new Random();
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {//don't modify the outer walls
                horizontalWalls[i][j] = RNJesus.nextDouble() < density;
                verticalWalls[i][j] = RNJesus.nextDouble() < density;
            }
        }
        //vertical walls

        Room[][] rooms = new Room[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                rooms[i][j] = new Room(verticalWalls[i][j], verticalWalls[i + 1][j], horizontalWalls[i][j + 1], horizontalWalls[i][j]);
            }
        }
        return new Maze(rooms);
    }

    public static void main(String[] args) {
        Maze maze = MazeGenerator.generateMaze(7, 7);
        ImprovedMazePrinter.printMaze(maze);
    }
}
