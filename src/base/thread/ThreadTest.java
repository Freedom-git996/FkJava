package base.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {

    public static void main(String[] args) {
        Account account = new Account("123", 800);
        new Thread(() -> {
           account.drawBySyn(400);
        },"Thread1").start();
        new Thread(() -> {
            account.drawBySyn(500);
        }, "Thread2").start();

        Account account1 = new Account("456", 800);
        new Thread(() -> {
            account1.drawByLock(400);
        }, "Thread3").start();
        new Thread(() -> {
            account1.drawByLock(500);
        }, "Thread4").start();
    }
}

class Account {

    private final ReentrantLock reentrantLock = new ReentrantLock();

    private String accountNo;
    private Integer balance;

    public Account() {

    }

    public Account(String accountNo, Integer balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        return accountNo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == Account.class) {
            Account accountObj = (Account)obj;
            return accountObj.accountNo.equals(accountNo);
        }
        return false;
    }

    public synchronized void drawBySyn(Integer drawAmount) {
        draw(drawAmount);
    }

    public void drawByLock(Integer drawAmount) {
        reentrantLock.lock();
        try {
            draw(drawAmount);
        }finally {
            reentrantLock.unlock();
        }
    }

    public void draw(Integer drawAmount) {
        System.out.println(Thread.currentThread().getName() + "想取出：" + drawAmount);
        System.out.println(Thread.currentThread().getName() + "此时还剩：" + balance);
        if(balance >= drawAmount) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -=drawAmount;
            System.out.println(Thread.currentThread().getName() + "还剩：" + balance);
        }else {
            System.out.println(Thread.currentThread().getName() + "余额不够");
        }
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
