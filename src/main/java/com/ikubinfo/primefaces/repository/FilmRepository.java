package com.ikubinfo.primefaces.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikubinfo.datasource.DatasourceManager;
import com.ikubinfo.primefaces.model.Film;


public class FilmRepository {
	Logger logger = LoggerFactory.getLogger(CategoryRepository.class);
	
	public static final String GET_ALL_FILMS_BY_CATEGORY_ID="Select film.film_id,film.title,actor.first_name,actor.last_name,film.release_year,film.description "
			+ "from film "
			+ "join film_category on film.film_id= film_category.film_id "
			+ "join film_actor on film_actor.film_id = film.film_id "
			+ "join actor on film_actor.actor_id = actor.actor_id "
			+ "where film_category.category_id=? "
			+ "order by film.title DESC ";
	
	private DataSource dataSource = DatasourceManager.getDataSource();

	public List<Film> getAllFilmsByCategoryId(int category_id){
		List<Film> films =new ArrayList<Film>();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FILMS_BY_CATEGORY_ID);
			System.out.println(preparedStatement.toString());
			preparedStatement.setInt(1, category_id);
			System.out.println(preparedStatement.toString());
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) {
				Film film =new Film();
				film.setTitle(result.getString("title"));
				film.setDescription(result.getString("description"));
				film.setReleaseYear(result.getInt("release_year"));
				film.setFirstName(result.getString("first_name"));
				film.setLastName(result.getString("last_name"));
				film.setId(result.getInt("film_id"));
				films.add(film);
			}
			connection.close();
			return films;
		}catch (Exception ex) {
			logger.error("Something went wrong ", ex);
			return null;
		}
	}
}

