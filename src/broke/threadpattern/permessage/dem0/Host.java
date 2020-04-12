package broke.threadpattern.permessage.dem0;

public class Host {

    private final Helper helper = new Helper();

    public Host() {}

    public void request(final int count, final char c) {
        System.out.println("request begin " + count + " " + c);

        new Thread(() -> {
            helper.handle(count, c);
        }).start();

        System.out.println("request end " + count + " " + c);
    }
}
