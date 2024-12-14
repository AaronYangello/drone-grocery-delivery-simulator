package edu.gatech.cs6310;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;

public class Session {
	private HttpClient httpClient;
	
	Session(final String user, final String password) {
		httpClient = HttpClient.newBuilder()
				.authenticator(new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
							user,
							password.toCharArray());
					}
				})
				.build();
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}
}
