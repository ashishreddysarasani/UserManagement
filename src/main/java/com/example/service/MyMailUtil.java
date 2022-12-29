package com.example.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
@Service
public class MyMailUtil implements NotificationServiceInterface{

	@Autowired
	private JavaMailSender mailSender;

	public boolean send(
			String to,
			String cc[],
			String bcc[],
			String subject,
			String text,
			Resource file
			) 
	{
		boolean flag = false;

		try {
			//1. create one Empty/new MimeMessage
			MimeMessage message  = mailSender.createMimeMessage();
			
			//2. use Helper class and set details
			MimeMessageHelper  helper = new MimeMessageHelper(message, file!=null);
			
			helper.setTo(to);
			
			if(cc!=null && cc.length>0)
				helper.setCc(cc);
			
			if(bcc!=null && bcc.length>0)
				helper.setBcc(bcc);
			
			helper.setSubject(subject);
			helper.setText(text);
			
			if(file!=null) {
				//file name , resource object
				helper.addAttachment(file.getFilename(), file);
			}
			
			//3. send email 
			mailSender.send(message);
			
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
