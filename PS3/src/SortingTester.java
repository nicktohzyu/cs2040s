import java.security.Key;
import java.util.Random;

public class SortingTester {

	public static boolean checkSort(ISort sorter, int size){
		KeyValuePair[] toSort = new KeyValuePair[size];
		for (int i = 0; i < size; i++) {
			Random random = new Random();
			int k = random.nextInt();
			int v = random.nextInt();
			toSort[i] = new KeyValuePair(k, v);
		}

		sorter.sort(toSort);

		for (int i = 0; i < size-1; i++) {
			if (toSort[i].compareTo(toSort[i + 1]) == 1) {
				return false;
			}
		}
		return true;
	}

	public static boolean isStable(ISort sorter, int size){
		KeyValuePair[] toSort = new KeyValuePair[size];
		for (int i = 0; i < size; i++) {
			if (i % 2 == 0) {
				int k = 100;
				toSort[i] = new KeyValuePair(k, i);
			} else {
				Random random = new Random();
				int k1 = random.nextInt(101);
				int k2 = random.nextInt(900) + 101;
				while (i < size/2) {
					toSort[i] = new KeyValuePair(k1, i);
				} while (i > size/2) {
					toSort[i] = new KeyValuePair(k2, i);
				}

			}
		}
		if (checkSort(sorter, size)) {
			sorter.sort(toSort);
			for (int i = 0; i < size; i++) {
				if (toSort[i].getKey() == toSort[i + 1].getKey()) {
					return toSort[i].getValue() < toSort[i + 1].getValue();
				}
			}
			return true;
		} else { return false; }
	}


	public static void main(String[] args){
		//testing out sorters
		ISort sorter = new SorterA();

		Random random = new Random();
		int k = random.nextInt();
		int v = random.nextInt();
		int size = random.nextInt(100);
		KeyValuePair[] toSort = new KeyValuePair[size];
		for (int i = 0; i < size; i++) {
			toSort[i] = new KeyValuePair(k, v);
		}
		StopWatch watch = new StopWatch();

		if (!checkSort(sorter, size)) {
			System.out.println("Is it Dr.Evil???");
		} else if (isStable(sorter, size)) {
			System.out.println("Might be QuickSort or SelectionSort");
			//check relative time
			watch.start();
			sorter.sort(toSort);
			watch.stop();
		} else {
			System.out.println("Might be MergeSort, BubbleSort or InsertionSort");
			watch.start();
			sorter.sort(toSort);
			watch.stop();
		}
	}
}