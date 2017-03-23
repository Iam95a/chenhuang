package pub.chenhuang.comm.cal;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by cjw on 2017/3/21.
 */
public class DateUtil {
    public static String getXingqi(Date date,int pianyi){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        w+=pianyi;
        if(w>6){
            w=w-7;
        }
        return weekDays[w];
    }
    public static String getZhou(Date date,int pianyi){
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        w+=pianyi;
        if(w>6){
            w=w-7;
        }
        return weekDays[w];
    }
}
