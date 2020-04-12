package broke.gc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExitHook {

    public static void main(String[] args) throws FileNotFoundException {
        final FileOutputStream fos;
        fos = new FileOutputStream("obj.txt");
        System.out.println("系统打开物理资源");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("系统关闭物理资源");
        }));
        int count = 5;

        System.out.println(count(count));
        System.out.println(count2(count));

        System.out.println(test());
    }

    private static int count(int count) {
        return ++ count;
    }

    private static int count2(int count) {
        return count ++;
    }

    private static int test() {
        int count = 5;
        try {
            return ++ count;
        } finally {
            System.out.println("执行finally");
            return count ++;
        }
    }
}
