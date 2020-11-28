package com.ikubinfo.primefaces.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import com.ikubinfo.primefaces.model.Country;

public class CountryRepository {
	private static final String QUERY = "Select country.code,country.name,country.continent,country.surfacearea,country.indepyear from public.country where 1=1 ";
	private static final String UPDATE_QUERY = "update country set  name = ?,continent=?  where code = ?";
	private DataSource datasource;

	public CountryRepository(DataSource datasource) {
		super();
		this.datasource = datasource;
	}

	public List<Country> getAll(String continent, BigDecimal surface) {

		String queryString = QUERY;
		int index = 0;
		int surfaceIndex = 0;
		int continentIndex = 0;

		if (!Objects.isNull(surface)) {
			queryString = queryString.concat(" and  country.surfaceArea >= ? ");
			surfaceIndex = ++index;

		}

		if (!Objects.isNull(continent) && !continent.isEmpty()) {
			queryString = queryString.concat("and LOWER(country.continent) LIKE LOWER(?) ");
			continentIndex = ++index;
		}

		List<Country> toReturn = new ArrayList<>();
		try (Connection connection = datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(queryString);) {

			if (!Objects.isNull(surface)) {
				statement.setBigDecimal(surfaceIndex, surface);
			}

			if (!Objects.isNull(continent) && !continent.isEmpty()) {
				statement.setString(continentIndex, "%" + continent + "%");
			}

			System.out.println(statement);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Country country = new Country();
				country.setCountryCode(result.getString("code"));
				country.setName(result.getString("name"));
				country.setSurfaceArea(result.getBigDecimal("surfaceArea"));
				country.setContinent(result.getString("continent"));
				country.setIndepencenceYear(result.getInt("indepyear"));
				toReturn.add(country);

			}

		} catch (Exception ex) {
			System.out.println("Something went wrong ");
			ex.printStackTrace();
		}
		return toReturn;
	}

	public boolean save(Country country) {
		try (Connection connection = datasource.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {

			statement.setString(1, country.getName());
			statement.setString(2, country.getContinent());
			statement.setString(3, country.getCountryCode());

			return statement.executeUpdate() == 1;

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

}
