package com.codemos.bot.utils.json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonValueRetriever {
	public static List<String> getAllValues(String jsonString) {
		List<String> values = new ArrayList<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(jsonString);
			
			retrieveValuesRecursively(jsonNode, values);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid Json");
		}
		
		return values;
	}
	
	private static void retrieveValuesRecursively(JsonNode jsonNode, List<String> values) {
		if (jsonNode.isObject()) {
			jsonNode.elements().forEachRemaining(node -> retrieveValuesRecursively(node, values));
		} else if (jsonNode.isArray()) {
			jsonNode.elements().forEachRemaining(node -> retrieveValuesRecursively(node, values));
		} else {
			values.add(jsonNode.asText());
		}
	}
}
