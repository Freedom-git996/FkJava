package base.basic;

public class ParentCallChile {
    public static void main(String[] args) {
        Base base = new Derived();
        base.display();
    }
}

class Base {
    private int i = 2;
    public Base() {
        System.out.println(this.getClass());
        System.out.println(this);
        i = 2222;
        this.display();
    }
    public void display() {
        System.out.println(this);
        System.out.println(i);
    }
}

class Derived extends Base {
    private int i = 22;
    public Derived() {
        i = 222;
    }
    public void display() {
        System.out.println(i);
    }
}
