package base.basic;

public class Child extends Parent {

    public String word = "this is child word";

    public static void info() {
        System.out.println("this is child static method");
    }

    public void cat() {
        System.out.println(this.word);
        System.out.println(word);
    }

    public static void main(String[] args) {
        Parent parent = new Child();
        parent.cat();
//        parent.info();
        parent.test();
//        System.out.println(parent.word);
    }
}
