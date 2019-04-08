package com.wind.network;

import org.junit.Test;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailTest {

    @Test
    public void test(){
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
//		Email email = new Email(props, sender, username, password, receiver, title, content, attachments);
//		EmailUtil.sendEmail(email);
    }
}
