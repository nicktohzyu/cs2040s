import static org.junit.Assert.*;

import org.junit.Test;

public class MarkovModelTest {

    @Test
    public void standardTest() {
        String text = "abababababababc";
        int order = 2;
        int seed = 100;
        String kgram = "ab";
        int expected = 7;

        MarkovModel testModel = new MarkovModel(order, seed);
        testModel.initializeText(text);
        int freq = testModel.getFrequency(kgram);
        System.out.println(freq);
        assertEquals(expected, freq);
    }


    @Test
    public void standardTest2() {
        String text = "qwertyqwerty";
        int order = 2;
        int seed = 100;
        String kgram = "qw";
        int expected = 2;

        MarkovModel testModel = new MarkovModel(order, seed);
        testModel.initializeText(text);
        int freq = testModel.getFrequency(kgram);

        assertEquals(expected, freq);
    }

    @Test
    public void standardTest3() {
        String text = "abcabcabcdabcas";
        int order = 1;
        int seed = 100;
        String kgram = "c";
        int expected = 3;
        char charAfter = 'a';

        MarkovModel testModel = new MarkovModel(order, seed);
        testModel.initializeText(text);
        int freq = testModel.getFrequency(kgram, charAfter);

        assertEquals(expected, freq);
    }

    @Test
    public void standardTest4() {
        String text = "abcabcabcdabcas";
        int order = 2;
        int seed = 100;
        String kgram = "ab";
        int expected = 4;
        char charAfter = 'c';

        MarkovModel testModel = new MarkovModel(order, seed);
        testModel.initializeText(text);
        int freq = testModel.getFrequency(kgram, charAfter);

        assertEquals(expected, freq);
    }

    @Test
    public void wordsTest() {
        String text = "Once when a Lion was asleep a little Mouse began running up and down upon him; this soon wakened the Lion, who placed his huge paw upon him, and opened his big jaws to swallow him";
        int order = 2;
        int seed = 100;

        MarkovModel testModel = new MarkovModel(order, seed);
        testModel.initializeTextWords(text, 2);
    }
}
