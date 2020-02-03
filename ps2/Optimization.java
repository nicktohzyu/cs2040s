/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };



    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @param i an index of the array between 0 to dataArray.length-2
     * @return whether dataArray[i]<dataArray[i+1]
     */
    static boolean isIncreasing(int[] dataArray, int i){
        return dataArray[i]<dataArray[i+1];
    }

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        if(dataArray.length<1){
            throw new IllegalArgumentException("dataArray is empty");
        } else if(dataArray.length==1){
            return dataArray[0];
        }
        if(dataArray[0] > dataArray[1] || dataArray[dataArray.length-2] < dataArray[dataArray.length-1]){
            //array is either decreasing then increasing or sorted; only elements at the ends are possible maxima
            return Math.max(dataArray[0], dataArray[dataArray.length-1]);
        }

        //binary search to find the point where the array switches from increasing to decreasing. this must be the maxima
        int low = 0;
        int high = dataArray.length-2; //-2 because of how isIncreasing is implemented
        while (low < high)
        {
            int middle = low + (high - low) / 2;
            if (isIncreasing(dataArray, middle))
            {
                low = middle + 1;
            }
            else{
                high = middle;
            }
        }
        return dataArray[low];
    }
    /*
    Time Complexity: O(log(n))
    Optional:
    1. When the array is empty we throw an IllegalArgumentException with message "dataArray is empty"
    2. There is no sub-linear solution. Consider the case where all but one element (the maximum) are the same
    3. There is no sub-linear solution. Consider the case {1,2,..,k,M,k+1,..n) where M is the maximum
    4. O(log(n)) is possible. To check if a the array is increasing at a point we check 4 consecutive elements.

     */

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
