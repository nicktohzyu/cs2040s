import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SpeedDemon {
    static final int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733};

    //play around with swapping the primes
    public int processData(String fileName) {
        try {
//            StopWatch stopwatch = new StopWatch();
//            stopwatch.start();
            Reader custReader = new Reader(fileName);
            int n = custReader.nextInt();
            //        System.out.println(n);
//            CustomHashMap custmap = new CustomHashMap(n * 3);
            int n1 = Integer.highestOneBit(n);
            int capacity = (n1 + Integer.highestOneBit(n - n1)) * 2; //tune
            long[] keys = new long[capacity + 128];
            int[] value = new int[capacity + 128];
//            stopwatch.stop();
//            System.out.println("initialize: " + stopwatch.getTime());
//            stopwatch.reset();
//            stopwatch.start();
            long hash;
            int c;
            int sum = 0;
            while (n > 0) {
                //scan and hash
                //            StringBuilder s = new StringBuilder(); //debug
                hash = 1;
                c = custReader.read();
                while (c != 10 && c != 13) {
                    //                s.append((char) c); //debug
                    hash = hash * primes[c]; //consider reading a bunch then use for loop to multiply
                    c = custReader.read();
                }
                //            System.out.println(hash); //debug
                //put hash into map
//                custmap.update(hash);

                int hashed = Math.floorMod(hash, capacity);
                while (true) {
                    if (keys[hashed] == hash) {
                        sum += value[hashed];
                        value[hashed] += 1;
                        break;
                    } else if (keys[hashed] == 0) {
                        keys[hashed] = hash;
                        value[hashed] = 1;
                        break;
                    }
                    hashed++;
                }
                n--;
            }
//            stopwatch.stop();
//            System.out.println("scan and hash: " + stopwatch.getTime());
//            stopwatch.reset();
            return sum;
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte[] buffer;
        private int bufferPointer, bytesRead;

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    public static void main(String[] args) {
        SpeedDemon dataProcessor = new SpeedDemon();
        int answer = dataProcessor.processData(args[0]);
        System.out.println(answer);
    }
}
