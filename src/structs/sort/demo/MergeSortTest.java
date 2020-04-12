package structs.sort.demo;

/**
 * 归并排序
 * 时间复杂度O(nlogn)
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.sort
 * @version: 1.0
 */
public class MergeSortTest {

    private void internalMergeSort(int[] arr, int left, int middle, int right){
        int[] leftArr = new int[middle - left + 1];
        int[] rightArr = new int[right - middle];

        for(int i = left; i <= middle; i ++){
            leftArr[i - left] = arr[i];
        }

        for(int i = middle + 1; i <= right; i ++){
            rightArr[i - (middle + 1)] = arr[i];
        }

        int i = 0, j = 0, k = left;
        while(i < leftArr.length && j < rightArr.length){
            if(leftArr[i] > rightArr[j]){
                arr[k] = rightArr[j];
                j ++;
            }else{
                arr[k] = leftArr[i];
                i ++;
            }
            k ++;
        }

        while(i < leftArr.length){
            arr[k] = leftArr[i];
            i ++;
            k ++;
        }

        while(i < rightArr.length){
            arr[k] = rightArr[j];
            j ++;
            k ++;
        }
    }

    public void mergeSort(int[] arr, int left, int right){
        if(left < right){
            int middle = (right + left) / 2;
            mergeSort(arr, left, middle);
            mergeSort(arr,middle + 1, right);
            internalMergeSort(arr, left, middle, right);
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{2, 8, 9, 10, 4, 5, 6, 7};
        MergeSortTest mergeSortTest = new MergeSortTest();
        mergeSortTest.internalMergeSort(arr, 0, 3, 7);
        for(int i : arr){
            System.out.println(i);
        }
        System.out.println("-------");
        int[] arr1 = new int[]{78, 17, 39, 26, 72, 94, 21, 12, 23, 68};
        mergeSortTest.mergeSort(arr1, 0, arr1.length - 1);
        for(int i : arr1){
            System.out.println(i);
        }
    }
}
