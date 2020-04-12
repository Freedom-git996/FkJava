package base.io;

import java.io.*;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @Auther: Vectory
 * @Date: 2019/10/7
 * @Description: base.io
 * @version: 1.0
 */
public class FileChannelTest {

    public static void main(String[] args) {
        File f = new File("D:\\MyProjects\\IdeaProjects\\FKJava\\src\\base\\io\\FileChannelTest.java");
        try(
                FileChannel inChannel = new FileInputStream(f).getChannel();
                FileChannel outChannel = new FileOutputStream("channel.txt").getChannel()
                ) {
            MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
            Charset charset = Charset.forName("UTF-8");
            outChannel.write(buffer);
            System.out.println("file的size: " + f.length());
            System.out.println("buffer的capacity: " + buffer.capacity());
            System.out.println("buffer的limit: " + buffer.limit());
            System.out.println("buffer的position: " + buffer.position());
            buffer.clear();
            System.out.println("file的size: " + f.length());
            System.out.println("buffer的capacity: " + buffer.capacity());
            System.out.println("buffer的limit: " + buffer.limit());
            System.out.println("buffer的position: " + buffer.position());
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(buffer);
            System.out.println(charBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
