package base.reflect;

import broke.set2map.SimpleEntry;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect
 * @version: 1.0
 */
public class GenericTest {

    private static Map<String, Integer> score;

    public static Class<?> comparableClassFor(Object x) {
        if(x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if((c = x.getClass()) == String.class) {
                return c;
            }
            if((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; i++) {
                    if (((t = ts[i]) instanceof ParameterizedType)
                            && ((p = (ParameterizedType) t).getRawType() == Comparable.class)
                            && (as = p.getActualTypeArguments()) != null
                            && as.length == 1
                            && as[0] == c) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    public static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >=  (1 << 30)) ? (1 << 30) : n + 1;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Map<Integer, Map<String, Integer>> map = new HashMap<Integer,Map<String, Integer>>();
        System.out.println(GenericTest.comparableClassFor(map));

        Class<GenericTest> clazz = GenericTest.class;
        Field f = clazz.getDeclaredField("score");
        Class<?> a = f.getType();
        System.out.println("score的类型是：" + a);
        Type gType = f.getGenericType();
        if(gType instanceof ParameterizedType){
            ParameterizedType pType = (ParameterizedType)gType;
            Type rType = pType.getRawType();
            System.out.println("原始泛型是：" + rType);
            Type[] tArgs = pType.getActualTypeArguments();
            System.out.println("泛型信息是：");
            for(int i = 0; i < tArgs.length; i++){
                System.out.println("第" + i + "个泛型是：" + tArgs[i]);
            }
        }else{
            System.out.println("获取泛型出错");
        }

        int count = 15;
        System.out.println(tableSizeFor(15));
    }
}
