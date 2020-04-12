package base.basic;

public class OutterClass {

    private String code = "outter";

    static {
        System.out.println("外部类初始化");
    }

    private static class InnerClass {
        private String code = "inner";

        {
            System.out.println("内部类初始化");
        }
    }

    private class InnerClass2 {
        private String code = "innerClass2";

        {
            System.out.println(code);
            System.out.println(OutterClass.this.code);
        }
    }

    public static void main(String[] args) {
        OutterClass outterClass = new OutterClass();
        OutterClass.InnerClass innerClass = new OutterClass.InnerClass();
        InnerClass2 innerClass2 = outterClass.new InnerClass2();
    }
}