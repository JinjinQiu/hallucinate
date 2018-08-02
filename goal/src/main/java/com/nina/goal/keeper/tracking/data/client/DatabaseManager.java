package com.nina.goal.keeper.tracking.data.client;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.nina.goal.keeper.tracking.HomeController;

@Component
public class DatabaseManager {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
	private DBProperties config;

	private Connection conn;

	@Autowired
	public DatabaseManager(DBProperties config) {
		this.config = config;
	}

	public Connection connect() {
		try {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser(config.getUserName());
			dataSource.setPassword(config.getPassWord());
			dataSource.setUrl(config.getUrl());
			dataSource.setServerTimezone("UTC");
			// dataSource.setServerName("localhost");
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getStackTrace().toString());
		}
		return conn;
	}

//	public static void main(String args[]) {
//		Connection conn = new DatabaseManager("jdbc:mysql://localhost:3306/goal", "root", "rootpassword").connect();
//		try {
//			Statement statement = conn.createStatement();
//			ResultSet rs = statement.executeQuery("select * from basic_goal");
//			while (rs.next()) {
//				System.out.println(rs.getString(2));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	//}
}
