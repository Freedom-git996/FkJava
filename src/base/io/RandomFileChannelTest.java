package base.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: Vectory
 * @Date: 2019/10/7
 * @Description: base.io
 * @version: 1.0
 */
public class RandomFileChannelTest {

    public static void main(String[] args) {
        File f = new File("D:\\MyProjects\\IdeaProjects\\FKJava\\random.txt");
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(f, "rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            ByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, f.length());
            fileChannel.position(f.length());
            fileChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
