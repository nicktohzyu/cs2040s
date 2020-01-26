public class HelloWorld {
    static int MysteryFunction(int argA, int argB) {
        int c = 1;
        int d = argA;
        int e = argB;
        while (e > 0) {
            if (2*(e/2) !=e) {
                //System.out.printf("entered \n");
                c = c*d;
            }
            d = d*d;
            e = e/2;
            //System.out.printf(c + " " + d + " " + e + "\n");
        }
        return c;
    }

    public static void main(String[] args) {
        int output = MysteryFunction(5, 5);
        System.out.printf("My name is Nicholas/Nick. My favourite data structure is union-find disjoint-set. I have some background in algorithms from competitive programming. \n");
        System.out.printf("The answer to part a is: " + output + ". The function is fast power.\n");

    }
}
