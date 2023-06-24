package com.codemos.bot.utils.datas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnimalData {
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("width")
	private int width;
	
	@JsonProperty("height")
	private int height;
	
	public String getId() {
		return id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
