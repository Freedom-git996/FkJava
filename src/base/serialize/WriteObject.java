package base.serialize;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteObject {

    public static void main(String[] args) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.txt"))) {
            Person person = new Person("孙悟空", 23);
            Person t1 = new Person("菩提祖师", 43, person);
            Person t2 = new Person("唐僧", 41, person);
            oos.writeObject(person);
            oos.writeObject(t1);
            oos.writeObject(t2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
