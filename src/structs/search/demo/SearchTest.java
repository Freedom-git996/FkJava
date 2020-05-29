package structs.search.demo;

import java.util.Arrays;
import java.util.Random;

public class SearchTest {

    /**
     * 顺序查找
     * @param arr a[]
     * @param key target
     * @return index
     */
    private static int search(int[] arr, int key) {
        for(int i = 0; i < arr.length; i ++) {
            if(arr[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 二分查找，非递归
     * @param arr a[]
     * @param value target
     * @return index
     */
    private static int binarySearch(int[] arr, int value) {
        int low = 0;
        int high = arr.length - 1;
        while(low <= high) {
            int mid = low + ((high - low) >> 1);
            if(arr[mid] == value) {
                return mid;
            }else if(arr[mid] > value) {
                high = mid - 1;
            }else if(arr[mid] < value) {
                low = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 插入查找
     * @param arr a[]
     * @param value target
     * @return index
     */
    private static int insertSearch(int[] arr, int value) {
        int left = 0;
        int right = arr.length - 1;

        while(left <= right) {
            if(arr[right] == arr[left]) {
                if(arr[right] == value) {
                    return right;
                }else {
                    return -1;
                }
            }

            int mid = left + ((value - arr[left]) / (arr[right] -arr[left])) * (right - left);
            if(arr[mid] == value) {
                return mid;
            }else if(value < arr[mid]) {
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = generatorTest(20);
        System.out.println("=================================");
        System.out.println("原数组：");
        for(int a : arr) {
            System.out.print(a + " ");
        }

        System.out.println("\n=================================");
        Arrays.sort(arr);
        System.out.println("排序后：");
        for(int a : arr) {
            System.out.print(a + " ");
        }

        System.out.println("\n=================================");
        System.out.println("顺序查找8：");
        System.out.println(search(arr, 8));

        System.out.println("=================================");
        System.out.println("二分查找8：");
        System.out.println(binarySearch(arr, 8));

        System.out.println("=================================");
        System.out.println("插入查找8：");
        System.out.println(insertSearch(arr, 8));
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
