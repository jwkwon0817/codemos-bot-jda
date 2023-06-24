package com.codemos.bot.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiRequest {
	
	public static String getJsonString(String urlString) throws IOException {
		URL url = new URL(urlString);
		
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setDoOutput(true);
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		conn.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		
		String inputLine;
		StringBuilder response = new StringBuilder();
		
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		return response.toString();
	}
}
