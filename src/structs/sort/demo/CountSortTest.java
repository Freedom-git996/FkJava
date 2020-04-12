package structs.sort.demo;

/**
 * 计数排序
 * @Auther: Vectory
 * @Date: 2019/10/12
 * @Description: structs.sort
 * @version: 1.0
 */
public class CountSortTest {

    private int chooseMax(int[] arr){
        int max = 0;
        for(int i = 0; i < arr.length; i ++){
            if(max < arr[i])
                max = arr[i];
        }
        return max;
    }

    private int chooseMin(int[] arr){
        int min = 0;
        for(int i = 0; i < arr.length; i ++){
            if(min > arr[i])
                min = arr[i];
        }
        return min;
    }

    public void natureCountSort(int[] arr){
        int max = chooseMax(arr);
        int min = chooseMin(arr);
        int size = max - min + 1;
        for(int i = 0; i < arr.length; i ++){
            arr[i] = arr[i] + Math.abs(min);
        }

        int[] newArr = new int[size];
        for(int i = 0; i < arr.length; i ++){
            newArr[arr[i]] ++;
        }

        int i = 0;
        for(int j = 0; j < newArr.length; j ++){
            if(newArr[j] == 0)
                continue;
            for(int z = 0; z < newArr[j]; z ++){
                arr[i ++] = j - Math.abs(min);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3, 1, 2, 0, 3, 2, 6};
        CountSortTest countSortTest = new CountSortTest();
        countSortTest.natureCountSort(arr);
        for(int i : arr){
            System.out.println(i);
        }
        System.out.println("-------");
        int[] arr1 = new int[]{-3, 1, 2, 0, 3, 2, 6};
        countSortTest.natureCountSort(arr1);
        for(int i : arr1){
            System.out.println(i);
        }
    }
}
