package structs.heap;

import java.util.HashSet;
import java.util.Set;

public class MaxHeap {

    int[] eles;
    int capacity;
    int size;

    public MaxHeap(int capacity) {
        this.eles = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public void insert(int ele) {
        if(size == capacity) {
            return;
        }
        eles[size + 1] = ele;
        size ++;
        shiftUp(size);
    }

    public int extractMax() {
        if(size == 0) {
            return -1;
        }
        int ele = eles[1];
        eles[1] = eles[size];
        size --;
        shiftDown(1);
        return ele;
    }

    private void shiftUp(int size) {
        int index = size;
        while(index > 1 && eles[index / 2] < eles[index]) {
            int temp = eles[index / 2];
            eles[index / 2] = eles[index];
            eles[index] = temp;
            index = index / 2;
        }
    }

    private void shiftDown(int head) {
        while(head * 2 <= size) {
            int j = 2 * head;
            if(j + 1 <= size && eles[j + 1] > eles[j])
                j += 1;
            if(eles[head] >= eles[j])
                break;
            int temp = eles[head];
            eles[head] = eles[j];
            eles[j] = temp;
            head = j;
        }
    }

    public static int findKthLargest(int[] nums, int k) {
        MaxHeap heap = new MaxHeap(nums.length + 1);
        for(int i = 0; i < nums.length; i ++) {
            heap.insert(nums[i]);
        }

        for(int i = 1; i <= nums.length; i ++) {
            int temp = heap.extractMax();
            if(nums.length < k) return temp;
            if(i == k) {
                return temp;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, 2, 3, 1};
        System.out.println(findKthLargest(nums, 3));
    }
}
