package base.basic;

public class Parent implements GrandParent {

    public String word = "this is parent word";

    public static void info() {
        System.out.println("this is parent static method");
    }

    public void cat() {
        System.out.println(this);
        System.out.println(word);
    }

    public void test() {
        System.out.println(this.word);
    }
}
