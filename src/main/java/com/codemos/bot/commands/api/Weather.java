package com.codemos.bot.commands.api;

import com.codemos.bot.bots.Api;
import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.datas.WeatherData;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		if (e.getName().equals("날씨")) {
			String location = e.getOption("location").getAsString();
			User bot = Bot.getBot();
			final String apiLink = Api.WEATHER_API_URL + location + "&appid=" + Api.WEATHER_API_KEY + "&units=metric&lang=kr";
			
			try {
				URL url = new URL(apiLink);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				
				String inputLine;
				StringBuilder response = new StringBuilder();
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				ObjectMapper mapper = new ObjectMapper();
				WeatherData data = mapper.readValue(response.toString(), WeatherData.class);
				
				String main = data.getWeather()[0].getMain();
				String description = data.getWeather()[0].getDescription();
				
				
				MessageEmbed weatherEmbed = new EmbedBuilder()
					.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
					.setTitle(Markdown.bold("날씨"))
					.setDescription(Markdown.bold(main) + " | " + Markdown.bold(description))
					.setColor(Color.GREEN)
					.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
					.build();
				
				e.replyEmbeds(weatherEmbed).queue();
			} catch (Exception ex) {
				MessageEmbed errorEmbed = new EmbedBuilder()
					.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
					.setTitle(Markdown.bold("오류"))
					.setDescription(Markdown.bold("날씨 정보를 조회하는 도중 오류가 발생했습니다."))
					.addField("오류", Markdown.codeBlock(ex.getMessage(), "sh"), false)
					.setColor(Color.RED)
					.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
					.build();
				
				e.replyEmbeds(errorEmbed).queue();
			}
		}
	}
}
