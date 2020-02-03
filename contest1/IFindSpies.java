/**
 * interface FindSpies
 *
 * @author benleong
 * Description: Interface for problems
 */
public interface IFindSpies {

    /**
     * Finds the spies amongst the students.
     *
     * @param N              Number of students, numbered from 0 to N-1
     * @param k              Number of spies
     * @param missionControl Interface from which missions can be sent
     * @return Student bitmap with all spies marked with `1` and all other
     * students marked with `0`
     */
    int[] findSpies(int N, int k, IMissionControl missionControl);
}
