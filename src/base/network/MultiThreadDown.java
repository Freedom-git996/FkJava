package base.network;

/**
 * @Auther: Vectory
 * @Date: 2019/10/6
 * @Description: base.network
 * @version: 1.0
 */
public class MultiThreadDown {

    public static void main(String[] args) throws Exception {
        final DownUtil downUtil = new DownUtil("https://www.baidu.com/img/xinshouyedong_4f93b2577f07c164ae8efa0412dd6808.gif",
                "baidu.png",4);
        downUtil.download();
        new Thread(() ->{
           while(downUtil.getCompleteRate() < 1){
               System.out.println("已完成: " + downUtil.getCompleteRate());
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }).start();
    }
}
