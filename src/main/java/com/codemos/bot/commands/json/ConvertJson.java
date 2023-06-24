package com.codemos.bot.commands.json;

import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class ConvertJson extends ListenerAdapter {
	
	User bot = Bot.getBot();
	JsonNode jsonNode;
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		List<String> subCommands = List.of("정렬", "압축");
		
		if (e.getName().equals("json")) {
			if (subCommands.contains(e.getSubcommandName())) {
				String json = e.getOption("json").getAsString();
				
				ObjectMapper objectMapper = new ObjectMapper();
				if (e.getSubcommandName().equals("정렬")) {
					objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
				} else {
					objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
				}
				
				try {
					jsonNode = objectMapper.readTree(json);
					String convertedJson = objectMapper.writeValueAsString(jsonNode);
					
					MessageEmbed successEmbed = new EmbedBuilder()
						.setTitle("**성공!**")
						.setDescription("변환된 JSON")
						.addField("결과", Markdown.codeBlock(convertedJson, "json"), false)
						.setColor(Color.GREEN)
						.build();
					
					
					e.replyEmbeds(successEmbed).queue();
				} catch (JsonProcessingException ex) {
					MessageEmbed failedEmbed = new EmbedBuilder()
						.setTitle("**실패!**")
						.setDescription("올바르지 않은 JSON")
						.addField("입력", Markdown.codeBlock(json, "json"), false)
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(failedEmbed).setEphemeral(true).queue();
				}
			}
		}
	}
	
	@Override
	public void onMessageContextInteraction(MessageContextInteractionEvent e) {
		List<String> commands = List.of("JSON 정렬하기", "JSON 압축하기");
		
		if (commands.contains(e.getName())) {
			String json = e.getTarget().getContentRaw();
			
			ObjectMapper objectMapper = new ObjectMapper();
			if (e.getName().equals("JSON 정렬하기")) {
				objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			} else {
				objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
			}
			
			try {
				jsonNode = objectMapper.readTree(json);
				String convertedJson = objectMapper.writeValueAsString(jsonNode);
				
				MessageEmbed successEmbed = new EmbedBuilder()
					.setTitle("**성공!**")
					.setDescription("변환된 JSON")
					.addField("결과", Markdown.codeBlock(convertedJson, "json"), false)
					.setColor(Color.GREEN)
					.build();
				
				e.replyEmbeds(successEmbed).setEphemeral(true).queue();
			} catch (JsonProcessingException ex) {
				MessageEmbed failedEmbed = new EmbedBuilder()
					.setTitle("**실패!**")
					.setDescription("올바르지 않은 JSON")
					.addField("입력", Markdown.codeBlock(json, "json"), false)
					.setColor(Color.RED)
					.build();
				
				e.replyEmbeds(failedEmbed).setEphemeral(true).queue();
			}
		}
	}
}
