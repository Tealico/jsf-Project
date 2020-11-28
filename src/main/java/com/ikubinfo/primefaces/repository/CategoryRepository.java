package com.ikubinfo.primefaces.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ikubinfo.primefaces.exception.ApfasException;
import com.ikubinfo.primefaces.model.Category;

public class CategoryRepository {

	Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

	private static final String QUERY = "Select * from public.category where 1=1 ";
	private static final String UPDATE_QUERY = "update category set  name = ? where category_id = ?";
	private static final String INSERT_QUERY = "insert into category (name,last_update) values (?,?)";
	private static final String CATEGORY_IN_USE = "Select count(category_id) as category_count from film_category where category_id = ?";
	private static final String DELETE_CATEGORY = "Delete from category where category_id = ?";

	private DataSource dataSource;

	public CategoryRepository(DataSource datasource) {
		super();
		this.dataSource = datasource;
	}

	public List<Category> getAll(String name) {

		String queryString = QUERY;
		int index = 0;
		int nameIndex = 0;

		if (!Objects.isNull(name) && !name.isEmpty()) {
			queryString = queryString.concat(" and  category.name like  ? ");
			nameIndex = ++index;

		}

		List<Category> toReturn = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(queryString);) {

			if (!Objects.isNull(name) && !name.isEmpty()) {

				statement.setString(nameIndex, "%" + name + "%");
			}

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Category category = new Category();
				category.setId(result.getInt("category_id"));
				category.setName(result.getString("name"));
				category.setLastUpdated(new Date(result.getTimestamp("last_update").getTime()));
				toReturn.add(category);

			}

			return toReturn;

		} catch (SQLException e) {
			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}

	}

	public boolean save(Category category) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY);) {

			statement.setString(1, category.getName());
			statement.setInt(2, category.getId());

			return statement.executeUpdate() == 1;

		} catch (SQLException e) {

			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}
	}

	public boolean create(Category category) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT_QUERY);) {

			statement.setString(1, category.getName());
			statement.setTimestamp(2, new Timestamp(category.getLastUpdated().getTime()));

			return statement.executeUpdate() == 1;

		} catch (SQLException e) {

			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}
	}

	public boolean isCategoryInUse(Category category) {

		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(CATEGORY_IN_USE);) {

			statement.setInt(1, category.getId());

			ResultSet result = statement.executeQuery();
			result.next();
			return result.getInt("category_count") > 0;

		} catch (SQLException e) {
			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}

	}

	public void delete(Category category) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY);) {

			statement.setInt(1, category.getId());

			statement.executeUpdate();

		} catch (SQLException e) {
			logger.error("An error occured during query execution");
			throw new ApfasException(e.getMessage());
		}

	}

}
