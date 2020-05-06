/**
 * Represents a single room in the maze.
 */
public class Room { // modify to multidimensional for superpower
    private boolean westWall, eastWall, northWall, southWall;
    public boolean onPath;
    Integer[] distance;
    Room[] previous;

    void drawPath() {
        int p = -1;
        Integer min = Integer.MAX_VALUE;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != null && distance[i] < min) {
                min = distance[i];
                p = i;
            }
        }
        if (p >= 0){
			onPath = true;
        	if(previous[p] != null) {
				previous[p].drawPath();
			}
        }
    }

    Integer minDist() {
        Integer min = Integer.MAX_VALUE;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] != null && distance[i] < min) {
                min = distance[i];
            }
        }
        return min < Integer.MAX_VALUE ? min : null;
    }

    Room(boolean north, boolean south, boolean east, boolean west) {
        northWall = north;
        southWall = south;
        eastWall = east;
        westWall = west;

        onPath = false;
    }

    /**
     * @return true iff there is a wall to the west of the room
     */
    public boolean hasWestWall() {
        return westWall;
    }

    /**
     * @return true iff there is a wall to the east of the room
     */
    public boolean hasEastWall() {
        return eastWall;
    }

    /**
     * @return true iff there is a wall to the north of the room
     */
    public boolean hasNorthWall() {
        return northWall;
    }

    /**
     * @return true iff there is a wall to the south of the room
     */
    public boolean hasSouthWall() {
        return southWall;
    }
}
