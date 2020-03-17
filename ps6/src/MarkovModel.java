import java.util.*;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
class Frequency {
	Integer[] charCount = new Integer[256];
	int kgramCount = 0;
	Frequency(){
		Arrays.fill(charCount, 0);
	}
}

class FrequencyWords {
	HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
	int kgramCount = 0;
}

public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	HashMap<String, Frequency> map;
	HashMap<String, FrequencyWords> mapWords;
	int order;
	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;
		map = new HashMap<String, Frequency>();
		mapWords = new HashMap<String, FrequencyWords>();
		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		int len = text.length();
		for(int i = order; i < len; i++){
			String kgram = text.substring(i-order, i);
			Character c = text.charAt(i);
			Frequency freq = map.get(kgram);
			if(freq == null){
				freq = new Frequency();
				map.put(kgram, freq);
			}
			freq.kgramCount++;
			freq.charCount[(int) c]++;
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		Frequency freq = map.get(kgram);
		if(freq == null) {
			return 0;
		} else{
			return freq.kgramCount;
		}
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		Frequency freq = map.get(kgram);
		if(freq == null) {
			return 0;
		} else{
			return freq.charCount[(int) c];
		}
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		Frequency freq = map.get(kgram);
		if(freq == null){
			return NOCHARACTER;
		}
		int random = generator.nextInt(freq.kgramCount);
		Integer[] array = freq.charCount;
		for(int i = 0; i<256; i++){
			if(array[i] > random){
				return (char) i;
			} else {
				random -= array[i];
			}
		}
		return 'a';
	}

	public String[] initializeTextWords(String text, int seedLength) {
		String[] words = text.split("\\s+");
		int len = words.length;
		for(int i = order; i < len; i++){
//			System.out.println(words[i]);
			String kgram = String.join(", ", Arrays.copyOfRange(words, i-order, i));
			String nextWord = words[i];
//			System.out.println(kgram + ":  " + nextWord);
			FrequencyWords freq = mapWords.get(kgram);
			if(freq == null){
				freq = new FrequencyWords();
				mapWords.put(kgram, freq);
			}
			freq.kgramCount++;
			Integer nextWordCount = freq.wordCount.get(nextWord);
			if(nextWordCount == null){
				freq.wordCount.put(nextWord, 1);
			} else {
				freq.wordCount.put(nextWord, nextWordCount+1);
			}
		}
		return Arrays.copyOfRange(words, 0, seedLength);
	}

	public String nextWord(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		FrequencyWords freq = mapWords.get(kgram);
		if(freq == null){
			return null;
		}
		int random = generator.nextInt(freq.kgramCount);
		Set<Map.Entry<String, Integer>> entrySet = freq.wordCount.entrySet();
		for(Map.Entry<String, Integer> e : entrySet){
			if(e.getValue() > random){
				return e.getKey();
			} else {
				random -= e.getValue();
			}
		}
		return "error";
	}
}