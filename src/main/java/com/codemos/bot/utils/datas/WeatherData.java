package com.codemos.bot.utils.datas;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherData {
	@JsonProperty("coord")
	private Coord coord;
	
	@JsonProperty("weather")
	private Weather[] weather;
	
	@JsonProperty("base")
	private String base;
	
	@JsonProperty("main")
	private Main main;
	
	@JsonProperty("visibility")
	private int visibility;
	
	@JsonProperty("wind")
	private Wind wind;
	
	@JsonProperty("clouds")
	private Clouds clouds;
	
	@JsonProperty("dt")
	private long dt;
	
	@JsonProperty("sys")
	private Sys sys;
	
	@JsonProperty("timezone")
	private int timezone;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("cod")
	private int cod;
	
	public Coord getCoord() {
		return coord;
	}
	
	public Weather[] getWeather() {
		return weather;
	}
	
	public static class Coord {
		private double lon;
		private double lat;
		
		public double getLon() {
			return lon;
		}
		
		public double getLat() {
			return lat;
		}
	}
	
	public static class Weather {
		private int id;
		private String main;
		private String description;
		private String icon;
		
		public int getId() {
			return id;
		}
		
		public String getMain() {
			return main;
		}
		
		public String getDescription() {
			return description;
		}
		
		public String getIcon() {
			return icon;
		}
	}
	
	public static class Main {
		private double temp;
		@JsonProperty("feels_like")
		private double feelsLike;
		@JsonProperty("temp_min")
		private double tempMin;
		@JsonProperty("temp_max")
		private double tempMax;
		private int pressure;
		private int humidity;
		
		public double getTemp() {
			return temp;
		}
		
		public double getFeelsLike() {
			return feelsLike;
		}
		
		public double getTempMin() {
			return tempMin;
		}
		
		public double getTempMax() {
			return tempMax;
		}
		
		public int getPressure() {
			return pressure;
		}
		
		public int getHumidity() {
			return humidity;
		}
	}
	
	public static class Wind {
		private double speed;
		private int deg;
		
		public double getSpeed() {
			return speed;
		}
		
		public int getDeg() {
			return deg;
		}
	}
	
	public static class Clouds {
		private int all;
		
		public int getAll() {
			return all;
		}
	}
	
	public static class Sys {
		private int type;
		private int id;
		private String country;
		private long sunrise;
		private long sunset;
		
		public int getType() {
			return type;
		}
		
		public int getId() {
			return id;
		}
		
		public String getCountry() {
			return country;
		}
		
		public long getSunrise() {
			return sunrise;
		}
		
		public long getSunset() {
			return sunset;
		}
	}
}
