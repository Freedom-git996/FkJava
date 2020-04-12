package base.basic;

public class Circle extends Shape {

    public int code = 201;

    private int num;

    public Circle(int num) {
        this.num = num;
    }

    @Override
    public void draw(Canvas c) {
        System.out.println("draw a circle" + num);
    }
}
