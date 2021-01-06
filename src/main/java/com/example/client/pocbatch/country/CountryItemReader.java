package com.example.client.pocbatch.country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.client.util.ObjectMapper;
import com.exemple.poc.client.dto.response.CountryDTO;

import bball.dao.DAO;
import bball.dao.DAOImpl;
import bball.dataobj.Continent;
import bball.dataobj.Country;

public class CountryItemReader implements ItemReader<CountryDTO>  {
	
	private int count = 0;
	private List<CountryDTO> countries = new ArrayList<CountryDTO>();
	
	Logger logger = LoggerFactory.getLogger(CountryItemReader.class);
	
	
	public void init() {
		
    	DAO dao = new DAOImpl();
    	dao.connect();
    	
    	HashMap<Integer, Continent> mapContinents = new HashMap<Integer, Continent>();
    	Vector<Continent> continents = dao.getContinents();
    	continents.stream().forEach(continent -> mapContinents.put(continent.getId(), continent));
    	
    	Vector<Country> cs = dao.getCountries();
    	cs.stream().forEach(country -> countries.add(ObjectMapper.toCountryDto(country,mapContinents.get(country.getContinent()))));
    	dao.deconnect();
		logger.debug("CountryItemReader size continent = {}",countries.size());
	}

	@Override
	public CountryDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < countries.size()) {
			CountryDTO country  = countries.get(count++);
			logger.debug("Reading : {}",country);
			return country;
		} else {
			count = 0;
		}
		return null;
	}

}
