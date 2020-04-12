package base.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect.proxy
 * @version: 1.0
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DogUtil du = new DogUtil();
        du.method1();
        Object result = method.invoke(target, args);
        System.out.println(result);
        du.method2();
        return result;
    }
}
