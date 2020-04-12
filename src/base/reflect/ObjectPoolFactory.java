package base.reflect;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect
 * @version: 1.0
 */
public class ObjectPoolFactory {

    private Map<String, Object> objectPool = new HashMap<>();
    private Properties config = new Properties();

    public void init(String fileName){
        try(FileInputStream fis = new FileInputStream(fileName)) {
            config.load(fis);
        } catch (Exception e) {
            System.out.println("读取" + fileName + "异常");
        }
    }

    private Object createObject(String clazzName) throws Exception{
        Class<?> clazz = Class.forName(clazzName);
        return clazz.getConstructor().newInstance();
    }

    public void initPool() throws Exception {
        for(String name : config.stringPropertyNames()){
            if(!name.contains("%")){
                objectPool.put(name, createObject(config.getProperty(name)));
            }
        }
    }

    public Object getObject(String name){
        return objectPool.get(name);
    }

    public void initProperty() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(String name : config.stringPropertyNames()){
            if(name.contains("%")){
                String[] objAndProp = name.split("%");
                Object target = getObject(objAndProp[0]);
                String mtdName = "set" + objAndProp[1].substring(0, 1).toUpperCase()
                        + objAndProp[1].substring(1);
                Class<?> targetClass = target.getClass();
                Method mtd = targetClass.getMethod(mtdName, String.class);
                mtd.invoke(target, config.getProperty(name));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ObjectPoolFactory of = new ObjectPoolFactory();
        of.init("obj.txt");
        of.initPool();
        of.initProperty();
        System.out.println(of.getObject("a"));
    }
}
