package com.itcast.dubbo.pro.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by zebon lu on 2017/6/1.
 */
public class sendMailBySpringmail  {

    public void sendMail(){
        try {
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
