package com.example.client.pocbatch.continent;

import org.springframework.batch.item.ItemProcessor;

import com.exemple.poc.client.dto.response.ContinentDTO;


public class ContinentItemProcessor implements ItemProcessor<ContinentDTO, ContinentDTO> {
    
    @Override
    public ContinentDTO process(final ContinentDTO continent) throws Exception {
    	//System.out.println("PROCESSOR => continent = "+continent.getName()+" (code = "+continent.getCode()+")");
        return continent;
    }
}
