package base.basic;

public class Person {

    private final Name name;

    public Person(Name name) {
        this.name = new Name(name.getFirstName(), name.getLastName());
    }

    public Name getName() {
        return new Name(name.getFirstName(), name.getLastName());
    }

    public static void main(String[] args) {
        Name n = new Name("悟空", "孙");
        Person p = new Person(n);
        System.out.println(p.getName().getFirstName());

        n.setFirstName("行者");
        System.out.println(p.getName().getFirstName());

//        String str1 =  "Hello";
//        String str2 = "World";
//        String str3 = "HelloWorld";
//        String str4 = "Hello" + "World";
//        System.out.println(str3 == (str1 + str2));
//        System.out.println(str3 == str4);
    }
}

class Name {

    private String firstName;
    private String lastName;

    public Name() {

    }

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}