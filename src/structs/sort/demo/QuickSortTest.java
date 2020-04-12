package structs.sort.demo;

/**
 * 快速排序
 * 时间复杂度O(nlogn)
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.sort
 * @version: 1.0
 */
public class QuickSortTest {

    private int partition(int[] arr, int low, int high){
        int pivot = arr[low];
        while(low < high){
            while(low < high && arr[high] >= pivot){
                high --;
            }
            arr[low] = arr[high];
            while(low < high && arr[low] <= pivot){
                low ++;
            }
            arr[high] = arr[low];
        }
        arr[low] = pivot;
        return low;
    }

    public void quickSort(int[] arr, int low, int high){
        if(low >= high)
            return;
        int pivot = partition(arr, low, high);
        quickSort(arr, low, pivot - 1);
        quickSort(arr, pivot + 1, high);
    }

    public static void main(String[] args) {
        int[] arr= new int[]{22, 15, 46, 27, 72};
        QuickSortTest quickSortTest = new QuickSortTest();
        quickSortTest.quickSort(arr, 0, arr.length - 1);
        for(int i : arr){
            System.out.println(i);
        }
    }
}
