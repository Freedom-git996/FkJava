package base.basic;

public class Apple extends Fruit {
    private final int count;

    {
        count = 2;
    }

    @Override
    public void info() {
        System.out.println("Apple方法");
    }
    public void AccessSuperInfo() {
        super.info();
    }
    public Fruit getSuper() {
        return super.getThis();
    }
    String color = "红色";

    public static void main(String[] args) {
        Apple a = new Apple();
        Fruit f = a.getSuper();
        System.out.println("a和f引用的对象是否相同：" + (a==f));
        System.out.println("访问a所引用对象的color实例变量：" + a.color);
        System.out.println("访问f所引用对象的color实例变量：" + f.color);
        a.info();
        f.info();
        a.AccessSuperInfo();
    }
}
