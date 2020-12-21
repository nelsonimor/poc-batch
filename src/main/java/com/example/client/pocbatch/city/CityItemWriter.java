package com.example.client.pocbatch.city;

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
import com.exemple.poc.client.dto.response.CityDTO;

public class CityItemWriter implements ItemWriter<CityDTO> {
	
	Logger logger = LoggerFactory.getLogger(CityItemReader.class);

	@Autowired
	private AllConfig allConfig;

	@Override
	public void write(List<? extends CityDTO> cities) throws Exception { 
		System.out.println("CityItemWriter.write()");
		for (CityDTO pa : cities) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				logger.debug("Add new city : {}",pa.getName());
				ResponseEntity<CityDTO> respEntity = restTemplate.postForEntity(allConfig.getEndPointCity(), pa, CityDTO.class);
				logger.debug("Status Code = {} => {} ",respEntity.getStatusCodeValue(),respEntity.getStatusCode());
				CityDTO resp = respEntity.getBody();
				System.out.println("-> Calling web service:" + resp);
			} catch (HttpClientErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating city with name "+pa.getName(),e);

			} catch (HttpServerErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating city with name "+pa.getName(),e);
			}
		} 
	}

}
