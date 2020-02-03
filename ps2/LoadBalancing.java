import java.util.Arrays;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean feasibleLoad(int[] jobSize, int queryLoad, int p) {
        int index = 0, sum = 0;
        while(p-->0){
            sum = 0;
            while(sum <= queryLoad){
                sum += jobSize[index];
                if(sum <= queryLoad){
                    index++;
                }
                if(index>jobSize.length-1){
                    return true;
                }
            }
        }
        return false;
    }
    //complexity: O(N), where N is the length of jobSize

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param prefixSum the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean betterFeasibleLoad(int[] prefixSum, int queryLoad, int p) {
        //binary search on prefixSum for the largest interval each processor can handle
        int index = 0;//"completed" tasks
        while(p-->0){
            index = Arrays.binarySearch(prefixSum, queryLoad+prefixSum[index]);
            if(index<0){
                index = -index -2;
            }
        }
        return index == prefixSum.length-1;
    }
    //complexity: O(p * log(N)), where N is the length of prefixSum

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSize the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */

    public static int findLoad(int[] jobSize, int p){
        return weaklyPolynomialFindLoad(jobSize, p);
    }
    public static int weaklyPolynomialFindLoad(int[] jobSize, int p) {
        if(p<1){
            throw new IllegalArgumentException("not possible with less than 1 processor");
        }
        // Prefix sum starting with 0, this step takes O(N), where N is the length of jobSize
        int[] prefixSum = new int[jobSize.length+1]; //prefix sum has size O(jobSize)
        prefixSum[0] = 0;
        for(int i = 1; i <= jobSize.length; i++){
            prefixSum[i] = prefixSum[i-1]+jobSize[i-1];
        }
        if(p==1){
            return prefixSum[prefixSum.length-1];
            //avoid edge case
        }
        //note that if the first processor takes k tasks in the optimal solution,
        //then the true feasible load falls between jobSize[k] and jobSize[k+1]
        //binary search for smallest k where betterFeasibleLoad(prefixSum, prefixSum[k], p) is true
        //this step takes O(log^2 N), where N is the length of jobSize
        int low = 0, high = prefixSum.length-1, middle=0;
        while (low < high)
        {
            middle = low + (high - low) / 2;
            if (betterFeasibleLoad(prefixSum, prefixSum[middle], p)){
                high = middle;
            }
            else{
                low = middle + 1;
            }
        }
        //middle will never be 0
        if(middle==prefixSum.length-1){
            //avoid edge case
            return prefixSum[prefixSum.length-1];
        }

        //finally, we binary search for the smallest achievable load
        //this step takes O(p * log(N) * log(max(jobSize))), where N is the length of jobSize
        low = prefixSum[high-1]+1; //lower bound for load
        high = prefixSum[high]; //upper bound for load
        while (low < high)
        {
            middle = low + (high - low) / 2;
            if (betterFeasibleLoad(prefixSum, middle, p)){
                high = middle;
            }
            else{
                low = middle + 1;
            }
        }
        return low;
    }
    // final complexity: O(N + log^2(N) + p * log(N) * log(max(jobSize))) = O(N + p * log(N) * log(max(jobSize)))

    public static int stronglyPolynomialFindLoad(int[] jobSize, int p) {
        if(p<1){
            throw new IllegalArgumentException("not possible with less than 1 processor");
        }
        // Prefix sum starting with 0, this step takes O(N), where N is the length of jobSize
        int[] prefixSum = new int[jobSize.length+1];
        prefixSum[0] = 0;
        for(int i = 1; i <= jobSize.length; i++){
            prefixSum[i] = prefixSum[i-1]+jobSize[i-1];
        }
        if(p==1){
            return prefixSum[prefixSum.length-1];
            //avoid edge case
        }
        //note that if the first processor takes k tasks in the optimal solution,
        //then the true feasible load falls between jobSize[k] and jobSize[k+1]
        //binary search for smallest k where betterFeasibleLoad(prefixSum, prefixSum[k], p) is true
        //this step takes O(log^2 N), where N is the length of jobSize
        int low = 0, high = prefixSum.length-1, middle=0;
        while (low < high)
        {
            middle = low + (high - low) / 2;
            if (betterFeasibleLoad(prefixSum, prefixSum[middle], p)){
                high = middle;
            }
            else{
                low = middle + 1;
            }
        }
        //high will never be 0
        if(high==prefixSum.length-1){
            //avoid edge case
            return prefixSum[prefixSum.length-1];
        }

        //finally, we use sliding window to only query relevant loads.
        //this step takes O(p * N * log(N)), where N is the length of jobSize
        int lbload = prefixSum[high-1]+1; //lower bound for load
        int ubload = prefixSum[high]; //upper bound for load
        int lindex=0, rindex=1, load;
        while (rindex < prefixSum.length && lbload!= ubload){
            load = prefixSum[rindex]-prefixSum[lindex];
            if(betterFeasibleLoad(prefixSum, load, p)){
                //load is feasible, shrink window
                ubload = min(ubload,load);
                lindex++;
            } else {
                lbload = max(lbload, load);
                rindex++;
            }
        }
        return ubload;
    }
    //final complexity: O(N + log^2(N) + N * p * log(N)) = O(p * N * log(N))

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };
    /*Optional:
    Sort tasks by size, then greedily assign starting with the largest tasks to the processor with least load


     */
    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
