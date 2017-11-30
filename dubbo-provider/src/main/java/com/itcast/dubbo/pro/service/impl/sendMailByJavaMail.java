package com.itcast.dubbo.pro.service.impl;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by zebon lu on 2017/6/1.
 */
public class sendMailByJavaMail {

    public void sendMail() {
        try {
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
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
