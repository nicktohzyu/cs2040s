import java.util.Random;

/**
 * class NaiveSpyLowestCost
 *
 * @author benleong
 * Description: Naive algorithm that randomly picks k students to send on a mission
 */
public class NaiveSpyLowestCost implements IFindSpies {

    Random random = new Random();

    @Override
    public int[] findSpies(int N, int k, IMissionControl missionControl) {
        while (true) {
            int[] bitmap = new int[N];
            for (int i = 0; i < k; i++) {
                boolean found = false;
                while (!found) {
                    int rnd = random.nextInt(bitmap.length);
                    if (bitmap[rnd] == 0) {
                        bitmap[rnd] = 1;
                        found = true;
                    }
                }
            }
            if (missionControl.sendForMission(bitmap)) {
                return bitmap;
            }
        }
    }
}
