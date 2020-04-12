package base.io;

import java.nio.CharBuffer;

/**
 * @Auther: Vectory
 * @Date: 2019/10/7
 * @Description: base.io
 * @version: 1.0
 */
public class BufferTest {

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(8);
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("limit: " + buffer.limit());
        System.out.println("position: " + buffer.position());

        buffer.put('a');
        buffer.put('b');
        buffer.put('c');

        System.out.println("加入三个元素后, position: " + buffer.position());

        buffer.flip();

        System.out.println("执行flip后, limit: " + buffer.limit());
        System.out.println("执行flip后, position: " + buffer.position());

        System.out.println("第一个元素(position=0): " + buffer.get());
        System.out.println("取出第一个元素后, position: " + buffer.position());

        System.out.println("第二个元素(position=0): " + buffer.get());
        System.out.println("取出第二个元素后, position: " + buffer.position());

        buffer.clear();
        System.out.println("执行clear后, limit: " + buffer.limit());
        System.out.println("执行clear后, position: " + buffer.position());
        System.out.println("执行clear后, 缓冲区内容并没有被清除, 执行绝对读取, 第三个元素: " + buffer.get(2));
        System.out.println("执行绝对读取后, position: " + buffer.position());
    }
}
