package pub.chenhuang.comm;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pub.chenhuang.comm.cal.DateUtil;
import pub.chenhuang.comm.cal.Lunar;
import pub.chenhuang.comm.http.HttpUtil;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by cjw on 2017/3/20.
 */
public class WeChatUtils {
    public static Map<String, String> getAddrMsg(InputStream in) {
        try {
            Document document = new SAXReader().read(in);
            Element root = document.getRootElement();
            Iterator<Element> it = root.elementIterator();
            Map<String, String> map = new HashMap<String, String>();
            while (it.hasNext()) {
                Element element = it.next();
                String name = element.getName();
                String text = element.getText();
                map.put(name, text);
            }
            return map;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendTianqiMsg(String x, String y) {

        String loc = getLocByLatlng(x, y);
        return null;
    }

    public static String getLocByLatlng(String x, String y) {
        String result = HttpUtil.getByUTF8("http://maps.google.cn/maps/api/geocode/json?latlng=" + x + "," + y + "&language=CN");
        System.out.println(result);
        Map<String, Object> map = new Gson().fromJson(result, Map.class);
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("results");
        List<Map<String, Object>> list1 = (List<Map<String, Object>>) list.get(0).get("address_components");
        String loc = "";
        for (Map<String, Object> objectMap : list1) {
            String long_name = (String) objectMap.get("long_name");
            if (long_name.endsWith("市")) {
                loc = long_name;
                break;
            }
        }
        return loc.substring(0, loc.lastIndexOf("市"));
    }

    public static String getWeatherByLoc(String loc) {
        try {
            Map<String,String> map=getWeatherByLocAndPianyi(loc,0);
            Map<String,String> map1=getWeatherByLocAndPianyi(loc,1);
            Map<String,String> map2=getWeatherByLocAndPianyi(loc,2);
            Map<String,String> map3=getWeatherByLocAndPianyi(loc,3);

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int month = cal.get(Calendar.MONTH); //注意月份是从0开始的,比如当前7月，获得的month为6
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String res = "今天" + (month + 1) + "月" + day + "日，" + DateUtil.getXingqi(new Date(), 0) +
                    "，" + Lunar.getLunar(new Date()) + "。\n 今天" + map.get("status1") + "转" + map.get("status2") + "  "
                    + map.get("temperature2") + "~" + map.get("temperature1") + "℃\n " +
                    "明天"+map1.get("status1")+"转"+map1.get("status2")+" "+map1.get("temperature2")+"~"+map1.get("temperature1")+"℃\n" +
                    " " + DateUtil.getZhou(new Date(), 2) +map2.get("status1")+ "，" + DateUtil.getZhou(new Date(), 3) + map3.get("status1");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Map<String, String> getWeatherByLocAndPianyi(String loc, int pianyi) {
        try {
            String result = HttpUtil.getByCharSet("http://php.weather.sina.com.cn/xml.php?city="
                    + URLEncoder.encode(loc, "gb2312") + "&password=DJOYnieT8234jlsK&day="+pianyi, "utf-8");
            Document document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            Element weather = iterator.next();
            Iterator<Element> elementIterator = weather.elementIterator();
            Map<String, String> map = Maps.newHashMap();
            while (elementIterator.hasNext()) {
                Element element = elementIterator.next();
                map.put(element.getName(), element.getText());
            }
            return map;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String result = HttpUtil.getByUTF8("http://maps.google.cn/maps/api/geocode/json?latlng=" + 120.1548 + "," + 121.1245 + "&language=CN");
        System.out.println(result);
//        System.out.println("");
//        System.out.println(HttpUtil.getByUTF8("http://php.weather.sina.com.cn/xml.php?city="+URLEncoder.encode("杭州","gb2312")+"&password=DJOYnieT8234jlsK&day=0"));
    }

}
