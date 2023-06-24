package com.codemos.bot.utils.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusChecker {
	public static String getHttpStatus(String method, String url) throws IllegalArgumentException {
		try {
			URL urlObj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
			connection.setRequestMethod(method);
			
			int statusCode = connection.getResponseCode();
			String statusMessage = connection.getResponseMessage();
			
			connection.disconnect();
			
			return connection.getRequestMethod() + "\n" + statusCode + " " + statusMessage;
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to retrieve HTTP status");
		}
	}
}
