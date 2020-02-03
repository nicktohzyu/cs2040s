public interface IMissionControl {
    /**
     * Sends a group of students on a mission and returns true if there's
     * a secret meeting, false otherwise.
     *
     * @param mission Student bitmap of size N, where N is the number
     *                of students. A '1' at index i indicates that student i is to be
     *                sent on this mission.
     * @return true if there's a secret meeting on this mission
     */
    boolean sendForMission(int[] mission);

}
