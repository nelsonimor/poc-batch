package com.example.client.pocbatch.person;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.client.pocbatch.AllConfig;
import com.exemple.poc.client.dto.response.CountryDTO;
import com.exemple.poc.client.dto.response.PersonDTO;

public class PersonItemWriter implements ItemWriter<PersonDTO> {

	@Autowired
	private AllConfig allConfig;
	
	Logger logger = LoggerFactory.getLogger(PersonItemReader.class);

	@Override
	public void write(List<? extends PersonDTO> persons) throws Exception { 
		for (PersonDTO pa : persons) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<CountryDTO> respEntity = restTemplate.postForEntity(allConfig.getEndPointPerson(), pa, CountryDTO.class);
				CountryDTO resp = respEntity.getBody();
				logger.debug("-> Calling web service:" + resp);
			}catch (HttpClientErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating person : "+pa.getLastname()+" "+pa.getFirstname(),e);
			} catch (HttpServerErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating person : "+pa.getLastname()+" "+pa.getFirstname(),e);
			}

		} 
	}

}
