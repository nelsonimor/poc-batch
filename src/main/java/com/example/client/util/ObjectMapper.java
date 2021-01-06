package com.example.client.util;

import java.util.Date;
import java.util.HashMap;

import com.exemple.poc.client.dto.response.CityDTO;
import com.exemple.poc.client.dto.response.ContinentDTO;
import com.exemple.poc.client.dto.response.CountryDTO;
import com.exemple.poc.client.dto.response.PersonDTO;

import bball.dataobj.City;
import bball.dataobj.Continent;
import bball.dataobj.Country;
import bball.dataobj.Person;

public class ObjectMapper {

	public static ContinentDTO toContinentDto(Continent c) {
		ContinentDTO continentDTO = new ContinentDTO();
		continentDTO.setName(c.getName());
		continentDTO.setCode(c.getCode());
		return continentDTO;
	}

	public static CountryDTO toCountryDto(Country country, Continent continent) {
		CountryDTO countryDto = new CountryDTO();
		countryDto.setCodeiso2(country.getCodeIso2());
		countryDto.setCodeiso3(country.getCodeIso3());
		countryDto.setName(country.getName());
		countryDto.setNationality(country.getNationality());
		if(continent!=null) {
			countryDto.setContinentName(continent.getName());
		}
		return countryDto;
	}

	public static CityDTO toCityDto(City city, Country country) {
		CityDTO cityDto = new CityDTO();
		cityDto.setName(city.getName());
		cityDto.setLatitude(city.getLatitude());
		cityDto.setLongitude(city.getLongitude());
		cityDto.setCounty(city.getCounty());
		cityDto.setState(city.getState());
		cityDto.setZip(city.getZip());
		cityDto.setCountycode(city.getCountyCode());
		if(country!=null) {
			cityDto.setCountryName(country.getName());
		}
		return cityDto;
	}

	public static PersonDTO toPersonDto(Person person, HashMap<Integer, City> mapCities,HashMap<Integer, Country> mapCountries) {
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
		return personDTO;
	}

}
