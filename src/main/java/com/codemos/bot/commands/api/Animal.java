package com.codemos.bot.commands.api;

import com.codemos.bot.bots.Api;
import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.datas.AnimalData;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Animal extends ListenerAdapter {
	
	List<String> subCommands = List.of("강아지", "고양이");
	
	@Override
	public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {
		User bot = e.getJDA().getSelfUser();
		
		if (e.getName().equals("동물")) {
			if (subCommands.contains(e.getSubcommandName())) {
				String apiUrl = e.getSubcommandName().equals("강아지") ? Api.DOG_API_URL : Api.CAT_API_URL;
				
				try {
					URL url = new URL(apiUrl);
					
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
					AnimalData data = mapper.readValue(response.toString().replace("[", "").replace("]", ""), AnimalData.class);
					
					MessageEmbed animalEmbed = new EmbedBuilder()
						.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
						.setTitle(e.getSubcommandName().equals("강아지") ? "강아지" : "고양이")
						.setImage(data.getUrl())
						.setColor(Color.GREEN)
						.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
						.build();
					
					e.replyEmbeds(animalEmbed).queue();
				} catch (IOException ex) {
					MessageEmbed errorEmbed = new EmbedBuilder()
						.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
						.setTitle(Markdown.bold("오류"))
						.setDescription("API 요청 중 오류가 발생했습니다.")
						.setColor(0xFF0000)
						.addField("오류", Markdown.codeBlock(ex.getMessage(), "sh"), false)
						.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
						.build();
					
					e.replyEmbeds(errorEmbed).queue();
				}
			}
		}
	}
}
