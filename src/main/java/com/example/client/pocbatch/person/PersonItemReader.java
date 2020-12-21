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
    	for (Iterator iterator = cities.iterator(); iterator.hasNext();) {
			City city = (City) iterator.next();
			mapCities.put(city.getId(), city);
		}
    	
    	HashMap<Integer, Country> mapCountries = new HashMap<Integer, Country>();
    	Vector<Country> countries = dao.getCountries();
    	for (Iterator iterator = countries.iterator(); iterator.hasNext();) {
    		Country country = (Country) iterator.next();
    		mapCountries.put(country.getId(), country);
		}
    	
    	
    	
    	Vector<Person> ps = dao.getPersons();

    	for (Iterator iterator = ps.iterator(); iterator.hasNext();) {
			Person person = (Person) iterator.next();
			PersonDTO personDTO = new PersonDTO();
			personDTO.setLastname(person.getLname());
			personDTO.setFirstname(person.getFname());
			personDTO.setBirthDate(new Date(person.getBirthdate()));
			
			if(person.getNat1()>0) {
				personDTO.setNationality1(mapCountries.get(person.getNat1()).getName());
			}
			
			if(person.getNat2()>0) {
				personDTO.setNationality2(mapCountries.get(person.getNat2()).getName());
			}
			
			if(person.getBirthcity()>0) {
				City c = mapCities.get(person.getBirthcity());
				personDTO.setBirthCityPlace(c.getName());
				personDTO.setBirthCountryPlace(mapCountries.get(c.getCountry()).getName());
			}

			persons.add(personDTO);
		}
    	
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
