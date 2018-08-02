package com.nina.goal.keeper.tracking.data.process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nina.goal.keeper.tracking.data.client.DatabaseManager;

@Component
public class DBWriter {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DBWriter.class);
	private final DatabaseManager dbmanager;
	private Connection connection;

	@Autowired
	public DBWriter(DatabaseManager dbmanager) {
		this.dbmanager = dbmanager;
		this.connection = dbmanager.connect();
	}

	public boolean existingCheck(String query, String goal) {
		Boolean exist = false;
		try {
			PreparedStatement st = connection.prepareStatement(query);
			st.setString(1, goal);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				exist = true;
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return exist;
	}

	public void update(String query, String data) {
		try {
			PreparedStatement st = connection.prepareStatement(query);
			String dataArray[] = data.split(",");
			for (int i = 1; i < dataArray.length + 1; i++) {
				st.setString(i, dataArray[i - 1]);
			}
			boolean rs = st.execute();

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
