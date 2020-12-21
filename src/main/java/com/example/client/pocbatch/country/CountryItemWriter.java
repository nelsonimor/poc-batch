package com.example.client.pocbatch.country;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.client.pocbatch.AllConfig;
import com.exemple.poc.client.dto.response.CountryDTO;

public class CountryItemWriter implements ItemWriter<CountryDTO> {

	@Autowired
	private AllConfig allConfig;
	
	Logger logger = LoggerFactory.getLogger(CountryItemReader.class);

	@Override
	public void write(List<? extends CountryDTO> countries) throws Exception { 
		for (CountryDTO pa : countries) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<CountryDTO> respEntity = restTemplate.postForEntity(allConfig.getEndPointCountry(), pa, CountryDTO.class);
				CountryDTO resp = respEntity.getBody();
				logger.debug("-> Calling web service:" + resp);
			}catch (HttpClientErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating country : "+pa.getName(),e);

			}

		} 
	}

}
