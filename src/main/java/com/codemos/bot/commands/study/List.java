package com.codemos.bot.commands.study;

import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.json.ModifyJson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class List extends ListenerAdapter {
	
	ModifyJson modifyJson = new ModifyJson();
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		if (e.getName().equals("단어")) {
			if (e.getSubcommandName().equals("목록")) {
				try {
					String type;
					
					if (e.getOption("type") != null) {
						type = e.getOption("type").getAsString();
					} else {
						type = "mine";
					}
					
					
					EmbedBuilder embed = new EmbedBuilder();
					embed.setTitle(Markdown.bold("배운 목록"));
					embed.setColor(Color.GREEN);
					
					if (modifyJson.getKeys().isEmpty()) {
						embed.setDescription("아무것도 없어요!");
						e.replyEmbeds(embed.build()).queue();
						return;
					}
					
					for (String key : modifyJson.getKeys()) {
						String valueStr = modifyJson.getValue(key).length() > 20 ? modifyJson.getValue(key).substring(0, 20) + "..." : modifyJson.getValue(key);
						
						if (type.equals("mine")) {
							if (modifyJson.getUserId(key).equals(e.getUser().getId())) {
								embed.addField(key, valueStr, false);
							}
						} else if (type.equals("all")) {
							embed.addField(key, valueStr, false);
						}
					}
					
					e.replyEmbeds(embed.build()).queue();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
