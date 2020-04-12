package base.timeopr;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarOpr {

    public static void main(String[] args) throws Exception {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        System.out.println("当前年份 ---> " + calendar.get(Calendar.YEAR));
        System.out.println("当前月份(月份计数从0开始) ---> " + calendar.get(Calendar.MONTH));
        System.out.println("当前日期 ---> " + calendar.get(Calendar.DATE));
        System.out.println("当前时间在一年中的第几天 ---> " + calendar.get(Calendar.DAY_OF_YEAR));
        System.out.println("当前时间在一月中的第几天 ---> " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("当前时间在一月中的第几周 ---> " + calendar.get(Calendar.WEEK_OF_MONTH));
        System.out.println("当前时间在一周中的第几天(从星期天开始计数) ---> " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("当前几点(12小时制) ---> " + calendar.get(Calendar.HOUR));
        System.out.println("当前几点(24小时制) ---> " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("当前是上午还是下午 ---> " + calendar.get(Calendar.AM_PM));
        System.out.println("本月最大天数 ---> " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("本月最小天数 ---> " + calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println("一天中最大的点数 ---> " + calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        System.out.println(calendar.getFirstDayOfWeek());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime()));

        // 2019-01-01 00:00:00
        // 设置：2019年00月  --> 实际：2019年01月
        // 设置：2019年-1月  --> 实际：2018年12月, 以此类推
        // 设置：2月00日  --> 实际：1月31日
        // 设置：2月-1日  --> 实际：1月30日, 以此类推
        // 设置：1小时-1分钟  --> 实际：0小时59分钟
        // 设置：1分钟-1秒钟  --> 实际：0分钟59秒
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR,2020);
        ca.set(Calendar.MONTH,1);
        ca.set(Calendar.DATE,31);
        ca.set(Calendar.HOUR_OF_DAY,12);
        ca.set(Calendar.MINUTE,23);
        ca.set(Calendar.SECOND,43);
        System.out.println(sdf.format(ca.getTime()));
        System.out.println("当前时间在一月中的第几周 ---> " + calendar.get(Calendar.WEEK_OF_MONTH));

        System.out.println(calendar.before(ca));

        ca.roll(Calendar.MONTH, false);
        System.out.println(sdf.format(ca.getTime()));
        ca.roll(Calendar.MONTH, true);
        System.out.println(sdf.format(ca.getTime()));

        System.out.println("------ 获取某月所有日期 ------");
        monthToDates(calendar.getTime()).forEach(e -> {
            System.out.println(sdf.format(e));
        });
    }


    public static List<Date> monthToDates(Date dt){
        //创建list列表
        List<Date> dts = new ArrayList<Date>();
        //创建日历
        Calendar ca = Calendar.getInstance();
        //设置时间
        ca.setTime(dt);
        //将时间调整到当前日历月份最后一天
        ca.set(ca.get(Calendar.YEAR),ca.get(Calendar.MONTH)+1, 0);
        //获取当前日历月份的实际总天数
        int dayNumOfMonth = ca.get(Calendar.DAY_OF_MONTH);
        //将日历时间指向当前日历月份的一号
        ca.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 0; i < dayNumOfMonth; i++, ca.add(Calendar.DATE, 1)) {
            //设置为0时0分0秒0毫秒
            ca.set(Calendar.HOUR_OF_DAY, 0);
            ca.set(Calendar.MINUTE, 0);
            ca.set(Calendar.SECOND, 0);
            ca.set(Calendar.MILLISECOND, 0);
            //将Calendar日期对象转换为Date时间对象，保存到提前创建的列表中
            Date d = ca.getTime();
            dts.add(d);
        }
        return dts;
    }
}
