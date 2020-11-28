package com.ikubinfo.primefaces.service;

import java.math.BigDecimal;
import java.util.List;

import com.ikubinfo.datasource.DatasourceManager;
import com.ikubinfo.primefaces.model.Country;
import com.ikubinfo.primefaces.repository.CountryRepository;

public class CountryService {

	CountryRepository countryDao;

	public CountryService() {
		super();
		this.countryDao = new CountryRepository(DatasourceManager.getDataSource());
	}

	public List<Country> getAll(String continent, BigDecimal surface) {

		return countryDao.getAll(continent, surface);

	}

	public boolean save(Country country) {
		return countryDao.save(country);
		
	}
}
