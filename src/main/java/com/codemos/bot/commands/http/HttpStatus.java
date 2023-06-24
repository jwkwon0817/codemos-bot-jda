package com.codemos.bot.commands.http;

import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.http.HttpStatusChecker;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class HttpStatus extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		User bot = Bot.getBot();
		
		if (e.getName().equals("http")) {
			if (e.getSubcommandName().equals("상태코드")) {
				String method;
				
				if (e.getOption("method") != null) {
					method = e.getOption("method").getAsString();
				} else {
					method = "GET";
				}
				
				String url = e.getOption("url").getAsString();
				try {
					String httpStatus = HttpStatusChecker.getHttpStatus(method, url);
					
					MessageEmbed successEmbed = new EmbedBuilder()
						.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
						.setTitle(Markdown.bold("성공!"))
						.setDescription("조회된 HTTP 상태 코드")
						.addField("HTTP 상태", Markdown.codeBlock(httpStatus, "json"), false)
						.setColor(Color.GREEN)
						.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
						.build();
					
					e.replyEmbeds(successEmbed).queue();
				} catch (IllegalArgumentException ex) {
					MessageEmbed failedEmbed = new EmbedBuilder()
						.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
						.setTitle(Markdown.bold("실패!"))
						.setDescription("올바르지 않은 URL")
						.addField("입력", Markdown.codeBlock(url, "json"), false)
						.setColor(Color.RED)
						.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
						.build();
					
					e.replyEmbeds(failedEmbed).queue();
				}
			}
		}
	}
}
