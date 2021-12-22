import java.util.*;

public class MergeSort {

    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        int[] ar = new int[n];

        for (int i = 0; i < n; ++i) {
            ar[i] = in.nextInt();
        }

        int side = -1;          // placeholder
        int depth = -1;         // placeholder

        mergeSort(ar, 0, ar.length-1, side, depth);
        bubbleSort();

    }

    public static void mergeSort(int[] ar, int start, int end, int side, int depth) {
        if (side == 1) {
            System.out.print(depth);
        }
        if (ar.length > 2) {
            int m = (end - start / 2);
            mergeSort(ar, start, m, 1, depth++);
            mergeSort(ar, m+1, end, 2, depth++);
        }
    }

    public static void bubbleSort() {

    }
}
