///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

import java.util.Arrays;

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    private int size;
    private int tap;
    int[] register;
    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        this.size = size;
        this.tap = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override

    public void setSeed(int[] seed) {
        if (size != seed.length) {
            throw new RuntimeException("error: size invalid for seed");
        }
        for (int i = 0; i < size; i++) {
            if (seed[i] != 1 && seed[i] != 0) {
                throw new RuntimeException("Error: Use only 1's and 0's in the seed");
            }
        }
//        register = new int[size];
        register = seed;

//        for (int z = 0; z < size; z++)
//            register[size - z - 1] = seed[z];
//        System.out.println(Arrays.toString(register));
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {

        int ret = 0;
        if (register[size-1] != register[tap]) {
            ret = 1;
        }

        System.out.println("register: " + Arrays.toString(register) + " out: " + ret);
        for (int q = size - 1; q > 0; q--) {
            register[q] = register[q - 1];
        }
        register[0] = ret;

        return register[0];
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        int[] arr = new int[k];
        for (int y = 0; y < k; y++) {
            arr[y] = shift();
        }
            System.out.println(Arrays.toString(arr));

        return toBinary(arr);
    }

    /**
     * toBinary
     * @param array
     * @return integer
     * Description: Returns the integer representation for a binary int array.
     */
    private int toBinary(int[] array) {
        int v = 0;
        for (int t = 0; t < array.length; t++) {
            v = (v * 2) + array[t];
        }
        return v;
    }

//    public static void main(String[] args) {
//        ShiftRegister r = new ShiftRegister(9, 7);
//        int[] seed = {0,1,0,1,1,1,1,0,1};
//        r.setSeed(seed);
//        for (int i = 0; i < 10; i++) {
//            System.out.println(r.shift());
//        }
//    }
}
