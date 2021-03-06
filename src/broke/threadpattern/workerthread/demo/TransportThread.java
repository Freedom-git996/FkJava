package broke.threadpattern.workerthread.demo;

import java.util.Random;

public class TransportThread extends Thread {

    private final Channel channel;

    private static final Random random = new Random();

    public TransportThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; true; i ++) {
                Request request = new Request(getName(), i);
                this.channel.put(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
