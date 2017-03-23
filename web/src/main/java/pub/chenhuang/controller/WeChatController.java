package pub.chenhuang.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.chenhuang.comm.WeChatUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by cjw on 2017/3/20.
 */
@RequestMapping("/wechat")
@Controller
public class WeChatController {
    private static Logger LOG = Logger.getLogger(WeChatController.class);

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    @ResponseBody
    public void getConnect(HttpServletRequest request, HttpServletResponse response,
                           String signature, String timestamp, String nonce, String echostr) {
        if (echostr != null && echostr.length() > 0) {
            try {
                response.getWriter().print(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    @ResponseBody
    public void getConnectPost(HttpServletRequest request, HttpServletResponse response,
                               String signature, String timestamp, String nonce) {
        response.setCharacterEncoding("utf-8");
        try {
            InputStream in = request.getInputStream();
            Map<String, String> map = WeChatUtils.getAddrMsg(in);
            String msgType=map.get("MsgType");
            String ToUserName = map.get("ToUserName");
            String FromUserName = map.get("FromUserName");
            if(msgType.equals("location")) {
                String x = map.get("Location_X");
                String y = map.get("Location_Y");
                String loc = WeChatUtils.getLocByLatlng(x, y);
                String weather = WeChatUtils.getWeatherByLoc(loc);
                String msg = "<xml> " +
                        "<ToUserName><![CDATA[" + FromUserName + "]]></ToUserName>" +
                        " <FromUserName><![CDATA[" + ToUserName + "]]></FromUserName> " +
                        "<CreateTime>" + (new Date().getTime() / 1000) + "</CreateTime>" +
                        " <MsgType><![CDATA[text]]></MsgType>" +
                        " <Content><![CDATA[" + weather + "]]></Content>" +
                        " </xml>";
                response.getWriter().print(msg);
            }else if(msgType.equals("event")){
                String event=map.get("Event");
                //关注事件
                if(event.equals("subscribe")){
                    String msg = "<xml> " +
                            "<ToUserName><![CDATA[" + FromUserName + "]]></ToUserName>" +
                            " <FromUserName><![CDATA[" + ToUserName + "]]></FromUserName> " +
                            "<CreateTime>" + (new Date().getTime() / 1000) + "</CreateTime>" +
                            " <MsgType><![CDATA[text]]></MsgType>" +
                            " <Content><![CDATA[" + "感谢您关注订阅号，发送地理位置可以获取当地天气" + "]]></Content>" +
                            " </xml>";
                    response.getWriter().print(msg);
                }
            } else{
                response.getWriter().print("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
