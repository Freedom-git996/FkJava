package structs.sort.demo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 桶排序
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.sort
 * @version: 1.0
 */
public class BucketSortTest {

    public void bucketSort(int[] arr){
        // 首先还是找出最大、最小值
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++){
            max = Math.max(max, arr[i]);
            min = Math.min(min, arr[i]);
        }

        // 桶数
        // 在桶排序中，对桶的划分个数是随意的
        // 这个方法划分的桶数量随带划分数列的密集程度改变而改变
        int bucketNum = (max - min) / arr.length + 1;
        ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>(bucketNum);
        // 初始化各个桶
        for(int i = 0; i < bucketNum; i++){
            bucketArr.add(new ArrayList<Integer>());
        }

        // 将每个元素放入相应的桶
        for(int i = 0; i < arr.length; i++){
            int num = (arr[i] - min) / (arr.length);
            bucketArr.get(num).add(arr[i]);
        }

        // 对每个桶进行排序
        int k = 0;
        for(int i = 0; i < bucketArr.size(); i++){
            Collections.sort(bucketArr.get(i));
            for (int j = 0; j < bucketArr.get(i).size(); j++) {
                arr[k ++] = bucketArr.get(i).get(j);
            }
        }

        // 归并排序
        MergeSortTest mergeSortTest = new MergeSortTest();
        mergeSortTest.mergeSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = new int[]{78, 17, 39, 26, 72, 94, 21, 12, 23, 68};
        BucketSortTest bucketSortTest = new BucketSortTest();
        bucketSortTest.bucketSort(arr);
        for(int i : arr) {
            System.out.println(i);
        }
    }
}
