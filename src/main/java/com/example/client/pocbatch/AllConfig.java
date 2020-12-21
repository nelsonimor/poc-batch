package com.example.client.pocbatch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:endpoint.properties"})
public class AllConfig {

	@Value("${endpoint.continent}")
	private String endPointContinent;
	
	@Value("${endpoint.country}")
	private String endPointCountry;
	
	@Value("${endpoint.city}")
	private String endPointCity;
	
	@Value("${endpoint.person}")
	private String endPointPerson;

	public String getEndPointContinent() {
		return endPointContinent;
	}

	public void setEndPointContinent(String endPointContinent) {
		this.endPointContinent = endPointContinent;
	}

	public String getEndPointCountry() {
		return endPointCountry;
	}

	public void setEndPointCountry(String endPointCountry) {
		this.endPointCountry = endPointCountry;
	}

	public String getEndPointCity() {
		return endPointCity;
	}

	public void setEndPointCity(String endPointCity) {
		this.endPointCity = endPointCity;
	}

	public String getEndPointPerson() {
		return endPointPerson;
	}

	public void setEndPointPerson(String endPointPerson) {
		this.endPointPerson = endPointPerson;
	}

}
