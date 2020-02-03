/**
 * class RunContest
 *
 * @author benleong
 * Description: Code that runs the 2 contests
 */
public class RunContest implements IMissionControl {

    public static final int MAX_COUNT = 9999;

    int[] bitmap; // Bitmap used to keep track of the spies. 0 is regular student, 1 is spy
    int k = 0; // Number of spies

    int totalMissions = 0;
    int totalCost = 0;

    /**
     * Sends a group of students on a mission and returns true if there's
     * a secret meeting, false otherwise.
     *
     * @param mission Student bitmap of size N, where N is the number
     *                of students. A '1' at index i indicates that student i is to be
     *                sent on this mission.
     * @return true if there's a secret meeting on this mission
     */
    @Override
    public boolean sendForMission(int[] mission) {
        if (mission.length != bitmap.length) {
            throw new RuntimeException("Invalid mission bitmap.");
        }
        if (totalMissions > MAX_COUNT) {
            throw new RuntimeException("Count exceeded!");
        }

        int spiesOnMission = 0;
        int studentsOnMission = 0;
        for (int i = 0; i < mission.length; i++) {
            if (mission[i] < 0 || mission[i] > 1) {
                throw new RuntimeException("Invalid mission bitmap.");
            }
            if (mission[i] == 1) {
                studentsOnMission += 1;
            }
            spiesOnMission += mission[i] * bitmap[i];
        }
        if (studentsOnMission < k) {
            throw new RuntimeException("Invalid mission - not enough students!");
        }

        totalMissions++;
        totalCost += studentsOnMission;
        return spiesOnMission == k;
    }

    /**
     * Tests if the given bitmap is the correct configuration (i.e. identifies
     * all the spies.
     */
    private boolean testCorrect(int[] testBitmap) {
        if (testBitmap.length != bitmap.length) {
            throw new RuntimeException("Invalid test bitmap.");
        }
        for (int i = 0; i < bitmap.length; i++) {
            if (bitmap[i] != testBitmap[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Runs the test and return the number of steps taken to find all the spies.
     *
     * @param f Algorithm to be tested
     * @return Number of steps required to find the spies
     */
    private int findSteps(IFindSpies f) {
        resetStatistics();
        int[] ans = f.findSpies(bitmap.length, k, this);
        if (!testCorrect(ans)) {
            throw new RuntimeException("Wrong answer!");
        }
        return totalMissions;
    }

    /**
     * Runs the test and return the cost to find all the spies.
     *
     * @param f Algorithm to be tested
     * @return Cost incurred to find the spies
     */
    private int findCost(IFindSpies f) {
        resetStatistics();
        int[] ans = f.findSpies(bitmap.length, k, this);
        if (!testCorrect(ans)) {
            throw new RuntimeException("Wrong answer!");
        }
        return totalCost;
    }

    private void resetStatistics() {
        totalMissions = 0;
        totalCost = 0;
    }

    private void setTestCase(int[] bitmap) {
        int count = 0;
        for (int i = 0; i < bitmap.length; i++) {
            if (bitmap[i] < 0 || bitmap[i] > 1) {
                throw new RuntimeException("Invalid bitmap.");
            }
            count += bitmap[i];
        }
        this.bitmap = bitmap;
        this.k = count;
    }

    /**
     * main procedure - for testing
     *
     * @param args
     */
    public static void main(String[] args) {

        // TODO: Feel free to change the test case here.
        //  bitmap is an array of n students where 1 means that the student is a spy
        int[] bitmap = {0, 0, 1, 1};

        RunContest contestRunner = new RunContest();
        contestRunner.setTestCase(bitmap);
        System.out.printf("Testing on %d students with %d spies.\n", contestRunner.bitmap.length, contestRunner.k);
        IFindSpies minimum = new FindSpyMinimumSteps();
        System.out.println("Total missions sent: " + contestRunner.findSteps(minimum));

        IFindSpies lowest = new FindSpyLowestCost();
        System.out.println("Total cost: $" + contestRunner.findCost(lowest));

    }

}
