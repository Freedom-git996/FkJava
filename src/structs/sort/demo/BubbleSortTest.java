package structs.sort.demo;

/**
 * 冒泡排序：交换排序，两两比较
 * 最好的情况，是本身已经排好序的情况，需要进行[n - 1]次比较
 * 最坏的情况，是逆序，需要进行[(n * (n - 1)) / 2]次比较
 * 因此总的时间复杂度为O(n^2)
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.sort
 * @version: 1.0
 */
public class BubbleSortTest {

    public void bubbleSort(int arr[]){
        int swap = 0;
        int count = arr.length - 1;
        for(; count > 0; count --) {
            for (int i = 0; i < count; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = swap;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{4, 3, 3, 2, 5};
        BubbleSortTest bubbleSortTest = new BubbleSortTest();
        bubbleSortTest.bubbleSort(arr);
        for(int i : arr){
            System.out.println(i);
        }
    }
}
