package com.nina.goal.keeper.tracking.data.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBProperties {
	@Value("${db.server.url}")
	private String url;
	@Value("${db.server.username}")
	private String userName;
	@Value("${db.server.password}")
	private String passWord;

	public String getUrl() {
		return url;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassWord() {
		return passWord;
	}

}
