package com.codemos.bot.commands.study;

import com.codemos.bot.utils.json.ModifyJson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Utilize extends ListenerAdapter {
	
	ModifyJson modifyJson = new ModifyJson();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		User bot = e.getJDA().getSelfUser();
		
		Message message = e.getMessage();
		
		if (e.getAuthor().isBot()) return;
		
		if (message.getContentRaw().startsWith(bot.getAsMention() + " ")) {
			String command = message.getContentRaw().replace(bot.getAsMention() + " ", "");
			
			if (modifyJson.hasKey(command)) {
				message.reply(modifyJson.getValue(command)).mentionRepliedUser(false).queue();
			} else {
				MessageEmbed embed = new EmbedBuilder()
					.setTitle("그런 키워드는 없어요!")
					.setColor(Color.RED)
					.build();
				
				message.replyEmbeds(embed).mentionRepliedUser(false).queue();
			}
		}
	}
}
