package com.itcast.dubbo.web.Utils;

import com.itcast.dubbo.ser.annotation.SheetHeader;
import com.itcast.dubbo.web.entity.School;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by zebon lu on 2017/6/26.
 */
public class MyUtils {

    private CloseableHttpClient client;

    //   生成随机六位数
    
    public void testRandomNumber() {
        String a = "0123456789";
        char[] rands = new char[6];
        for (int i = 0; i < rands.length; i++) {
//            生成随机的double类型的数字
            double v = Math.random();
//            和a的长度相乘
            double v1 = v * a.length();
//            double转化为int
            int rand = (int) (v1);
//            赋值
            rands[i] = a.charAt(rand);
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < rands.length; i++) {
            stringBuffer.append(rands[i]);
        }
        System.out.println("----------" + stringBuffer);
    }

    //    spring mail 发送邮件  （对java mail进行封装）
    
    public void springMailTest() throws MessagingException, UnsupportedEncodingException {
        //邮件发送对象
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        //邮件发送方服务器地址
        senderImpl.setHost("smtp.163.com");
        Properties prop = new Properties();
        prop.put(" mail.smtp.auth ", " true "); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put(" mail.smtp.timeout ", " 25000 ");
        senderImpl.setUsername("m17701848923@163.com");
        senderImpl.setPassword("19920312lzp"); //授权码
        senderImpl.setJavaMailProperties(prop);
//       1 mimeMessageHelper（发送复杂的信息 包括附件、图片）
        MimeMessage mimeMessage = senderImpl.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
//        MimeMessage
//        发件人
        mimeMessageHelper.setFrom(new InternetAddress("m17701848923@163.com"));
//        收件人
        mimeMessageHelper.setTo(new InternetAddress("2285349248@qq.com"));
//        主题
        mimeMessageHelper.setSubject("您的 Apple ID 被用于在 Web 浏览器上登录 iCloud");
//        邮件内容。
//        文本内容
        mimeMessageHelper.setText("文本内容");
//        附件内容
        File file = new File("D:\\接口文档.txt");
        mimeMessageHelper.addAttachment(file.getName(), new FileDataSource(file));

//      2 SimpleMailMessage （发送简单的信息 不包括附件等）
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("m17701848923@163.com");
        simpleMailMessage.setTo("2285349248@qq.com");
//       设置主题时不要用 测试、马化腾。。。此类的无用或者敏感词汇 ，否则会被qq邮箱默认扔到垃圾箱
        simpleMailMessage.setSubject("您的 Apple ID 被用于在 Web 浏览器上登录 iCloud");
        simpleMailMessage.setText("网易邮箱给qq邮箱的一封信");

        senderImpl.send(mimeMessage);


    }

    // java mail发送邮件
    
    public void javaMailTest() throws UnsupportedEncodingException, MessagingException {
//        邮件发送服务器的属性
        Properties properties = new Properties();
//        设置发送方服务器地址
        properties.put("mail.smtp.host", "smtp.163.com");
//        需要经过授权 才能发送（用户名 密码）
        properties.put("mail.smtp.auth", "true");
//        构建session
        Session session = Session.getDefaultInstance(properties);
//        调试用
        session.getDebug();
//        创建消息对象
        MimeMessage mimeMessage = new MimeMessage(session);
//        发件人
        mimeMessage.setFrom(new InternetAddress("m17701848923@163.com"));
//        收件人
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress("2285349248@qq.com"));
//        主题
        mimeMessage.setSubject("您的 Apple ID 被用于在 Web 浏览器上登录 iCloud");
//        邮件内容。
        MimeMultipart mimeMultipart = new MimeMultipart();
//        文本内容
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("文本内容");
//        附件内容
        MimeBodyPart attachBodyPart = new MimeBodyPart();
        File file = new File("D:\\接口文档.txt");
        attachBodyPart.setDataHandler(new DataHandler(new FileDataSource(file)));
        mimeMessage.setContent(mimeMultipart);
        // 添加附件的标题
        // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
        sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
        attachBodyPart.setFileName(MimeUtility.encodeText(file.getName().replaceAll("\r", "").replaceAll("\n", "")));
        mimeMultipart.addBodyPart(attachBodyPart);
        // 将multipart对象放到message中
        mimeMessage.setContent(mimeMultipart);
        // 保存邮件
        mimeMessage.saveChanges();
        // 发送邮件
        Transport transport = session.getTransport("smtp");
        // 连接服务器的邮箱 （ 这里的password是指 授权码 不是邮箱的密码 ）
        transport.connect("smtp.163.com", "m17701848923@163.com", "19920312lzp");
        // 把邮件发送出去
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }


    /**
     * 导出excel表格  by jxl
     *
     * @throws IOException
     * @throws WriteException
     */
    
    public void jxlTest() throws IOException, WriteException {
        String fileName = "excel导出测试" + ".xls";
        File file = new File("d:\\" + fileName);
        if (file.exists()) {
            file.delete();
        }
        OutputStream os = new FileOutputStream(file);
        School school1 = new School("清华大学", "计算机专业", "高3", new Date());
        School school2 = new School("北京大学", "法律专业", "中2", new Date());
        School school3 = new School("北京理工大学", "金融专业", "中1", new Date());
        School school4 = new School("南京理工大学", "电子专业", "低1", new Date());
        School school5 = new School("南京大学", "计算机专业", "低2", new Date());
        School school6 = new School("复旦大学", "通讯专业", "中3", new Date());
        List<School> list = new ArrayList<>();
        list.add(school1);
        list.add(school2);
        list.add(school3);
        list.add(school4);
        list.add(school5);
        list.add(school6);
        WritableWorkbook workbook = WriteToeExcel(list, School.class, os);

//        Label xuexiao = new Label(0,0,"学校");
//        sheet.addCell(xuexiao);
//        Label zhuanye = new Label(1,0,"专业");
//        sheet.addCell(zhuanye);
//        Label jingzhengli = new Label(2,0,"专业竞争力");
//        sheet.addCell(jingzhengli);

        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        os.close();
    }

    private WritableWorkbook WriteToeExcel(List<? extends Object> list, Class<? extends Object> clazz, OutputStream os) {
        WritableWorkbook workbook = null;
        try {
//        创建工作簿
            workbook = Workbook.createWorkbook(os);
//        创建新页
            WritableSheet sheet = workbook.createSheet("first sheet", 0);
//        表头
            Label labelTopic = null;
//        表内容
            Label labelContent = null;
//        处理表头
//        获取类的所有字段
            Field[] declaredFields = clazz.getDeclaredFields();
//        遍历
            for (int i = 0; i < declaredFields.length; i++) {
                //            获取指定注解
                SheetHeader sheetHeader = declaredFields[i].getAnnotation(SheetHeader.class);
                labelTopic = new Label(sheetHeader.index(), 0, sheetHeader.comments());
                sheet.addCell(labelTopic);
            }
//            处理表内容
            for (int i = 0; i < list.size(); i++) {
                Object o = list.get(i);
                for (int j = 0; j < declaredFields.length; j++) {
                    Field field = declaredFields[j];
                    SheetHeader sheetHeader = field.getAnnotation(SheetHeader.class);
//                    获取字段类型
                    Class<?> type = field.getType();
//                    获取get方法名
                    String fieldName = field.toString().substring(field.toString().lastIndexOf(".") + 1);
                    fieldName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                    调用get获取值
                    Method method = clazz.getDeclaredMethod(fieldName);
//                    处理时间类型
                    SimpleDateFormat simpleDateFormat = null;
                    if (type.toString().endsWith("Date")) {
                        simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        labelContent = new Label(sheetHeader.index(), i + 1, simpleDateFormat.format(method.invoke(o)));
                    } else {
                        labelContent = new Label(sheetHeader.index(), i + 1, method.invoke(o).toString());
                    }
                    sheet.addCell(labelContent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }


    //百度地图api
    //百度秘钥 ：lGuvTZMNDlRFUN1wrj9yHobWNGZGuSMe
    
    public void baiduMapTest() {
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
            System.out.println("-----------" + EntityUtils.toString(entity, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    高德地图api
//     key d023956431ce240b82d760958a937b94
//    私钥 bb3cb22394f78347560d21da342242fd
    
    public void gaodeMapTest() {
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
            HttpGet httpGet = new HttpGet("http://restapi.amap.com/v3/place/text?key=d023956431ce240b82d760958a937b94&keywords=北京大学&types=高等院校&city=北京&children=1&offset=20&page=1&extensions=all");
//            发送请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println("-----------" + EntityUtils.toString(entity, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
