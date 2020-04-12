package base.basic;

import java.util.ArrayList;
import java.util.List;

public class Canvas {

    public void drawAll(List<? extends Shape> shapes) {
        for(Shape s : shapes) {
            System.out.println(s.code);
            s.draw(this);
        }
    }

    public static void main(String[] args) {
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(1));
        circles.add(new Circle(2));

        List<Rectangle> rectangles = new ArrayList<>();
        rectangles.add(new Rectangle(1));
        rectangles.add(new Rectangle(2));

        Rectangle rectangle = new Rectangle(0);
        rectangle.addRectangle(rectangles);

        Canvas canvas = new Canvas();

        canvas.drawAll(circles);
        canvas.drawAll(rectangles);

        canvas.drawAll(circles);
    }
}
