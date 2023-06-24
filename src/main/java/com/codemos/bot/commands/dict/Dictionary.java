package com.codemos.bot.commands.dict;

import com.codemos.bot.bots.Api;
import com.codemos.bot.utils.ApiRequest;
import com.codemos.bot.utils.Markdown;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.IOException;

public class Dictionary extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		DictionaryDataParser parser = new DictionaryDataParser();
		
		if (e.getName().equals("사전")) {
			if (e.getSubcommandName().equals("국어")) {
				String wordOption = e.getOption("word").getAsString();
				
				String url = Api.DICTIONARY_API_URL + "?key=" + Api.DICTIONARY_API_KEY + "&q=" + wordOption + "&req_type=json";
				String jsonString = null;
				try {
					jsonString = ApiRequest.getJsonString(url);
				} catch (IOException ex) {
					MessageEmbed embed = new EmbedBuilder()
						.setTitle("오류")
						.setDescription("단어를 찾을 수 없습니다.")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(embed).queue();
					return;
				}
				
				if (jsonString.isBlank() || jsonString.isEmpty()) {
					MessageEmbed embed = new EmbedBuilder()
						.setTitle("오류")
						.setDescription("단어를 찾을 수 없습니다.")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(embed).queue();
					return;
				}
				
				try {
					String word = parser.getWord(jsonString);
					String definition = parser.getDefinition(jsonString);
					String link = parser.getLink(jsonString);
					String type = parser.getType(jsonString);
					String pos = parser.getPos(jsonString);
					
					MessageEmbed embed = new EmbedBuilder()
						.setTitle("📖 " + word)
						.setDescription(definition)
						.addField(Markdown.bold("[ 품사 ]"), pos, true)
						.addField(Markdown.bold("[ 종류 ]"), type, true)
						.setColor(Color.GREEN)
						.build();
					
					Button button = Button.link(link, "더 알아보기");
					
					e.replyEmbeds(embed).setActionRow(button).queue();
				} catch (JsonProcessingException ex) {
					MessageEmbed embed = new EmbedBuilder()
						.setTitle("오류")
						.setDescription("단어를 찾을 수 없습니다.")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(embed).queue();
				}
			}
		}
	}
}
