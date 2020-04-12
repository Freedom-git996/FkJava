package base.reflect;

import java.io.FileInputStream;
import java.lang.reflect.*;
import java.util.Properties;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect
 * @version: 1.0
 */
public class Hello {

    private static String str = "param";

    private void info() {
        System.out.println("hello world");
    }

    public void info(String name) {
        System.out.println("hello " + name);
    }

    public void info(Integer code) {
        System.out.println("this is code " + code);
    }

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties();
        properties.load(new FileInputStream("obj.txt"));

        Class<Hello> clazz = Hello.class;
        System.out.println("类的名称：" + clazz.getName());
        System.out.println("类的简称：" + clazz.getSimpleName());

        Method[] methods = clazz.getMethods();
        for(Method m : methods) {
            System.out.println(m);
        }

        Constructor<Hello> constructor = clazz.getConstructor();
        Hello hello = constructor.newInstance();

        Method stringM = clazz.getMethod("info", String.class);
        stringM.invoke(hello, "Nick");

        Method intM = clazz.getMethod("info", Integer.class);
        intM.invoke(hello, 2);

        Field field = clazz.getDeclaredField("str");
        field.setAccessible(true);
        System.out.println(field.get(field));

        // 操作数组
        Object strArr = Array.newInstance(String.class, 5);
        Array.set(strArr, 1, "sdje");


    }
}
