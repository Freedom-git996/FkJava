package broke.threadpattern.balking.demo;

public class Main {

    public static void main(String[] args) {
        BalkingData data = new BalkingData("obj.txt", "(empty)");
        new ChangeThread("ChangeThread", data).start();
        new SaverThread("SaveThread", data).start();
    }
}
