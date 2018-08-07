package com.k12.common.util.mail;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MailUtils {
	
	private Logger logger = LoggerFactory.getLogger(MailUtils.class);
	
	/**
	 * 发送邮件
	 * @param mailInfo 邮件基本信息
	 * @param file 如果有附件，从参数带入
	 * @return
	 */
	public boolean sendMail(MailInfo mailInfo, List<File> file){
		boolean result = true;
		try{
			Session mailSession = getMailSession(mailInfo, false);
			// 创建邮件消息
	        MimeMessage message = new MimeMessage(mailSession);
	        // 设置发件人
	        InternetAddress form = new InternetAddress(mailInfo.getFromAddress());
	        message.setFrom(form);
	        // 设置收件人
	        String[] toArray = mailInfo.getToAddress().split(",");
	        InternetAddress[] toList = new InternetAddress[toArray.length];
	        for(int i=0; i<toArray.length; i++){
	        	toList[i] = new InternetAddress(toArray[i]);
	        }
	        message.setRecipients(Message.RecipientType.TO, toList);
	        // 设置主题
	        message.setSubject(mailInfo.getSubject());    
	        // 设置邮件消息发送的时间    
	        message.setSentDate(new Date());   
	      
	        if (file != null && file.size() >0) {
	         // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
                Multipart mainPart = new MimeMultipart();
             // 创建一个包含HTML内容的MimeBodyPart
                BodyPart html = new MimeBodyPart(); 
                // 设置HTML内容    
                html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");    
                mainPart.addBodyPart(html); 
                BodyPart attachmentBodyPart = null;
                //  为邮件添加附件
    	        for (File obj : file) { 
    	            if(obj != null){   
    	                attachmentBodyPart = new MimeBodyPart();
    	                DataSource source = new FileDataSource(obj);
    	                attachmentBodyPart.setDataHandler(new DataHandler(source));
    	                //MimeUtility.encodeWord可以避免文件名乱码
    	                attachmentBodyPart.setFileName(MimeUtility.encodeWord(obj.getName()));
    	                mainPart.addBodyPart(attachmentBodyPart);  
    	            }
    	            
    	        }  
    	        // 将MiniMultipart对象设置为邮件内容     将multipart对象放到mailMessage中
                message.setContent(mainPart);
	        }else{
	         // 设置邮件内容  common mail. no attachment.
                message.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
	        }
	        
			// 保存邮件
			message.saveChanges();

			// 发送邮件
	        Transport.send(message);
	        logger.info("toAddress:"+mailInfo.getToAddress());
	        logger.info("邮件发送成功.");
		}catch(Exception e){
			e.printStackTrace();
			result = false;
			logger.warn("邮件发送失败~");
		}
		return result;
	}
	
	private Session getMailSession(MailInfo mailInfo, boolean isQQ){
		final Properties props = new Properties();
		/*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        if(isQQ){
	        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.smtp.port", "465");
	        props.setProperty("mail.smtp.socketFactory.port", "465");
        }
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", mailInfo.isValidate());
        props.put("mail.smtp.host", mailInfo.getMailServerHost());
        // 发件人的账号
        props.put("mail.user", mailInfo.getUserName());
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", mailInfo.getPassword());

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        return mailSession;
	}
}
