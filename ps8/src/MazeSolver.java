import java.util.ArrayDeque;

public class MazeSolver implements IMazeSolver {
    private Maze maze;
    private boolean[][][] addedToQueue;
    private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
    private static final int[][] ddir = new int[][]{
            {-1, 0}, // North
            {1, 0},  // South
            {0, 1},  // East
            {0, -1}  // West
    };
    ArrayDeque<Point> queue;
    int[] numReachableCount;

    static class Point {
        int x, y, p;

        Point(int x, int y, int p) {
            this.x = x;
            this.y = y;
            this.p = p;
        }
    }

    public MazeSolver() {
        maze = null;
    }

    @Override
    public void initialize(Maze maze) {
        this.maze = maze;
        addedToQueue = new boolean[maze.getRows()][maze.getColumns()][];
        numReachableCount = new int[maze.getRows() * maze.getColumns()];
        queue = new ArrayDeque<>();
    }

    @Override
    public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
        return pathSearch(startRow, startCol, endRow, endCol, 0);
    }

    public Integer pathSearch(int startRow, int startCol, int endRow, int endCol, int superpowers) throws Exception {
        if (maze == null) {
            throw new Exception("Oh no! You cannot call me without initializing the maze!");
        }

        if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
                endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
            throw new IllegalArgumentException("Invalid start/end coordinate");
        }

        addedToQueue = new boolean[maze.getRows()][maze.getColumns()][];
        numReachableCount = new int[maze.getRows() * maze.getColumns()];
        // set all visited flag to false
        // before we begin our search
        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                maze.getRoom(i, j).onPath = false;
                addedToQueue[i][j] = new boolean[superpowers + 1];
                maze.getRoom(i, j).previous = new Room[superpowers + 1];
                maze.getRoom(i, j).distance = new Integer[superpowers + 1];
            }
        }
        maze.getRoom(startRow, startCol).distance[superpowers] = 0;
        queue.add(new Point(startRow, startCol, superpowers));
        addedToQueue[startRow][startCol][superpowers] = true;
        while (!queue.isEmpty()) {
            explore(queue.poll());
        }

        maze.getRoom(endRow, endCol).drawPath();
        //compute reachable
        for (int i = 0; i < maze.getRows(); ++i) {
            for (int j = 0; j < maze.getColumns(); ++j) {
                Integer d = maze.getRoom(i, j).minDist();
                if(d != null){
                    numReachableCount[d]++;
                }
            }
        }
        return maze.getRoom(endRow, endCol).minDist();
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

    private boolean canJump(int row, int col, int dir) {
        // not needed since our maze has a surrounding block of wall
        // but Joe the Average Coder is a defensive coder!
        if (row + ddir[dir][0] < 0 || row + ddir[dir][0] >= maze.getRows()) return false;
        if (col + ddir[dir][1] < 0 || col + ddir[dir][1] >= maze.getColumns()) return false;

        switch (dir) {
            case NORTH:
                return maze.getRoom(row, col).hasNorthWall();
            case SOUTH:
                return maze.getRoom(row, col).hasSouthWall();
            case EAST:
                return maze.getRoom(row, col).hasEastWall();
            case WEST:
                return maze.getRoom(row, col).hasWestWall();
        }
        return false;
    }

    private void explore(Point point) {
        Room room = maze.getRoom(point.x, point.y);
        for (int direction = 0; direction < 4; ++direction) {
            int nextX = point.x + ddir[direction][0], nextY = point.y + ddir[direction][1], p = point.p;
            if (canGo(point.x, point.y, direction)) { // can we go in that direction?
                // yes we can :)
                if (addedToQueue[nextX][nextY][p]) {
                    continue;
                }
                queue.add(new Point(nextX, nextY, p));
                addedToQueue[nextX][nextY][p] = true;
                Room nextRoom = maze.getRoom(nextX, nextY);
                nextRoom.distance[p] = room.distance[p] + 1;
                nextRoom.previous[p] = room;
            } else if (point.p > 0 && canJump(point.x, point.y, direction)) {
                //no we can't but we sorta can :|
                if (addedToQueue[nextX][nextY][p-1]) {
                    continue;
                }
                queue.add(new Point(nextX, nextY, p-1));
                addedToQueue[nextX][nextY][p-1] = true;
                Room nextRoom = maze.getRoom(nextX, nextY);
                nextRoom.distance[p-1] = room.distance[p] + 1;
                nextRoom.previous[p-1] = room;
            }
        }
    }

    @Override
    public Integer numReachable(int k) throws Exception {
        return k >= numReachableCount.length ? 0 :numReachableCount[k];
    }

    public static void main(String[] args) {
        try {
            Maze maze = Maze.readMaze("maze-sample.txt");
            IMazeSolver solver = new MazeSolver();
            solver.initialize(maze);

            System.out.println(solver.pathSearch(0, 0, 2, 2));
            MazePrinter.printMaze(maze);
            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }
            System.out.println(solver.pathSearch(0, 0, 3, 3));
            MazePrinter.printMaze(maze);
            for (int i = 0; i <= 9; ++i) {
                System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
