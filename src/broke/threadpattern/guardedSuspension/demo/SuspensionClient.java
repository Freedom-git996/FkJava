package broke.threadpattern.guardedSuspension.demo;

public class SuspensionClient {

    public static void main(String[] args) throws InterruptedException {
        final RequestQueue queue = new RequestQueue();
        ClientThread client = new ClientThread(queue, "AAA");
        client.start();
        ServerThread server = new ServerThread(queue);
        server.start();

        Thread.sleep(10_000);
        server.close();
    }
}
