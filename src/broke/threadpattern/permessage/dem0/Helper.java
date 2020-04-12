package broke.threadpattern.permessage.dem0;

public class Helper {

    public void handle(int count, char c) {
        System.out.println(" handle begin " + c);
        for(int i = 0; i < count; i ++) {
            slowly();
            System.out.println(c);
        }
        System.out.println();
        System.out.println(" handle end " + c);
    }

    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
