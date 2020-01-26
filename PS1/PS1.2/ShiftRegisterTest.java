import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Arrays;

/**
 * ShiftRegisterTest
 * @author dcsslg
 * Description: set of tests for a shift register implementation
 */
public class ShiftRegisterTest {

    /**
     * getRegister returns a shiftregister to test
     * @param size
     * @param tap
     * @return a new shift register
     * Description: to test a shiftregister, update this function
     * to instantiate the shift register
     */
    ILFShiftRegister getRegister(int size, int tap){
        return new ShiftRegister(size, tap);
    }

    /**
     * Test shift with simple example
     */
    @Test
    public void testShift1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {1,1,0,0,0,1,1,1,1,0};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.shift());
        }
    }

    /**
     * Test generate with simple example
     */
    @Test
    public void testGenerate1() {
        ILFShiftRegister r = getRegister(9, 7);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {6,1,7,2,2,1,6,6,2,3};
        for (int i=0; i<10; i++){
            assertEquals("GenerateTest", expected[i], r.generate(3));
        }
    }

    /**
     * Test register of length 1
     */
    @Test
    public void testOneLength() {
        ILFShiftRegister r = getRegister(1, 0);
        int[] seed = {1};
        r.setSeed(seed);
        int[] expected = {0,0,0,0,0,0,0,0,0,0,};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.generate(3));
        }
    }

    /**
     * Test with erroneous seed.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSeedError() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {1,0,0,0,1,1,0};
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    } //should throw error if seed is not the same size as specified register


    //additional test cases

    @Test (expected = IllegalArgumentException.class)
    public void testSeedError2() {
        ILFShiftRegister r = getRegister(4, 1);
        int[] seed = {1,0,0};
        r.setSeed(seed);
        r.shift();
        r.generate(4);
    } //should throw error if seed is not the same size as specified register

    @Test
    public void testShift2() {
        ILFShiftRegister r = getRegister(9, 8);
        int[] seed = {0,1,0,1,1,1,1,0,1};
        r.setSeed(seed);
        int[] expected = {0,0,0,0,0,0,0,0,0,0};
        for (int i=0; i<10; i++){
            assertEquals(expected[i], r.shift());
        }
    } //when tap = size-1 the output will all be zero = MSB ^ MSB

    @Test (expected = IllegalArgumentException.class)
    public void testSizeError() {
        ILFShiftRegister r = getRegister(0, 0);
    } //throw error if seed is less than 1


    @Test (expected = IllegalArgumentException.class)
    public void testTapError() {
        ILFShiftRegister r = getRegister(4, 6);
    } //throw error if tap is not between 0 and (size-1) inclusive

//    @Test
//    public void testHashString() {
//        ILFShiftRegister r = getRegister(9, 8);
//        r.setSeedString("TheCowJumpedOverTheMoon");
//    }

//    @Test
//    public void testZeroOneBalance() {
//        ILFShiftRegister r = getRegister(15, 1);
//        r.setSeedString("TheCowJumpedOverTheMoo");
//        int iter = 10000, c0 = 0, c1 = 0;
//        while(iter-->0){
//            if(r.shift()==0) {
//                c0++;
//            }
//        }
//        System.out.print("0s : " + c0);
//        System.out.print("\n1s : " + (10000 - c0));
//    }

}
