package base.anntation;

import java.lang.annotation.*;

@Repeatable(FKTags.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FKTag {
    String name() default "test";
    int age() default 100;
}
