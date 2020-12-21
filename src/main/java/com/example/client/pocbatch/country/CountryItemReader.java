package com.example.client.pocbatch.country;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.example.client.pocbatch.continent.ContinentItemWriter;
import com.exemple.poc.client.dto.response.ContinentDTO;
import com.exemple.poc.client.dto.response.CountryDTO;

import bball.dao.DAO;
import bball.dao.DAOImpl;
import bball.dataobj.Continent;
import bball.dataobj.Country;

public class CountryItemReader implements ItemReader<CountryDTO>  {
	
	private int count = 0;
	private List<CountryDTO> countries= new ArrayList<CountryDTO>();
	
	Logger logger = LoggerFactory.getLogger(CountryItemReader.class);
	
	
	public void init() {
		
    	DAO dao = new DAOImpl();
    	dao.connect();
    	Vector<Country> cs = dao.getCountries();
    	
    	for (Iterator iterator = cs.iterator(); iterator.hasNext();) {
			Country country = (Country) iterator.next();
			CountryDTO c = new CountryDTO();
			c.setCodeiso2(country.getCodeIso2());
			c.setCodeiso3(country.getCodeIso3());
			c.setName(country.getName());
			c.setNationality(country.getNationality());
			
			Continent continent = dao.getContinentById(country.getContinent());
			
			if(continent!=null) {
				c.setContinentName(continent.getName());
			}
			countries.add(c);

		}
    	
    	dao.deconnect();
		logger.debug("CountryItemReader size continent = {}",countries.size());
	}

	@Override
	public CountryDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < countries.size()) {
			CountryDTO country  = countries.get(count++);
			return country;
		} else {
			count = 0;
		}
		return null;
	}

}
