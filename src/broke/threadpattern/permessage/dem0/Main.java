package broke.threadpattern.permessage.dem0;

public class Main {

    public static void main(String[] args) {
        Host host = new Host();
        try {
            host.request(50, 'A');
            host.request(50, 'B');
            host.request(50, 'C');
        } finally {
            System.out.println("main end");
        }
    }
}
