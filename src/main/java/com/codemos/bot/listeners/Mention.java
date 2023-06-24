package com.codemos.bot.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Mention extends ListenerAdapter {
	
	@Override
	public void onMessageReceived(MessageReceivedEvent e) {
		Message message = e.getMessage();
		
		boolean mentioned = message.getMentions().isMentioned(e.getJDA().getSelfUser());
		
		if (mentioned) {
			message.addReaction(Emoji.fromUnicode("✅")).queue();
		}
	}
}
