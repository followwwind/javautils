package com.wind.bean;

import java.util.List;
import java.util.Properties;

import javax.activation.DataSource;

/**
 * 邮件信息描述类
 * @author wind
 *
 */
public class Email {
	/**配置session属性*/
	Properties props;
	/**发送者*/
	String sender;
	/**发送者邮箱账号*/
	String username;
	/**发送者邮箱密码*/
	String password;
	/**接收者*/
	String receiver;
	/**标题*/
	String title;
	/**邮件内容*/
	String content;
	/**附件*/
	List<DataSource> attachments;
	
	
	public Email() {
		super();
	}
	
	

	public Email(Properties props, String sender, String username, String password, String receiver, String title,
			String content, List<DataSource> attachments) {
		super();
		this.props = props;
		this.sender = sender;
		this.username = username;
		this.password = password;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.attachments = attachments;
	}


	public Properties getProps() {
		return props;
	}
	public void setProps(Properties props) {
		this.props = props;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<DataSource> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DataSource> attachments) {
		this.attachments = attachments;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
