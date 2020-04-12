package base.reflect.proxy;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect.proxy
 * @version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        Dog target = new GunDog();
        Dog dog = (Dog)MyProxyFactory.getProxy(target);
        dog.info();
        dog.run();
    }
}
