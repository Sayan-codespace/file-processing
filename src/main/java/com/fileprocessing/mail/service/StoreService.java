package com.fileprocessing.mail.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fileprocessing.mail.exception.InvalidExtensionException;
import com.fileprocessing.mail.model.AllStores;
import com.fileprocessing.mail.model.CurrentStores;
import com.fileprocessing.mail.repo.AllStoresRepository;
import com.fileprocessing.mail.repo.CurrentStoresRepository;

import jakarta.mail.MessagingException;

@Service
public class StoreService {
	
	@Autowired
	private CurrentStoresRepository currentStoresRepo;

	@Autowired
	private AllStoresRepository allStoresRepo;
	
	@Autowired
	private EmailService emailService;
	
	Logger log = org.slf4j.LoggerFactory.getLogger(StoreService.class);
	
	public String readCsv(MultipartFile file) throws IOException {
		
			try {
			validateCsv(file);
				
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			List<String> list = reader.lines().skip(1).collect(Collectors.toList());
			
			
			Map<String,String> map = list.stream().map(val->{
				return val.split(",");	
			}).collect(Collectors.toMap(y->y[0], y->y[1]));
			
			List<Integer> storesList = map.keySet().stream()
					.map(val->Integer.parseInt(val)).collect(Collectors.toList());
			
			List<CurrentStores> validStoresList = checkStoresAvailable(storesList);
			
			List<Integer> inValidStores = storesList.stream()
					.filter(x->validStoresList.stream().noneMatch(y->y.getStoreId()==x)).collect(Collectors.toList());
			log.info("The invalid stores.....{}",inValidStores);

			updateStores(map,validStoresList);
			
			sendMail(file,inValidStores);
		}
		catch(InvalidExtensionException e) {
			throw new InvalidExtensionException("Upload csv file!!!!");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		
		return "Process completed successfully!!";
	}
	
	public void validateCsv(MultipartFile file) {
				
		String fileName = file.getOriginalFilename();
		String extension="";
		if(fileName!=null && fileName.contains(".")) {
			extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		}
		log.info("validateCsv method called the extension is .....{}",extension);
		if(!extension.equals("csv")) {
			throw new InvalidExtensionException("Upload csv file!!!!");
		}
		

	}
	
	public List<CurrentStores> checkStoresAvailable(List<Integer> storesList){
		return currentStoresRepo.findByStoreId(storesList);
	}
	
	
	public void updateStores(Map<String,String> map,List<CurrentStores> validStoresList) {
		
		List<Integer> validStoreId = validStoresList.stream().map(val->val.getStoreId()).collect(Collectors.toList());
		
		//stores needs to be updated
		List<AllStores> list = allStoresRepo.findByStoreIdIn(validStoreId);
				
		list.stream().forEach(val->{
			String isValid = map.get(String.valueOf(val.getStoreId()));
			val.setIsValid(isValid);
		});
		
		allStoresRepo.saveAll(list);			
	}
	
	public void sendMail(MultipartFile file, List<Integer> inValidStores) throws MessagingException {
		
		String to = "sayanchatterjee1811@gmail.com";
		String subject = "process completed";
		String body = "";
		body = body + "List of stores that are invalid: \n";
		for(Integer stores : inValidStores) {
			body = body+""+stores+" \n";
		}
		
		body = body +"Csv file attached below";
		
		emailService.sendEmailWithAttachment(to, subject, body, file);
		
	}
	
	@Scheduled(cron = "0 0/1 * 1/1 * ? ")
	public void testScheduler() {
		System.out.println("This is dummy Cron statement that will run every minute");
	}

}
