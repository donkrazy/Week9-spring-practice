package com.estsoft.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.stereotype.Component;


public class MariaDBConnection implements DBConnection {
	@Override
	public Connection getConnection() throws SQLException {
		return null;
	}
}
