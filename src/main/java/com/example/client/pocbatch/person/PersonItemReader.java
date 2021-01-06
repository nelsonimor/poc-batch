package com.example.client.pocbatch.person;

import java.util.ArrayList;
import java.util.Date;
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
import com.exemple.poc.client.dto.response.PersonDTO;

import bball.dao.DAO;
import bball.dao.DAOImpl;
import bball.dataobj.City;
import bball.dataobj.Country;
import bball.dataobj.Person;

public class PersonItemReader implements ItemReader<PersonDTO>  {
	
	private int count = 0;
	private List<PersonDTO> persons = new ArrayList<PersonDTO>();
	
	Logger logger = LoggerFactory.getLogger(PersonItemReader.class);
	
	
	public void init() {
		
    	DAO dao = new DAOImpl();
    	dao.connect();
    	
    	HashMap<Integer, City> mapCities = new HashMap<Integer, City>();
    	Vector<City> cities = dao.getCities();
    	cities.stream().forEach(city -> mapCities.put(city.getId(), city));

    	HashMap<Integer, Country> mapCountries = new HashMap<Integer, Country>();
    	Vector<Country> countries = dao.getCountries();
    	countries.stream().forEach(country -> mapCountries.put(country.getId(), country));
 
    	Vector<Person> ps = dao.getPersons();
    	ps.stream().forEach(person -> persons.add(ObjectMapper.toPersonDto(person,mapCities,mapCountries)));

    	dao.deconnect();
		logger.debug("PersonItemReader size persons = {}",persons.size());
	}

	@Override
	public PersonDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if (count < persons.size()) {
			PersonDTO person  = persons.get(count++);
			return person;
		} else {
			count = 0;
		}
		return null;
	}

}
