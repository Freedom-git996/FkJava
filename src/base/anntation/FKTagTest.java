package base.anntation;

@FKTag(age = 5)
@FKTag(name = "疯狂Java", age = 9)
public class FKTagTest {

    public static void main(String[] args) {
        Class<FKTagTest> clazz = FKTagTest.class;
        FKTag[] tags = clazz.getDeclaredAnnotationsByType(FKTag.class);
        for(FKTag tag : tags) {
            System.out.println(tag.name() + "--->" + tag.age());
        }

        // 使用传统获取容器的方式
        FKTags container = clazz.getDeclaredAnnotation(FKTags.class);
        System.out.println(container);
    }
}
