package com.wind.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


/**
 * 邮件发送工具
 * 1．SMTP（递送邮件机制）
 * 简单邮件传输协议
 * SMTP服务器将邮件转发到接收者的SMTP服务器，直至最后被接收者通过POP或者IMAP协议获取。
 * 2．POP（获取邮件机制）
 * 邮局协议，目前为第3个版本POP3
 * 3．IMAP（多目录共享）
 * 接收信息的高级协议，目前版本为第4版IMAP4
 * 接收新信息，将这些信息递送给用户，维护每个用户的多个目录。
 * 4．MIME
 * 邮件扩展内容格式：信息格式、附件格式等等
 * 5．NNTP
 * 第三方新闻组协议
 * @author wind
 *
 */
public class EmailUtil {
	
	/**
	 * 发送邮件
	 * @param email 邮件信息
	 */
	public static void sendEmail(Email email) {
		// 邮件配置信息
		Properties props = email.getProps();
		String sender = email.getSender();
		String receiver = email.getReceiver();
		String title = email.getTitle();
		String content = email.getContent();
		String username = email.getUsername();
		String password = email.getPassword();
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		// 根据属性新建一个邮件会话
		Session session = Session.getInstance(props, authenticator);
		session.setDebug(false);
		List<DataSource> attachments = email.getAttachments();
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			// 设置发件人的地址
			message.setFrom(new InternetAddress(sender));
			// 设置收件人,并设置其接收类型为TO
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			// 设置标题
			message.setSubject(title);
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart("mixed");
            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content,"text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);
            //BASE64Encoder enc = new BASE64Encoder(); 
            for(DataSource attach : attachments){
            	// 添加附件
                BodyPart msgAttach = new MimeBodyPart();
                // 添加附件的内容
				msgAttach.setDataHandler(new DataHandler(attach));
                // 添加附件的标题
				msgAttach.setFileName(MimeUtility.encodeText(attach.getName()));
                multipart.addBodyPart(msgAttach);
            }
            // 将multipart对象放到message中
            message.setContent(multipart);
			// 设置发信时间
			message.setSentDate(new Date());
			// 存储邮件信息
			message.saveChanges();
			// 发送邮件
			Transport.send(message);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.host", "smtp.163.com");
		String sender = "*********@163.com";
		String receiver = "*********@qq.com";
		String title = "你好、Hello Mail";
		String content = "Hello World!!!";
		String username = "*********@163.com";
		String password = "*********";
		List<DataSource> attachments = new ArrayList<>();
		DataSource dataSource1 = new FileDataSource("src/main/resources/image/你好.txt");
		DataSource dataSource2 = new FileDataSource("src/main/resources/image/code.png");
		attachments.add(dataSource1);
		attachments.add(dataSource2);
		Email email = new Email(props, sender, username, password, receiver, title, content, attachments);
		EmailUtil.sendEmail(email);
	}
}
