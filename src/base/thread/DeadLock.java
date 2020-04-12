package base.thread;

public class DeadLock {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        new Thread(() -> {
            while(true) {
                a.m(b);
            }
        }, "A").start();

        new Thread(() -> {
            while(true) {
                b.m(a);
            }
        }, "B").start();
    }
}

class A {

    public synchronized void m(B b) {
        System.out.println(Thread.currentThread().getName() + " 调用了A.m方法");
        b.f();
    }

    public synchronized void f() {
        System.out.println(Thread.currentThread().getName() + " 调用了A.f方法");
    }
}

class B {

    public synchronized void m(A a) {
        System.out.println(Thread.currentThread().getName() + " 调用了B.m方法");
        a.f();
    }

    public synchronized void f() {
        System.out.println(Thread.currentThread().getName() + " 调用了B.f方法");
    }
}
