package com.codemos.bot.commands.dict;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictionaryDataParser {
	
	ObjectMapper mapper = new ObjectMapper();
	
	public String getWord(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("channel").get("item").get(0).get("word").asText();
	}
	
	public String getDefinition(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("channel").get("item").get(0).get("sense").get("definition").asText();
	}
	
	public String getLink(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("channel").get("item").get(0).get("sense").get("link").asText();
	}
	
	public String getType(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("channel").get("item").get(0).get("sense").get("type").asText();
	}
	
	public String getPos(String jsonString) throws JsonProcessingException {
		return mapper.readTree(jsonString).get("channel").get("item").get(0).get("pos").asText();
	}
}
