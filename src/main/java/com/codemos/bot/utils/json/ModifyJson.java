package com.codemos.bot.utils.json;

import com.codemos.bot.bots.Bot;
import net.dv8tion.jda.api.entities.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class ModifyJson {
	
	private JSONObject getObject() throws IOException, ParseException {
		JSONParser parser = new JSONParser();
		
		Reader reader = new FileReader(Bot.STUDY_LIST_JSON_DIRECTORY);
		
		JSONObject object = (JSONObject)parser.parse(reader);
		
		if (object.isEmpty()) {
			object = new JSONObject();
		}
		
		return object;
	}
	
	public void add(User user, String key, String value) {
		
		try {
			JSONObject object = getObject();
			
			JSONObject data = new JSONObject();
			
			data.put("user", user.getId());
			data.put("text", value);
			
			object.put(key, data);
			
			write(object.toJSONString());
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean hasKey(String key) {
		
		try {
			JSONObject object = getObject();
			
			return object.containsKey(key);
		} catch (IOException | ParseException ex) {
			return false;
		}
	}
	
	public String getUserId(String key) {
		
		try {
			JSONObject object = getObject();
			
			JSONObject data = (JSONObject)object.get(key);
			
			return (String)data.get("user");
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void remove(String key) {
		
		try {
			JSONObject object = getObject();
			
			object.remove(key);
			
			write(object.toJSONString());
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
		}
	}
	
	public String get(String key) {
		
		try {
			JSONObject object = getObject();
			
			return (String)object.get(key);
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public String getValue(String key) {
		
		try {
			JSONObject object = getObject();
			
			JSONObject data = (JSONObject)object.get(key);
			
			return (String)data.get("text");
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public List<String> getKeys() {
		
		try {
			JSONObject object = getObject();
			
			return object.keySet().stream().toList();
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	private void write(String json) {
		try {
			FileWriter writer = new FileWriter(Bot.STUDY_LIST_JSON_DIRECTORY);
			writer.write(json);
			writer.flush();
			writer.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
