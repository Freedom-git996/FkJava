package broke.threadpattern.singlethread.demo;

public class User extends Thread {

    private String name;

    private String address;

    private Gate gate;

    public User(String name, String address, Gate gate) {
        this.name = name;
        this.address = address;
        this.gate = gate;
    }

    @Override
    public void run() {
        System.out.println("user = [name : " + name + " address = " + address + " ]");
        while(true) {
            gate.pass(name, address);
        }
    }
}
