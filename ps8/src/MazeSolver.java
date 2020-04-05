import java.util.ArrayDeque;

public class MazeSolver implements IMazeSolver {

    private Maze maze;
    private boolean[][] addedToQueue;
    private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
    private int[][] ddir = new int[][]{
            {-1, 0}, // North
            {1, 0},  // South
            {0, 1},  // East
            {0, -1}  // West
    };
    ArrayDeque<Pair> queue = new ArrayDeque<>();
    int[] numReachableCount;

    static class Pair {
        int x, y;

        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public MazeSolver() {
        maze = null;
    }

    @Override
    public void initialize(Maze maze) {
        this.maze = maze;
        addedToQueue = new boolean[maze.getRows()][maze.getColumns()];
        numReachableCount = new int[maze.getRows() * maze.getColumns()];
    }

    @Override
    public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
        if (maze == null) {
            throw new Exception("Oh no! You cannot call me without initializing the maze!");
        }

        if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
                endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
            throw new IllegalArgumentException("Invalid start/end coordinate");
        }

        // set all visited flag to false
        // before we begin our search
        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                this.addedToQueue[i][j] = false;
                maze.getRoom(i, j).onPath = false;
            }
        }
		maze.getRoom(startRow, startCol).distance = 0;
        queue.add(new Pair(startRow, startCol));
        addedToQueue[startRow][startCol] = true;
        while (!queue.isEmpty()) {
            explore(queue.poll());
        }
        //draw path
//		return solve(startRow, startCol, 0);
		Room nextRoom = maze.getRoom(endRow, endCol);
        while(nextRoom != null){
        	nextRoom.onPath = true;
        	nextRoom = nextRoom.previous;
		}
        return maze.getRoom(endRow, endCol).distance;
    }


    private boolean canGo(int row, int col, int dir) {
        // not needed since our maze has a surrounding block of wall
        // but Joe the Average Coder is a defensive coder!
        if (row + ddir[dir][0] < 0 || row + ddir[dir][0] >= maze.getRows()) return false;
        if (col + ddir[dir][1] < 0 || col + ddir[dir][1] >= maze.getColumns()) return false;

        switch (dir) {
            case NORTH:
                return !maze.getRoom(row, col).hasNorthWall();
            case SOUTH:
                return !maze.getRoom(row, col).hasSouthWall();
            case EAST:
                return !maze.getRoom(row, col).hasEastWall();
            case WEST:
                return !maze.getRoom(row, col).hasWestWall();
        }

        return false;
    }

    private void explore(Pair pair) {
        Room room = maze.getRoom(pair.x, pair.y);
        numReachableCount[room.distance]++;
        for (int direction = 0; direction < 4; ++direction) {
            if (canGo(pair.x, pair.y, direction)) { // can we go in that direction?
                // yes we can :)
                int nextX = pair.x + ddir[direction][0], nextY = pair.y + ddir[direction][1];
                if (addedToQueue[nextX][nextY]) {
                    continue;
                }
                queue.add(new Pair(nextX, nextY));
                addedToQueue[nextX][nextY] = true;
                Room nextRoom = maze.getRoom(nextX, nextY);
                nextRoom.distance = room.distance + 1;
                nextRoom.previous = room;
            }
        }
    }

    @Override
    public Integer numReachable(int k) throws Exception {
        return numReachableCount[k];
    }

    public static void main(String[] args) {
        try {
            Maze maze = Maze.readMaze("maze-dense.txt");
            IMazeSolver solver = new MazeSolver();
            solver.initialize(maze);

            System.out.println(solver.pathSearch(0, 0, 2, 3));
            MazePrinter.printMaze(maze);
            ImprovedMazePrinter.printMaze(maze);

            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
