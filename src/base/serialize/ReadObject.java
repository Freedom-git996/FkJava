package base.serialize;

import pattern.build.singleton.demo.LazySingleton;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadObject {

    public static void main(String[] args) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"))) {
            Person person = (Person)ois.readObject();
            Person t1 = (Person)ois.readObject();
            Person t2 = (Person)ois.readObject();
            System.out.println(person);
            System.out.println(t1);
            System.out.println(t2);

            System.out.println(person == t1.getStudent());
            System.out.println(person == t2.getStudent());
            System.out.println(t1.getStudent() == t2.getStudent());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
