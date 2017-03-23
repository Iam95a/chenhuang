package pub.chenhuang.comm.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cjw on 2016/10/26.
 */
public class HttpUtil {
    public static String postJson(String url, String param, Map<String, String> headers) {
        String string = "";
        try {
            DefaultHttpClient httpclient = null;
            if (url.startsWith("https")) {
                httpclient = new SSLClient();
            } else {
                httpclient = new DefaultHttpClient();
            }
            HttpPost httpPost = new HttpPost(url);
            if(headers!=null) {
                for (String s : headers.keySet()) {
                    httpPost.setHeader(s, headers.get(s));
                }
            }
            StringEntity entity = new StringEntity(param, "utf-8");
            httpPost.setEntity(entity);
            HttpResponse response2 = httpclient.execute(httpPost);
            try {
                HttpEntity entity2 = response2.getEntity();
                string = EntityUtils.toString(entity2);
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String post(String url, Map<String, String> params) {
        String string = "";
        try {
            DefaultHttpClient httpclient = null;
            if (url.startsWith("https")) {
                httpclient = new SSLClient();
            } else {
                httpclient = new DefaultHttpClient();
            }
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            Set<String> keys = params.keySet();
            for (String key : keys) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            HttpResponse response2 = httpclient.execute(httpPost);

            try {
//                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                string = EntityUtils.toString(entity2, "utf-8");
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static String getByCharSet(String url, String charSet){
        return  get(url,charSet);
    }
    public static String getByUTF8(String url){
        return  get(url,"utf-8");
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @return String类型的返回信息
     */
    private static String get(String url, String charSet) {
        String string = "";
        try {
            DefaultHttpClient httpclient = null;
            if (url.startsWith("https")) {
                httpclient = new SSLClient();
            } else {
                httpclient = new DefaultHttpClient();
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response1 = httpclient.execute(httpGet);

            try {
                HttpEntity entity = response1.getEntity();

                string = EntityUtils.toString(entity, charSet);
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 发送get请求 不带参数
     *
     * @param url
     * @return
     */
    public static String getWithNoParams(String url) {
        String string = "";
        try {

            DefaultHttpClient httpclient = null;
            if (url.startsWith("https")) {
                httpclient = new SSLClient();
            } else {
                httpclient = new DefaultHttpClient();
            }
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response1 = httpclient.execute(httpGet);

            try {
//                System.out.println(response1.getStatusLine());
                HttpEntity entity = response1.getEntity();

                string = EntityUtils.toString(entity, "utf-8");
            } finally {
                httpclient.getConnectionManager().shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
}
