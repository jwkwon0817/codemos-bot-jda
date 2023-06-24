package com.codemos.bot.commands.json;

import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.json.JsonKeyRetriever;
import com.codemos.bot.utils.json.JsonValueRetriever;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.List;

public class PrintJson extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		List<String> subCommands = List.of("키", "값");
		
		User bot = Bot.getBot();
		
		if (e.getName().equals("json")) {
			String json = e.getOption("json").getAsString();
			if (subCommands.contains(e.getSubcommandName())) {
				try {
					if (e.getSubcommandName().equals("키")) {
						List<String> allKeys = JsonKeyRetriever.getAllKeys(json);
						
						MessageEmbed successEmbed = new EmbedBuilder()
							.setTitle("**성공!**")
							.setDescription("조회된 JSON 키")
							.addField("키", Markdown.codeBlock(String.join("\n", allKeys), "json"), false)
							.setColor(Color.GREEN)
							.build();
						
						e.replyEmbeds(successEmbed).queue();
					} else {
						List<String> allValues = JsonValueRetriever.getAllValues(json);
						
						MessageEmbed successEmbed = new EmbedBuilder()
							.setTitle("**성공!**")
							.setDescription("조회된 JSON 값")
							.addField("값", Markdown.codeBlock(String.join("\n", allValues), "json"), false)
							.setColor(Color.GREEN)
							.build();
						
						e.replyEmbeds(successEmbed).queue();
					}
				} catch (IllegalArgumentException ex) {
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
		List<String> commands = List.of("JSON 키 출력하기", "JSON 값 출력하기");
		
		String json = e.getTarget().getContentRaw();
		if (commands.contains(e.getName())) {
			try {
				if (e.getName().equals("JSON 키 출력하기")) {
					List<String> allKeys = JsonKeyRetriever.getAllKeys(json);
					
					MessageEmbed successEmbed = new EmbedBuilder()
						.setTitle("**성공!**")
						.setDescription("조회된 JSON 키")
						.addField("키", Markdown.codeBlock(String.join("\n", allKeys), "json"), false)
						.setColor(Color.GREEN)
						.build();
					
					e.replyEmbeds(successEmbed).setEphemeral(true).queue();
				} else {
					List<String> allValues = JsonValueRetriever.getAllValues(json);
					
					MessageEmbed successEmbed = new EmbedBuilder()
						.setTitle("**성공!**")
						.setDescription("조회된 JSON 값")
						.addField("값", Markdown.codeBlock(String.join("\n", allValues), "json"), false)
						.setColor(Color.GREEN)
						.build();
					
					e.replyEmbeds(successEmbed).setEphemeral(true).queue();
				}
			} catch (IllegalArgumentException ex) {
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
