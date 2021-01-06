package com.example.client.pocbatch.continent;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.client.util.ObjectMapper;
import com.exemple.poc.client.dto.response.ContinentDTO;

import bball.dao.DAO;
import bball.dao.DAOImpl;
import bball.dataobj.Continent;

public class ContinentItemReader implements ItemReader<ContinentDTO>  {
	
	private int count = 0;
	private List<ContinentDTO> continents= new ArrayList<ContinentDTO>();
	
	Logger logger = LoggerFactory.getLogger(ContinentItemReader.class);
	
	public void init() {
    	DAO dao = new DAOImpl();
    	dao.connect();
    	Vector<Continent> cs = dao.getContinents();
    	logger.debug("Find {} continents",cs.size());
    	cs.stream().forEach(c -> continents.add(ObjectMapper.toContinentDto(c)));
    	dao.deconnect();
		logger.debug("ContinentItemReader size continent {} ",continents.size());
	}

	@Override
	public ContinentDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < continents.size()) {
			ContinentDTO continent = continents.get(count++);
			return continent;
		} else {
			count = 0;
		}
		return null;
	}

}
