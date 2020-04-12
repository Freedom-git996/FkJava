package structs.queue.demo;

import java.util.Arrays;

public class SequenceQueue<T> {

    private int DEFAULT_SIZE = 10;
    private int capacity;
    private Object[] elementData;
    private int front = 0;
    private int rear = 0;

    public SequenceQueue() {
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    public SequenceQueue(T element) {
        this();
        elementData[0] = element;
        rear ++;
    }

    public SequenceQueue(T element, int initSize) {
        this.capacity = initSize;
        elementData = new Object[capacity];
        elementData[0] = element;
        rear ++;
    }

    public int length() {
        return rear - front;
    }

    public void add(T element) {
        if(rear > capacity - 1) {
            throw new IndexOutOfBoundsException("队列已满");
        }
        elementData[rear ++] = element;
    }

    public T remove() {
        if(empty()) {
            throw new IndexOutOfBoundsException("空队列");
        }
        T oldVal = (T)elementData[front];
        elementData[front++] = null;
        return oldVal;
    }

    public T element() {
        if(empty()) {
            throw new IndexOutOfBoundsException("空队列");
        }
        return (T)elementData[front];
    }

    public boolean empty() {
        return rear == front;
    }

    public void clear() {
        Arrays.fill(elementData, null);
        rear = front = 0;
    }

    @Override
    public String toString() {
        if(rear == front) {
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for(int i = front; i < rear; i ++) {
                sb.append(elementData[i].toString()).append(",");
            }
            int len = sb.length();
            return sb.delete(len - 1, len).append("]").toString();
        }
    }

    public static void main(String[] args) {
        // 出现”假满“现象

        SequenceQueue<String> queue = new SequenceQueue<>();
        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("ddd");
        queue.add("eee");
        queue.add("fff");
        queue.add("ggg");
        System.out.println(queue);
        System.out.println(queue.element());

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue);

        queue.add("aaa");
        queue.add("bbb");
        queue.add("ccc");
        queue.add("ddd");
        queue.add("eee");
        queue.add("fff");
        queue.add("ggg");
        System.out.println(queue);
    }
}
