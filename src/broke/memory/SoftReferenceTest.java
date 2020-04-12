package broke.memory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class SoftReferenceTest {

//    public SoftReferenceTest soft = new SoftReferenceTest();
//
//    public SoftReferenceTest() {
//        System.out.println("执行初始化");
//    }

    public static void main(String[] args) {
        SoftReference<Person>[] people = new SoftReference[100];
        for(int i = 0; i < people.length; i ++) {
            people[i] = new SoftReference<>(new Person("name:" + i, (i + 1) *4 % 100));
        }
        System.out.println(people[2].get());
        System.out.println(people[4].get());

        System.gc();
        System.runFinalization();

        System.out.println(people[2].get());
        System.out.println(people[4].get());

        String s = new String("疯狂Java");
        WeakReference<String> weakReference = new WeakReference<>(s);
        String obj = weakReference.get();
        if(obj == null) {

        }


//        SoftReferenceTest softReferenceTest = new SoftReferenceTest();
    }
}

class Person{
    String name;
    int age;
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person[name=" +name + ",age=" +age + "]";
    }
}