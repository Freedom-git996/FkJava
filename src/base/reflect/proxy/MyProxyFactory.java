package base.reflect.proxy;

import java.lang.reflect.Proxy;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect.proxy
 * @version: 1.0
 */
public class MyProxyFactory {

    public static Object getProxy(Object target){
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
    }
}
