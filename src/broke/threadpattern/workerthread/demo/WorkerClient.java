package broke.threadpattern.workerthread.demo;

public class WorkerClient {

    public static void main(String[] args) {
        final Channel channel = new Channel(5);
        channel.startWorker();

        new TransportThread("John", channel).start();
        new TransportThread("Jack", channel).start();
        new TransportThread("Trump", channel).start();
    }
}
