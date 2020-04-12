package base.thread;

import java.io.*;

/**
 * @Auther: Vectory
 * @Date: 2019/10/6
 * @Description: base.thread
 * @version: 1.0
 */
public class DownThread {

    public static void main(String[] args) {

        File file = new File("D:\\projects\\idea\\FKJava\\src\\base\\thread\\DownThread.java");
        int threadNum = 4;
        Thread[] threads = new Thread[threadNum];

        try(
            RandomAccessFile randomAccessFile = new RandomAccessFile("random.txt", "rw");
            FileInputStream fileInputStream = new FileInputStream(file)
        ){
            long fileSize = file.length();
            randomAccessFile.setLength(fileSize);
            long currentPartSize = fileSize / threadNum + 1;
            randomAccessFile.close();
            for(int i = 0; i < threadNum; i ++){
                long startPos = i * currentPartSize;
                RandomAccessFile currentPart = new RandomAccessFile("random.txt", "rw");
                currentPart.seek(startPos);
                fileInputStream.skip(startPos);
                threads[i] = new MultiThread(startPos, currentPart);
                threads[i].start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class MultiThread extends Thread {

        File file = new File("D:\\projects\\idea\\FKJava\\src\\base\\thread\\DownThread.java");

        private long startPos;
        private RandomAccessFile currentPart;
        private FileInputStream fileInputStream;

        public MultiThread(long startPos, RandomAccessFile currentPart) throws FileNotFoundException {
            this.startPos = startPos;
            this.currentPart = currentPart;
            this.fileInputStream = new FileInputStream(file);
        }

        @Override
        public void run() {
            System.out.println(getName() + " " + "正在复制...");
            try {
                fileInputStream.skip(startPos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] content = new byte[1024];
            int len = 0;
            try {
                while ((len = fileInputStream.read(content)) > 0) {
                    currentPart.write(content, 0, len);
                }
                fileInputStream.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
