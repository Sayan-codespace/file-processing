package com.fileprocessing.mail.fileprocessing_mail;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fileprocessing.mail.repo.AllStoresRepository;
import com.fileprocessing.mail.repo.CurrentStoresRepository;
import com.fileprocessing.mail.service.EmailService;
import com.fileprocessing.mail.service.StoreService;

import jakarta.mail.MessagingException;


@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
	
	@Mock
	private CurrentStoresRepository currentStoresRepo;
	
	@Mock
	private AllStoresRepository allStoresRepo;
	
	@Mock
	private EmailService emailService;
	
	@Mock
	private MultipartFile multipart;

	@InjectMocks
	private StoreService storeService;
	
	
	@Test
	public void testValidateCsv() {
		
		when(multipart.getOriginalFilename()).thenReturn("store-valid.csv");
		storeService.validateCsv(multipart);
	}
	
	@Test
	public void testReadCsv() throws MessagingException, IOException {
        
        String csvContent = "id,name\n1,Alice\n2,Bob";
        MultipartFile validFile = new MockMultipartFile(
                "file",
                "valid.csv",
                "text/csv",
                csvContent.getBytes()
        );
		storeService.readCsv(validFile);
	}

	@Test
	public void testReadCsv_InvalidCsvException() throws MessagingException, IOException {
		
		assertThrows(RuntimeException.class, ()->storeService.readCsv(multipart));
	}
	
	@Test
	public void testScheduler() {
		
		storeService.testScheduler();
	}
}
