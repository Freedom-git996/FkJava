package base.serialize;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Person implements Serializable {

    private String name;
    private int age;
    private Person student;

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeObject(new StringBuffer(name).reverse());
        out.writeInt(age);
    }

    private void readObject(java.io.ObjectInputStream ois) throws IOException, ClassNotFoundException {
        this.name = ((StringBuffer)ois.readObject()).reverse().toString();
        this.age = ois.readInt();
    }

    private Object readResolve() throws ObjectStreamException {
        // 在序列化单例类、枚举类时使用
        return null;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, Person student) {
        this.name = name;
        this.age = age;
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Person[name:" + name + ",age:" + age + "]";
    }
}
