package base.stream;

import net.sf.cglib.core.Local;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LambdaTest {

    public static void main(String[] args) {
        List<String> strList1 = Arrays.asList("c", "c++", "c#", "java", "javaweb", "javascript", "python", "php", "lua");
        List<String> strList2 = Arrays.asList("mysql", "redis", "mongodb", "rocketmq", "kafka", "netty", "webservice");
        List<List<Integer>> intList1 = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9), Arrays.asList(1, 2, 3));
        LambdaTest lambdaTest = new LambdaTest();

        // 1、Java8新增接口默认方法
        Formula formula = a -> a + 1;
        double calculate1 = formula.calculate(1);

        // 2、排序
        strList1.sort(Comparator.comparingInt(String::length));

        // 3、函数式接口
        Action1 action1 = () -> System.out.println("test Action1");
        lambdaTest.testAction1(action1);
        Action2<String, Integer> action2 = (v) -> "test Action2";
        lambdaTest.testAction2(action2);

        // 4、Predicate接口
        lambdaTest.filter(strList1, (s) -> s.startsWith("j"));
        lambdaTest.filter(strList1, (s) -> true);
        lambdaTest.filter(strList1, (s) -> s.length() > 4);

        Predicate<String> predicate1 = (s) -> s.startsWith("j");
        lambdaTest.filter(strList1, predicate1.negate());

        Predicate<String> predicate2 = (s) -> s.startsWith("j");
        Predicate<String> predicate3 = (s) -> s.length() > 4;
        lambdaTest.filter(strList1, predicate2.or(predicate3));

        lambdaTest.filter(strList1, predicate2.and(predicate3));

        // 4、Function接口
        Function<String, String> functionBefore = (s) -> "briup_" + s;
        Function<Student, Integer> functionAfter = (stu) -> stu.getName().length();
        Function<String, Student> function = Student::new;
        Student student1 = function.compose(functionBefore).apply("Jerry");
        Integer nameLen = function.compose(functionBefore).andThen(functionAfter).apply("Jerry");

        // 5、Supplier
        Supplier<String> supplier = () -> {
            String base = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < 8; i++) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
            return sb.toString();
        };
        System.out.println(supplier.get());

        // 6、Consumer
        Consumer<String> consumer = (s) -> System.out.println("Hello, " + s);
        Consumer<String> consumerAfter = (s) -> System.out.println(s + ", what's up?");
        consumer.andThen(consumerAfter).accept(student1.getName());

        // 7、Optional
        Optional<String> op1 = Optional.of("Jerry");
        op1.ifPresent(System.out::println);
        System.out.println(op1.orElse("nobody"));

        Function<String, Integer> mapFunction = String::length;
        System.out.println(op1.map(mapFunction).get());

        System.out.println(op1.map(function.compose(functionBefore)).get());

        Function<String, Optional<Student>> flagMapFunction = (s) -> Optional.of(new Student(s));
        System.out.println(op1.flatMap(flagMapFunction.compose(functionBefore)).get());

        // Stream
        Stream<String> stream1 = Stream.of("hello", "world", "hello");
        Set<String> strSet1 = stream1.collect(Collectors.toSet());

        List<String> strList3 = strList2.stream().map(String::toUpperCase).collect(Collectors.toList());
        List<Integer> integerList1 = intList1.stream().flatMap(Collection::stream).map(i -> i * i).collect(Collectors.toList());
        List<String> strList4 = strList1.stream()
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("第一次符合条件的值为: " + e))
                .filter(e->e.length()>4)
                .peek(e -> System.out.println("第二次符合条件的值为: " + e))
                .collect(Collectors.toList());

        Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));
        System.out.println();

        List<String> list =Arrays.asList("test","javap","hello","world","java","tom","C","javascript");
        Optional<String> reduce = list.stream()
                .sorted((s1,s2)->s2.length()-s1.length())
                .filter(s->s.startsWith("j"))
                .map(s->s+"_briup")
                .reduce((s1,s2)->s1+" | "+s2);

        // Map新操作
        Map<String, Integer> map = new HashMap<>();
        map.put("English", 80);
        map.put("Math", 79);
        map.put("Chinese", 92);

        map.computeIfPresent("English", (k, v) -> v += 5);
        map.putIfAbsent("Science", 80);

        map.forEach((k, v) -> System.out.println(map.get(k)));

        // Collectors 分组
        List<String> list2 =Arrays.asList("test","javap","hello","world","java","tom","C","javascript");
        Map<Boolean, List<String>> map1 = list2.stream().collect(Collectors.groupingBy(s -> s.startsWith("j")));

        List<String> list3 =Arrays.asList("test","javap","hello","world","java","tom","C","javascript");
        Map<Boolean, List<String>> map2 = list3.stream().collect(Collectors.partitioningBy(s -> s.startsWith("j")));

        // LocalDate|Time
        LocalDate todayDate = LocalDate.now();
        LocalTime todayTime = LocalTime.now();
        LocalDateTime today = LocalDateTime.now();

        LocalDate ramdonDate = LocalDate.of(2018, 8, 19);
        LocalTime ramdonTime = LocalTime.of(12, 35, 18);
        LocalDateTime ramdonDateTime = LocalDateTime.of(ramdonDate, ramdonTime);

        System.out.println(today.getMonth().equals(ramdonDateTime.getMonth()));

        MonthDay birthDay = MonthDay.of(ramdonDate.getMonth(), ramdonDate.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);
        if (birthDay.equals(currentMonthDay)) System.out.println("today is birthDay!");

        YearMonth creditCardExpiry  = YearMonth.now();
        int days = creditCardExpiry.lengthOfMonth();

        System.out.println(todayDate.isLeapYear());

        LocalDateTime twoHoursLater = today.plusHours(2);
        LocalDateTime thirtyDaysLater = today.plusDays(30);
        LocalDateTime twoWeeksLater = today.plus(2, ChronoUnit.WEEKS);

        LocalDateTime twoHoursBefore = today.minusHours(2);
        LocalDateTime thirtyDaysBefore = today.minusDays(30);
        LocalDateTime twoWeeksBefore = today.minus(2, ChronoUnit.WEEKS);

        System.out.println(twoHoursLater.isAfter(twoHoursBefore));
        System.out.println(twoHoursBefore.isBefore(thirtyDaysLater));

        System.out.println(todayDate);
        System.out.println(todayDate.plusWeeks(2));
        Period period = Period.between(todayDate, todayDate.plusWeeks(2));
        System.out.println(period.getDays());
        System.out.println(period.getMonths());

        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime localtDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork  = ZonedDateTime.of(localtDateAndTime, america );

        // Clock
        Clock clock = Clock.systemUTC();
        LocalDateTime clockDateTime = LocalDateTime.now(clock);

        // Instant
        Instant timestamp = Instant.now();

        // formatter
        String time1 = "20200819";
        LocalDate fomatted1 = LocalDate.parse(time1, DateTimeFormatter.BASIC_ISO_DATE);
        String time2 = "2011-12-03T10:15:30";
        LocalDateTime fomatted2 = LocalDateTime.parse(time2, DateTimeFormatter.ISO_DATE_TIME);

        DateTimeFormatter fomatted3 = DateTimeFormatter.ofPattern("MM dd yyyy");
        String str1 = fomatted1.format(fomatted3);
        DateTimeFormatter fomatted4 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String str2 = fomatted2.format(fomatted4);


        System.out.println("================ end ================");
    }

    private void filter(List<String> list, Predicate<String> predicate) {
        for (String str : list) {
            if (predicate.test(str)) {
                System.out.print(str + " ");
            }
        }
        System.out.println();
    }

    private void testAction1(Action1 action1) {
        action1.run();
    }

    private void testAction2(Action2<String, Integer> action2) {
        System.out.println(action2.run(3));
    }

    // 函数式接口只能拥有一个抽象方法
    @FunctionalInterface
    interface Action1 {
        void run();

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    @FunctionalInterface
    interface Action2<T, F> {
        T run(F f);
    }

    public interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(a);
        }
    }

    private static class Student {
        private String name;

        public Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "The student's name is " + name;
        }
    }
}