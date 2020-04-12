package base.reflect.proxy;

/**
 * @Auther: Vectory
 * @Date: 2019/10/20
 * @Description: base.reflect.proxy
 * @version: 1.0
 */
public class GunDog implements Dog {
    @Override
    public void info() {
        System.out.println("我是一只猎狗");
    }

    @Override
    public void run() {
        System.out.println("我奔跑迅速");
    }
}
