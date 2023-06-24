package com.codemos.bot.commands.meme;

import com.codemos.bot.bots.Api;
import com.codemos.bot.utils.ApiRequest;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.io.IOException;

public class Meme extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		MemeDataParser parser = new MemeDataParser();
		
		if (e.getName().equals("밈")) {
			try {
				String jsonString = ApiRequest.getJsonString(Api.MEME_API_URL);
				
				while (!parser.isUnableToAccess(jsonString)) {
					jsonString = ApiRequest.getJsonString(Api.MEME_API_URL);
				}
				
				boolean isNsfw = parser.isNsfw(jsonString);
				
				while (!isNsfw) {
					jsonString = ApiRequest.getJsonString(Api.MEME_API_URL);
					isNsfw = parser.isNsfw(jsonString);
				}
				
				String postLink = parser.getPostLink(jsonString);
				String title = parser.getTitle(jsonString);
				String url = parser.getUrl(jsonString);
				String author = parser.getAuthor(jsonString);
				int ups = parser.getUps(jsonString);
				
				if (postLink.isBlank() || postLink.isEmpty()) {
					MessageEmbed embed = new EmbedBuilder()
						.setTitle("오류")
						.setDescription("밈을 찾을 수 없습니다.")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(embed).setEphemeral(true).queue();
					return;
				}
				
				MessageEmbed embed = new EmbedBuilder()
					.setTitle(title)
					.setDescription("작성자: " + author)
					.setImage(url)
					.setColor(Color.GREEN)
					.build();
				
				Button urlButton = Button.link(postLink, "더 알아보기");
				Button refreshButton = Button.primary("refresh", "새로고침");
				Button upsButton = Button.primary("ups", "👍 " + ups).asDisabled();
				
				e.replyEmbeds(embed).addActionRow(urlButton, refreshButton, upsButton).queue();
			} catch (IOException ex) {
				MessageEmbed embed = new EmbedBuilder()
					.setTitle("오류")
					.setDescription("밈을 찾을 수 없습니다.")
					.setColor(Color.RED)
					.build();
				
				e.replyEmbeds(embed).setEphemeral(true).queue();
			}
		}
	}
}
