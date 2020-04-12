package broke.threadpattern.singlethread.demo;

public class Client {
    public static void main(String[] args) {
        Gate gate = new Gate();
        User bj = new User("BaoBao", "Beijing", gate);
        User sh = new User("ShangLao", "Shanghai", gate);
        User gz = new User("GuangLao", "Guangzhou", gate);
        bj.start();
        sh.start();
        gz.start();
    }
}
