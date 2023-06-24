package com.codemos.bot.utils.json;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonKeyRetriever {
	public static List<String> getAllKeys(String jsonString) {
		List<String> keys = new ArrayList<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(jsonString);
			
			retrieveKeysRecursively(jsonNode, "", keys);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid Json");
		}
		
		return keys;
	}
	
	private static void retrieveKeysRecursively(JsonNode jsonNode, String currentPath, List<String> keys) {
		if (jsonNode.isObject()) {
			jsonNode.fields().forEachRemaining(entry -> {
				String key = entry.getKey();
				JsonNode value = entry.getValue();
				String newPath = currentPath.isEmpty() ? key : currentPath + "." + key;
				
				keys.add(newPath);
				retrieveKeysRecursively(value, newPath, keys);
			});
		} else if (jsonNode.isArray()) {
			int index = 0;
			for (JsonNode element : jsonNode) {
				String newPath = currentPath + "[" + index + "]";
				keys.add(newPath);
				retrieveKeysRecursively(element, newPath, keys);
				index++;
			}
		}
	}
}
