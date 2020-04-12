package base.lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: Vectory
 * @Date: 2019/10/13
 * @Description: base.lambda
 * @version: 1.0
 */
public class PersonFactory {

    // 测试初始化，生成指定的测试示例个数
    public List<Person> getPersons(int personNum) {
        List<Person> list = new ArrayList<>();
        String names[] = { "军", "荣", "莉", "轩", "兴", "馨", "云浩", "欣兰" };
        String firstNames[] = { "张", "陈", "王", "李", "蒲", "吴", "郝", "林" };
        for (int i = 0; i < personNum; i++) {
            int fn = (int) (Math.random() * firstNames.length);
            int n = (int) (Math.random() * names.length);
            int age = (fn + 1) * (n + 1);
            Person p = new Person();
            p.setAge(age);
            p.setFirstName(firstNames[fn]);
            p.setName(names[n]);
            p.setGen(age % 3 == 0 ? "女" : "男");
            list.add(p);
        }
        return list;
    }

    // 传统迭代器统计人数
    public int countMan(List<Person> personList){
        return (int)personList.stream().filter(person -> {
            return person.getGen().equals("男");
        }).count();
    }

    // 截取字串，年龄大于7的
    public List<Person> subList(List<Person> personList){
        return personList.stream().filter(person -> {
            return person.getAge() > 8;
        }).collect(Collectors.toList());
    }

    // 测试示例
    public void longChainTest(List<Person> personList) {
        List<Integer> ages = new ArrayList<>();
        personList.stream().forEach(person -> ages.add(person.getAge()));
        System.out.println(ages);
        ages.clear();
        personList.stream().map(person -> {
            person.setAge(person.getAge() + 10);
            return person;
        }).count();
        personList.stream().forEach(person -> ages.add(person.getAge()));
        System.out.println(ages);
    }

    public static void main(String[] args) {
        PersonFactory personFactory = new PersonFactory();
        List<Person> personList = personFactory.getPersons(8);
        System.out.println("personList toString: " + personList);
        System.out.println("--------------------------------");

        System.out.println("personList 中男性的数量: " + personFactory.countMan(personList));
        System.out.println("--------------------------------");

        System.out.println("personList 中年龄大于7的: " + personFactory.subList(personList));
        System.out.println("--------------------------------");

        IntSummaryStatistics intSummaryStatistics = personList.stream().mapToInt(Person::getAge).summaryStatistics();
        System.out.println("personList 中年龄的最大值: " + intSummaryStatistics.getMax());
        System.out.println("--------------------------------");

        personFactory.longChainTest(personList);
        System.out.println("--------------------------------");

        List<String> names = new ArrayList<>();
        personList.stream()
                .map(person -> {
                    person.setAge(person.getAge());
                    return person;
                })
                .filter(person -> person.getAge() > 8)
                .filter(person -> person.getGen().equals("男"))
                .forEach(person -> names.add(person.getFirstName()));
        System.out.println("personList 的链式调用: " + names);
        System.out.println("--------------------------------");

        List<String> newLists = personList.stream()
                .flatMap(person -> Stream.of(person.getName(), person.getGen()))
                .collect(Collectors.toList());
        System.out.println("personList 获取姓名和性别: " + newLists);
        System.out.println("--------------------------------");

        personList.stream().
                filter(person -> person.getGen().equals("男"))
                .sorted(Comparator.comparingInt(Person::getAge))
                .forEach(System.out::println);

        List<String> lowerCase = new ArrayList<>();
        lowerCase.add("alpha");
        lowerCase.add("beta");
        lowerCase.add("cool");
        lowerCase.add("delta");

        lowerCase.stream().map(string -> string.toUpperCase()).count();
        System.out.println("lowerCase 打印为小写: " + lowerCase);

        List<String> upCase = lowerCase.stream().map(String::toUpperCase).collect(Collectors.toList());
        upCase.stream().forEach(System.out::println);
        System.out.println();
        System.out.println("--------------------------------");

        List<Integer> collected0 = new ArrayList<>();
        collected0.add(1);
        collected0.add(3);
        collected0.add(5);
        List<Integer> collected1 = new ArrayList<>();
        collected1.add(2);
        collected1.add(4);
        List<Integer> collected3 = Stream.of(collected0, collected1).flatMap(Collection::stream).collect(Collectors.toList());
        for(Integer i : collected3)
            System.out.print(i);
        System.out.println();
        System.out.println("--------------------------------");

        System.out.println("collected3 的元素总和: " + collected3.stream().reduce(0, Integer::sum));
        System.out.println("--------------------------------");
    }

    class Person {
        private int age;
        private String name;
        private String firstName;
        private String gen;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getGen() {
            return gen;
        }

        public void setGen(String gen) {
            this.gen = gen;
        }

        @Override
        public String toString() {
            return "\n["+ firstName + name + " age: " + age + " gender: " + gen + "]\n";
        }
    }
}