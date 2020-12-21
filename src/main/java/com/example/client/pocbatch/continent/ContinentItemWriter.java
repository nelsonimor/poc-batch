package com.example.client.pocbatch.continent;

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
import com.exemple.poc.client.dto.response.ContinentDTO;


public class ContinentItemWriter implements ItemWriter<ContinentDTO> {
	
	@Autowired
	private AllConfig allConfig;
	
	Logger logger = LoggerFactory.getLogger(ContinentItemWriter.class);

    @Override
    public void write(List<? extends ContinentDTO> continents) throws Exception { 
    	
    	String endpoint = allConfig.getEndPointContinent();
    	logger.debug("Continent Endpoint = {}",endpoint); 
    	
    
        for (ContinentDTO pa : continents) {
        	try {
            	RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<ContinentDTO> respEntity = restTemplate.postForEntity(endpoint, pa, ContinentDTO.class);
                ContinentDTO resp = respEntity.getBody();
                logger.debug("-> Calling web service: {}",resp);
			}catch (HttpClientErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating continent : "+pa.getName(),e);
			}catch (HttpServerErrorException e) {
				logger.error("Status code : {}",e.getStatusCode());
				logger.error("Status text : "+e.getStatusText());
				logger.error("Error while creating continent : "+pa.getName(),e);
			}

        } 
    }

}
