package base.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class BaseFileOpr {

    public static void main(String[] args) throws Exception {
        watch();
    }

    private static void watch() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get("C:/").register(watchService, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
        while(true) {
            WatchKey key = watchService.take();
            for(WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context() + " 文件发生了" + event.kind() + "事件");
            }
            boolean valid = key.reset();
            if(!valid) {
                break;
            }
        }
    }

    private static void files() throws Exception {
        Files.walkFileTree(Paths.get("D:/projects/idea/FKJava"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                if(file.toString().toLowerCase().endsWith(".txt")) {
                    System.out.println("找到txt文件：" + file);
                    return FileVisitResult.TERMINATE;
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void path() {
        Path path = Paths.get(".");
        System.out.println("path里包含的路径数量：" + path.getNameCount());
        System.out.println("path的根路径：" + path.toAbsolutePath().getRoot());
        System.out.println("path的绝对路径：" +path.toAbsolutePath());
    }

    private static void channelNIO3() throws Exception {
        File file = new File("obj.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel randomChannel = raf.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(256);
        while(randomChannel.read(buffer) != -1) {
            buffer.flip();
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer cbuff = decoder.decode(buffer);
            System.out.println(cbuff);
            buffer.clear();
        }
        raf.close();
        randomChannel.close();
    }

    private static void channelNIO2() throws Exception {
        FileInputStream fis = new FileInputStream("obj.txt");
        FileChannel fcin = fis.getChannel();

        ByteBuffer bbuff = ByteBuffer.allocate(256);
        while(fcin.read(bbuff) != -1) {
            bbuff.flip();
            Charset charset = Charset.forName("UTF-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer cbuff = decoder.decode(bbuff);
            System.out.println(cbuff);
            bbuff.clear();
        }
        fis.close();
        fcin.close();
    }

    private static void channelNIO() throws Exception {
        File file = new File("obj.txt");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel randomChannel = raf.getChannel();
        ByteBuffer buffer = randomChannel.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
        randomChannel.position(file.length());
        randomChannel.write(buffer);
        raf.close();
        randomChannel.close();
    }

    private static void buffNIO() {
        CharBuffer charBuffer = CharBuffer.allocate(8);
        System.out.println("buff's position is " + charBuffer.position());
        System.out.println("buff's limit is " + charBuffer.limit());

        charBuffer.put('A');
        charBuffer.put('B');
        charBuffer.put('C');
        charBuffer.put('D');
        System.out.println("after insert 4 elem, buff's position is " + charBuffer.position());
        System.out.println("after insert 4 elem, buff's limit is " + charBuffer.limit());

        charBuffer.flip();
        System.out.println("after invoke flip, buff's position is " + charBuffer.position());
        System.out.println("after invoke flip, buff's limit is " + charBuffer.limit());

        charBuffer.get();
        charBuffer.get();
        System.out.println("after get 2 elem, buff's position is " + charBuffer.position());
        System.out.println("after get 2 elem, buff's limit is " + charBuffer.limit());

        charBuffer.clear();
        System.out.println("after invoke clear, buff's position is " + charBuffer.position());
        System.out.println("after invoke clear, buff's limit is " + charBuffer.limit());

        System.out.println("get index 1 elem: " + charBuffer.get(1));
    }

    private static void insert(String fileName, long pos, String insertContent) throws Exception {
        File tmp = File.createTempFile("tmp", null);
        System.out.println(tmp.getAbsolutePath());
        tmp.deleteOnExit();

        try (
                RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
                FileOutputStream tmpOut = new FileOutputStream(tmp);
                FileInputStream tmpIn = new FileInputStream(tmp)
                ) {

            raf.seek(pos);
            byte[] bbuf = new byte[64];
            int hasRead = 0;
            while((hasRead = raf.read(bbuf)) > 0) {
                tmpOut.write(bbuf, 0, hasRead);
            }

            raf.seek(pos);
            raf.write(insertContent.getBytes());
            while((hasRead = tmpIn.read(bbuf)) > 0) {
                raf.write(bbuf, 0, hasRead);
            }
        }
    }

    private static void buffIo() throws Exception {
        try(
                InputStreamReader reader = new InputStreamReader(System.in);
                BufferedReader br = new BufferedReader(reader)
        )
        {
            String line = null;
            while((line = br.readLine()) != null) {
                if(line.equals("exit")) {
                    System.exit(1);
                }
                System.out.println(line);
            }
        }
    }

    private static void baseIo() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\projects\\idea\\FKJava\\src\\base\\io\\BaseFileOpr.java");
        File file = new File("D:\\BaseFileOpr.java");
        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);

        byte[] buff = new byte[1024];
        int len = 0;
        while ((len = fis.read(buff)) != -1) {
            fos.write(buff, 0, len);
        }
        fis.close();
        fos.close();
    }

    private static void findJava(File[] files) {
        for(File file : files) {
            if (file.isDirectory()) {
                File[] child = file.listFiles();
                if(child != null)
                    findJava(child);
            } else if (file.isFile()) {
                if(file.getName().endsWith(".java")) {
                    System.out.println(file.getName());
                }
            }
        }
    }
}
