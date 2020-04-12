package broke.thread.bankthread.demo;

public class Bank {

    public static void main(String[] args) {

        // 版本一
//        TicketWindow ticketWindow1 = new TicketWindow("一号柜台");
//        ticketWindow1.start();
//
//        TicketWindow ticketWindow2 = new TicketWindow("二号柜台");
//        ticketWindow2.start();
//
//        TicketWindow ticketWindow3 = new TicketWindow("三号柜台");
//        ticketWindow3.start();

        // 版本二
        final TicketWindowRunnable ticketWindowRunnable = new TicketWindowRunnable();
        Thread thread1 = new Thread(ticketWindowRunnable,"一号柜台");
        Thread thread2 = new Thread(ticketWindowRunnable,"二号柜台");
        Thread thread3 = new Thread(ticketWindowRunnable,"三号柜台");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
