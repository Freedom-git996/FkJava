package broke.thread.bankthread.practice;

public class Bank {

    private int baseMoney = 400;
    private boolean flag = true;

    // 取钱
    public synchronized void withdrawMoney(int money) throws Exception {
        if(flag) {
            baseMoney -= money;
            System.out.println(Thread.currentThread().getName() + "取出" + money + "元, 余额是" + baseMoney);
            flag = false;
            notifyAll();
        }else {
            System.out.println(Thread.currentThread().getName() + " wait");
            wait();
        }

    }

    // 存钱
    public synchronized void depositMoney(int money) throws Exception {
        if(flag) {
            System.out.println(Thread.currentThread().getName() + " wait");
            wait();
        }else {
            baseMoney += money;
            System.out.println(Thread.currentThread().getName() + "存入" + money + "元, 余额是" + baseMoney);
            flag = true;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        new Thread(() -> {
            while(true) {
                try {
                    bank.withdrawMoney(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "w1").start();

        new Thread(() -> {
            while(true) {
                try {
                    bank.withdrawMoney(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "w2").start();

        new Thread(() -> {
            while(true) {
                try {
                    bank.depositMoney(400);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "d2").start();
    }
}
