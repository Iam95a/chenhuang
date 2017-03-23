package pub.chenhuang.comm.constant;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by cjw on 2017/3/20.
 */
public class WeChatConstant {
    // ==== 读取配置文件 wechat.properties start... ==== //
    private static ResourceBundle WeChatProp = ResourceBundle.getBundle("wechat", Locale.getDefault());
    public  static final String AppId=WeChatProp.getString("AppId");
    public  static final String AppSecret=WeChatProp.getString("AppSecret");
    // ==== 读取配置文件 wechat.properties end... ==== //
}
