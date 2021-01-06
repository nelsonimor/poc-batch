package com.example.client.pocbatch.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.client.util.ObjectMapper;
import com.exemple.poc.client.dto.response.CityDTO;

import bball.dao.DAO;
import bball.dao.DAOImpl;
import bball.dataobj.City;
import bball.dataobj.Country;

public class CityItemReader implements ItemReader<CityDTO>  {
	
	private int count = 0;
	private List<CityDTO> cities = new ArrayList<CityDTO>();
	
	Logger logger = LoggerFactory.getLogger(CityItemReader.class);
	
	
	public void init() {
		

    	DAO dao = new DAOImpl();
    	dao.connect();
    	
    	HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
    	Vector<Country> cs = dao.getCountries();
    	cs.stream().forEach(country -> countries.put(country.getId(), country));

    	Vector<City> css = dao.getCities();
    	css.stream().forEach(city -> cities.add(ObjectMapper.toCityDto(city,countries.get(city.getCountry()))));
    	
    	dao.deconnect();
		logger.debug("CityItemReader size cities {} ",cities.size());
	}

	@Override
	public CityDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < cities.size()) {
			CityDTO city  = cities.get(count++);
			return city;
		} else {
			count = 0;
		}
		return null;
	}

}
