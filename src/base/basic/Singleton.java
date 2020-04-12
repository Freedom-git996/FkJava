package base.basic;

import java.io.*;

public class Singleton implements Serializable {

    private static Singleton instance;
    private String name;

    private Singleton(String name) {
        this.name = name;
    }

    public static Singleton getInstance(String name) {
        if(instance == null) {
            instance = new Singleton(name);
        }
        return instance;
    }

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance("灰太狼");

        Singleton singleton1 = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try{
            oos = new ObjectOutputStream(new FileOutputStream("object.txt"));
            ois = new ObjectInputStream(new FileInputStream("object.txt"));
            oos.writeObject(singleton);
            oos.flush();

            singleton1 = (Singleton)ois.readObject();

            System.out.println(singleton == singleton1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
