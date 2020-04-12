package broke.threadpattern.balking.demo;

import java.io.IOException;
import java.util.Random;

public class ChangeThread extends Thread {

    private BalkingData data;

    private Random random = new Random();

    public ChangeThread(String name, BalkingData data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; true; i ++) {
                data.change("No." + i);
                Thread.sleep(random.nextInt(1000));
                data.save();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
