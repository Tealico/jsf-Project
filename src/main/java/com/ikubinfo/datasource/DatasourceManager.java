package com.ikubinfo.datasource;

import java.util.Objects;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatasourceManager {
	private static String url = "jdbc:postgresql://localhost:5433/dvdrental";
	private static String username = "postgres";
	private static String password = "tea1999";

	public static DataSource datasource;

	private DatasourceManager() {

	}

	public static DataSource getDataSource() {

		if (Objects.isNull(datasource)) {

			HikariConfig config = new HikariConfig();
			config.setDriverClassName(org.postgresql.Driver.class.getName());
			config.setJdbcUrl(url);
			config.setUsername(username);
			config.setPassword(password);
			datasource = new HikariDataSource(config);
		}

		return datasource;
	}
}
