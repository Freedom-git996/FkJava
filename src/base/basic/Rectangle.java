package base.basic;

import java.util.List;

public class Rectangle extends Shape {

    public int code = 301;

    private int num;

    public Rectangle(int num) {
        this.num = num;
    }

    @Override
    public void draw(Canvas c) {
        System.out.println("draw a rectangle" + num);
    }

    public void addRectangle(List<? super Rectangle> shapes) {
        shapes.add(new Rectangle(0));
    }
}
