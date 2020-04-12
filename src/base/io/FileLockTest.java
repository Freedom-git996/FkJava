package base.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @Auther: Vectory
 * @Date: 2019/10/7
 * @Description: base.io
 * @version: 1.0
 */
public class FileLockTest {

    public static void main(String[] args) {
        File f = new File("D:\\MyProjects\\IdeaProjects\\FKJava\\random.txt");
        try {
            FileChannel channel = new FileOutputStream(f).getChannel();
            FileLock lock = channel.tryLock();
            Thread.sleep(20000);
            lock.release();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
