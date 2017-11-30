package com.itcast.dubbo.pro.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

/**
 * Created by zebon lu on 2017/6/1.
 * 百度地图api
 * 百度秘钥 ：lGuvTZMNDlRFUN1wrj9yHobWNGZGuSMe
 */
public class BaiduMap {


//    搜索
    public String search(){
        try {
//          安全秘钥和证书设置
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//          安全密钥存放位置
//            FileInputStream instream = new FileInputStream(new File("d:\\tomcat.keystore"));
//            加载秘钥
//            trustStore.load(instream, "123456".toCharArray());
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
//            只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
//            创建get请求
            HttpGet httpGet = new HttpGet("http://api.map.baidu.com/place/v2/search?q=饭店&region=北京&output=json&ak=lGuvTZMNDlRFUN1wrj9yHobWNGZGuSMe");
//            发送请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
