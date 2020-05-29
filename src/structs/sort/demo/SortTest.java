package structs.sort.demo;

import java.util.Arrays;
import java.util.Random;

public class SortTest {

    /**
     * 冒泡排序
     * @param arr a[]
     * @return a[]
     */
    private static int[] bubbleSort(int[] arr) {
        if(arr.length == 0) {
            return arr;
        }
        for(int i = 0; i < arr.length; i ++) {
            for(int j = 0; j < arr.length - 1 - i; j ++) {
                if(arr[j + 1] < arr[j]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 选择排序
     * @param arr a[]
     * @return a[]
     */
    private static int[] selectionSort(int[] arr) {
        if(arr.length == 0) {
            return arr;
        }
        for(int i = 0; i < arr.length; i ++) {
            int minIndex = i;
            for(int j = i; j < arr.length; j ++) {
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    /**
     * 插入排序 https://blog.csdn.net/qq_42857603/article/details/81605124
     * @param arr a[]
     * @return a[]
     */
    private static int[] insertionSort(int[] arr) {
        if(arr.length == 0) {
            return arr;
        }
        int current;
        for(int i = 0; i < arr.length - 1; i ++) {
            current = arr[i + 1];
            int preIndex = i;
            while(preIndex >= 0 && current < arr[preIndex]) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex --;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }

    /**
     * 归并排序
     * @param arr a[]
     * @return a[]
     */
    private static int[] mergeSort(int[] arr) {
        if(arr.length < 2) return arr;
        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    // 合并数组
    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        for(int index = 0, i = 0, j = 0; index < result.length; index ++) {
            if(i >= left.length) {
                result[index] = right[j ++];
            }else if(j >= right.length) {
                result[index] = left[i ++];
            }else if(left[i] > right[j]) {
                result[index] = right[j ++];
            }else {
                result[index] = left[i ++];
            }
        }
        return result;
    }

    /**
     * 快速排序 https://www.jianshu.com/p/5f38dd54b11f
     * @param arr a[]
     * @param left 0
     * @param right a.length
     */
    private static void quickSort(int[] arr, int left, int right){
        // 左下标一定小于右下标，否则就越界了
        if (left < right) {
            // 对数组进行分割，取出下次分割的基准标号
            int base = division(arr, left, right);

            // 对“基准标号“左侧的一组数值进行递归的切割，以至于将这些数值完整的排序
            quickSort(arr, left, base - 1);

            // 对“基准标号“右侧的一组数值进行递归的切割，以至于将这些数值完整的排序
            quickSort(arr, base + 1, right);
        }
    }

    private static int division(int[] arr, int left, int right) {
        // 以最左边的数(left)为基准
        int base = arr[left];
        while (left < right) {

            // 从序列右端开始，向左遍历，直到找到小于base的数
            while (left < right && arr[right] >= base)
                right--;

            // 找到了比base小的元素，将这个元素放到最左边的位置
            arr[left] = arr[right];

            // 从序列左端开始，向右遍历，直到找到大于base的数
            while (left < right && arr[left] <= base)
                left++;

            // 找到了比base大的元素，将这个元素放到最右边的位置
            arr[right] = arr[left];
        }

        // 最后将base放到left位置。此时，left位置的左侧数值应该都比left小；
        // 而left位置的右侧数值应该都比left大。
        arr[left] = base;
        return left;
    }

    private static int[] countSort(int[] arr) {
        if(arr.length == 0) return arr;
        int bias, min = arr[0], max = arr[0];
        for(int i = 1; i < arr.length; i ++) {
            if(arr[i] > max) {
                max = arr[i];
            }
            if(arr[i] < min) {
                min = arr[i];
            }
        }
        bias = 0 - min;
        int[] bucket = new int[max - min + 1];
        Arrays.fill(bucket, 0);
        for(int i = 0; i < arr.length; i ++) {
            bucket[arr[i] + bias] ++;
        }
        int index = 0, i = 0;
        while(index < arr.length) {
            if(bucket[i] != 0) {
                arr[index] = i - bias;
                bucket[i] --;
                index ++;
            }else {
                i ++;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = generatorTest(20);
        System.out.println("=================================");
        System.out.println("原数组：");
        for (int a : arr) {
            System.out.print(a + " ");
        }

//        System.out.println("\n=================================");
//        Arrays.sort(arr);
//        System.out.println("排序后：");
//        for (int a : arr) {
//            System.out.print(a + " ");
//        }

//        System.out.println("\n=================================");
//        int[] bubbleResult = bubbleSort(arr);
//        System.out.println("冒泡排序后：");
//        for (int a : bubbleResult) {
//            System.out.print(a + " ");
//        }

//        System.out.println("\n=================================");
//        int[] selectionResult = selectionSort(arr);
//        System.out.println("选择排序后：");
//        for (int a : selectionResult) {
//            System.out.print(a + " ");
//        }

//        System.out.println("\n=================================");
//        quickSort(arr, 0, arr.length - 1);
//        System.out.println("快速排序后：");
//        for (int a : arr) {
//            System.out.print(a + " ");
//        }

//        System.out.println("\n=================================");
//        int[] mergeResult = mergeSort(arr);
//        System.out.println("归并排序后：");
//        for (int a : mergeResult) {
//            System.out.print(a + " ");
//        }

//        System.out.println("\n=================================");
//        int[] insertResult = insertionSort(arr);
//        System.out.println("插入排序后：");
//        for (int a : insertResult) {
//            System.out.print(a + " ");
//        }

        System.out.println("\n=================================");
        int[] insertResult = countSort(arr);
        System.out.println("计数排序后：");
        for (int a : insertResult) {
            System.out.print(a + " ");
        }
    }

    /**
     * 生成测试数据
     * @param length 数组长度
     * @return arr[]
     */
    private static int[] generatorTest(int length) {
        int[] arr = new int[length];
        for(int i = 0; i < length; i ++) {
            Random random = new Random();
            arr[i] = random.nextInt(20);
        }
        return arr;
    }
}
