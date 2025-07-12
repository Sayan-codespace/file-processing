package com.fileprocessing.mail.fileprocessing_mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fileprocessing.mail.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
	
	@Mock
	private JavaMailSender mailSender;
	
	@InjectMocks
	private EmailService emailService;
	
	@Mock
	private MimeMessage mimeMessage;
		
	@Test
	public void testSendEmail() {
		
		String to = "abc@gmail.com";
		String from = "abc@gmail.com";
		String subject = "Subject";
		String body = "Hello";
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);	
		
		
		emailService.sendEmail(to, subject, body);
		assertThat(message.getSubject().equals("Subject"));
		
	}
	
	@Test
	public void testSendEmailWithAttachment() throws Exception {
		
        String csvContent = "id,name\n1,Alice\n2,Bob";
		MultipartFile validFile = new MockMultipartFile(
                "file",
                "valid.csv",
                "text/csv",
                csvContent.getBytes()
        );
		
		
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
        
        Field fromField = EmailService.class.getDeclaredField("from");
        fromField.setAccessible(true);
        fromField.set(emailService, "abc@example.com");
		
		String to = "abc@gmail.com";
		String subject = "Subject";
		String body = "Hello";
				
		
		emailService.sendEmailWithAttachment(to, subject, body,validFile);
		verify(mailSender).send(mimeMessage);
		
	}

}
