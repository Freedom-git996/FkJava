package base.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @Auther: Vectory
 * @Date: 2019/10/7
 * @Description: base.io
 * @version: 1.0
 */
public class ReadFile {

    public static void main(String[] args) {
        File f = new File("D:\\MyProjects\\IdeaProjects\\FKJava\\src\\base\\io\\ReadFile.java");
        try(
                FileInputStream inputStream = new FileInputStream(f);
                FileChannel fileChannel = inputStream.getChannel()
                ) {
            ByteBuffer buffer = ByteBuffer.allocate(256);
            while(fileChannel.read(buffer) != -1){
                buffer.flip();
                Charset charset = Charset.forName("UTF-8");
                CharsetDecoder decoder = charset.newDecoder();
                CharBuffer charBuffer = decoder.decode(buffer);
                System.out.println(charBuffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
