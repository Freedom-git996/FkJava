package broke.threadpattern.readwritesplit.demo;

import java.util.Random;

public class WriterWorker extends Thread {

    private static final Random random = new Random(System.currentTimeMillis());

    private final ShareData data;

    private final String filler;

    private int index = 0;

    public WriterWorker(ShareData data, String filler) {
        this.data = data;
        this.filler = filler;
    }

    @Override
    public void run() {
        try {
            while (true) {
                char c = nextChar();
                data.write(c);
                System.out.println("I am write worker ......... " + Thread.currentThread() + c);
                Thread.sleep(random.nextInt(10_000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private char nextChar() {
        char c = filler.charAt(index);
        index++;
        if (index >= filler.length())
            index = 0;
        return c;
    }
}
