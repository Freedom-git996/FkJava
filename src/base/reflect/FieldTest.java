package base.reflect;

import java.lang.reflect.Field;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect
 * @version: 1.0
 */
public class FieldTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Person p = new Person();
        Class<Person> personClass = Person.class;
        Field nameField = personClass.getDeclaredField("name");
        nameField.setAccessible(true);
        nameField.set(p, "vectory");
        Field ageField = personClass.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(p, 21);
        System.out.println(p);
    }

    static class Person{

        private String name;
        private int age;

        @Override
        public String toString() {
            return "Person[name:" + name + ", age:" + age + "]";
        }
    }
}