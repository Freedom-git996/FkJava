package structs.queue.demo;

import java.util.Arrays;

public class LoopQueue<T> {

    private int DEFAULT_SIZE = 10;
    private int capacity;
    private Object[] elementData;
    private int front = 0;
    private int rear = 0;

    public LoopQueue() {
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    public LoopQueue(T element) {
        this();
        elementData[0] = element;
        rear++;
    }

    public LoopQueue(T element, int initSize) {
        this.capacity = initSize;
        elementData = new Object[capacity];
        elementData[0] = element;
        rear++;
    }

    public int length() {
        if(empty()) {
            return 0;
        }
        return rear > front ? rear - front : capacity - (front - rear);
    }

    public void add(T element) {
        if(rear == front && elementData[front] != null) {
            throw new IndexOutOfBoundsException("队列已满");
        }
        elementData[rear ++] = element;
        rear = rear == capacity ? 0 : rear;
    }

    public T remove() {
        if(empty()) {
            throw new IndexOutOfBoundsException("空队列");
        }
        T oldVal = (T) elementData[front];
        elementData[front ++] = null;
        front = front == capacity ? 0 : front;
        return oldVal;
    }

    public T element() {
        if(empty()) {
            throw new IndexOutOfBoundsException("空队列");
        }
        return (T) elementData[front];
    }

    public boolean empty() {
        return front == rear && elementData[front] == null;
    }

    public void clear() {
        Arrays.fill(elementData, null);
        rear = front = 0;
    }

    @Override
    public String toString() {
        if(empty()) {
            return "[]";
        }else {
            if(front < rear) {
                StringBuilder sb = new StringBuilder("[");
                for(int i = front; i < rear; i ++) {
                    sb.append(elementData[i].toString()).append(",");
                }
                int len = sb.length();
                return sb.delete(len - 1, len).append("]").toString();
            }else {
                StringBuilder sb = new StringBuilder("[");
                for(int i = front; i < capacity; i ++) {
                    sb.append(elementData[i].toString()).append(",");
                }
                for(int i = 0; i < rear; i ++) {
                    sb.append(elementData[i].toString()).append(",");
                }
                int len = sb.length();
                return sb.delete(len - 1, len).append("]").toString();
            }
        }
    }

    public static void main(String[] args) {
        LoopQueue<String> queue = new LoopQueue<>();
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
