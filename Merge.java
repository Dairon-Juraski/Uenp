package merge;

import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author dairon
 */
public class Merge {

    public static List<Long> merge(List<Long> a, List<Long> b) {
        int i = 0, j = 0;
        List<Long> result = new ArrayList<>(a.size() + b.size());
        while (i < a.size() && j < b.size()) {
            result.add(a.get(i) < b.get(j) ? a.get(i++) : b.get(j++));
        }

        while (i < a.size()) {
            result.add(a.get(i++));
        }

        while (j < b.size()) {
            result.add(b.get(j++));
        }

        return result;
    }

    public static long[] merge(long[] a, long[] b) {

        long[] answer = new long[a.length + b.length];
        int i = 0, j = 0, k = 0;

        while (i < a.length && j < b.length) {
            answer[k++] = a[i] < b[j] ? a[i++] : b[j++];
        }

        while (i < a.length) {
            answer[k++] = a[i++];
        }

        while (j < b.length) {
            answer[k++] = b[j++];
        }

        return answer;
    }

    public static class MergeTask extends RecursiveTask<List<Long>> {

        private static final int LIMITE = 4;
        private final List<Long> list;

        public MergeTask(List<Long> list) {
            this.list = list;
        }

        @Override
        protected List<Long> compute() {
            if (list.size() < LIMITE) {
                return list.stream().sorted().collect(toList());
            }

            MergeTask left = new MergeTask(list.stream().limit(list.size() / 2).collect(toList()));
            MergeTask right = new MergeTask(list.stream().skip(list.size() / 2).collect(toList()));
            invokeAll(left, right);

            return merge(left.join(), right.join());
        }
    }

    public static Long[] privideArray(int length) {
        assert length > 0;
        Random random = new Random(length);
        Long[] array = new Long[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Long.valueOf(random.nextInt(100));
        }
        return array;
    }

    static boolean isSorted(List<Long> array) {
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i - 1) > array.get(i)) {
                return false;
            }
        }
        return true;
    }

//****************************************************************************************\\
// Aqui se inicia um merge sort normal, sem concorrencia;
    static public class Normal_MergeSort {

        // Merges two subarrays of arr[]. 
        // First subarray is arr[l..m] 
        // Second subarray is arr[m+1..r] 
        void merge(Long arr[], int l, int m, int r) {
            // Find sizes of two subarrays to be merged 
            int n1 = m - l + 1;
            int n2 = r - m;

            /* Create temp arrays */
            long L[] = new long[n1];
            long R[] = new long[n2];

            /*Copy data to temp arrays*/
            for (int i = 0; i < n1; ++i) {
                L[i] = arr[l + i];
            }
            for (int j = 0; j < n2; ++j) {
                R[j] = arr[m + 1 + j];
            }

            /* Merge the temp arrays */
            // Initial indexes of first and second subarrays 
            int i = 0, j = 0;

            // Initial index of merged subarry array 
            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            /* Copy remaining elements of L[] if any */
            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }

            /* Copy remaining elements of R[] if any */
            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }

        // Main function that sorts arr[l..r] using 
        // merge() 
        void sort(Long arr[], int l, int r) {
            if (l < r) {
                // Find the middle point 
                int m = (l + r) / 2;

                // Sort first and second halves 
                sort(arr, l, m);
                sort(arr, m + 1, r);

                // Merge the sorted halves 
                merge(arr, l, m, r);
            }
        }
    }

    /* This code is contributed by Rajat Mishra */
//****************************************************************************************\\    
    public static void main(String[] args) {
        int TAM = 1000000; //TAM max = 99999999

        System.out.println("Realizando sort de maneira paralela...\n");

        long startP = System.currentTimeMillis();

        Long[] ar = privideArray(TAM);
        List<Long> longs = asList(ar);

        if (TAM <= 100) {
            Arrays.stream(ar).forEach(x -> System.out.print(x + " "));
            System.out.println(isSorted(longs));
        }

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinTask<List<Long>> task = new MergeTask(longs);
        List<Long> result = pool.invoke(task);
        pool.shutdown();

        if (TAM <= 100) {
            result.stream().forEach(x -> System.out.print(x + " "));
            System.out.println(isSorted(result));
            asList(ar).stream().sorted().forEach(x -> System.out.print(x + " "));
            System.out.println("");

        }
        System.out.println("-> Concorrentemente Demorou: " + (System.currentTimeMillis() - startP) + "ms");

        System.out.println("\n///////////////////////////////////////////////////////////////////////////////\n");

        System.out.println("Realizando sort de maneira Sequencial...\n");

        long startS = System.currentTimeMillis();

        Long[] arr = privideArray(TAM);
        List<Long> test = asList(arr);

        if (TAM <= 100) {
            Arrays.stream(arr).forEach(x -> System.out.print(x + " "));
            System.out.println(isSorted(test));
        }

        Normal_MergeSort ob = new Normal_MergeSort();
        ob.sort(arr, 0, arr.length - 1);

        if (TAM <= 100) {
            Arrays.stream(arr).forEach(x -> System.out.print(x + " "));
            System.out.println(isSorted(test));
        }
        System.out.println("-> Sequencialmente Demorou: " + (System.currentTimeMillis() - startP) + "ms");
    }

}
