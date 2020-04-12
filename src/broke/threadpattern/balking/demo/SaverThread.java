package broke.threadpattern.balking.demo;

import java.io.IOException;

public class SaverThread extends Thread {

    private BalkingData data;

    public SaverThread(String name, BalkingData data) {
        super(name);
        this.data = data;
    }

    @Override
    public void run() {
        try {
            while(true) {
                data.save();
                Thread.sleep(10_000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
