package com.codemos.bot.commands;

import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Ping extends ListenerAdapter {
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		if (e.getName().equals("지연시간")) {
			long gatewayPing = e.getJDA().getGatewayPing();
			
			User bot = Bot.getBot();
			
			MessageEmbed pingEmbed = new EmbedBuilder()
				.setAuthor(bot.getName(), Bot.getAvatarUrl(bot), Bot.getAvatarUrl(bot))
				.setTitle(Markdown.bold("지연시간"))
				.setDescription("Gateway 지연시간: " + gatewayPing + "ms")
				.setColor(judgeColor(gatewayPing))
				.setFooter("요청자: " + e.getUser().getName(), Bot.getAvatarUrl(e.getUser()))
				.build();
			
			e.replyEmbeds(pingEmbed).queue();
		}
	}
	
	private static Color judgeColor(long gatewayPing) {
		if (gatewayPing < 100) {
			return Color.GREEN;
		} else if (gatewayPing < 200) {
			return Color.BLUE;
		} else if (gatewayPing < 300) {
			return Color.YELLOW;
		} else {
			return Color.RED;
		}
	}
}
