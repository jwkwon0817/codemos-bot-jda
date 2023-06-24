package com.codemos.bot.commands.meme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MemeDataParser {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public String getPostLink(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("postLink").asText();
	}
	
	public boolean isUnableToAccess(String jsonString) throws JsonProcessingException {
		return !mapper.readTree(jsonString).get("code").isEmpty();
	}
	
	public String getTitle(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("title").asText();
	}
	
	public String getUrl(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("url").asText();
	}
	
	public boolean isNsfw(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("nsfw").asBoolean();
	}
	
	public String getAuthor(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("author").asText();
	}
	
	public int getUps(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("ups").asInt();
	}
}
