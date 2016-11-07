package com.bob.springboot.util.algorithm;

/**
 * Created by Bob Jiang on 2016/11/7.
 */
public class Algorithm {

    /**
     * 下一个质数
     *
     * @param num
     * @return
     */
    public static int nextPrime(int num) {
        for(int i = num + 1;; i++) {
            boolean isPrime = true;
            for(int j = 2; j < i; j++) {
                if(i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if(isPrime) {
                return i;
            }
        }
    }

    /**
     * 快速排序
     *
     * @param arr
     * @param left
     * @param right
     */
    public static void quickSort(int[] arr, int left, int right) {
        int temp;
        if (left < right) {
            temp = partition(arr, left, right);
            quickSort(arr, left, temp - 1);
            quickSort(arr, temp + 1, right);
        }
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[left];
        while (left < right) {
            while (left < right && arr[right] >= pivot)
                right--;
            arr[left] = arr[right];
            while (left < right && arr[left] <= pivot)
                left++;
            arr[right] = arr[left];
        }
        arr[left] = pivot;
        return left;
    }

}
