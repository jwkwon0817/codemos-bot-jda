package com.codemos.bot.commands.study;

import com.codemos.bot.bots.Bot;
import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.json.ModifyJson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class Forget extends ListenerAdapter {
	
	ModifyJson modifyJson = new ModifyJson();
	String key;
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		if (e.getName().equals("단어")) {
			if (e.getSubcommandName().equals("까먹기")) {
				key = e.getOption("word").getAsString();
				
				if (!modifyJson.hasKey(key)) {
					MessageEmbed failEmbed = new EmbedBuilder()
						.setTitle(Markdown.bold("실패!"))
						.setDescription("그런 키워드는 없어요!")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(failEmbed).queue();
					return;
				}
				
				boolean isManager = e.getUser().getId().equals(Bot.MANAGER_ID);
				
				if (modifyJson.getUserId(key).equals(e.getUser().getId())) {
					
					modifyJson.remove(key);
					MessageEmbed successEmbed = new EmbedBuilder()
						.setTitle(Markdown.bold("성공!"))
						.setDescription("키워드를 삭제했어요!")
						.addField("키워드", key, false)
						.setColor(Color.GREEN)
						.build();
					
					e.replyEmbeds(successEmbed).queue();
				} else {
					if (isManager) {
						MessageEmbed areYouSureEmbed = new EmbedBuilder()
							.setTitle(Markdown.bold("경고!"))
							.setDescription("정말로 키워드를 삭제할 건가요?")
							.addField("키워드", key, false)
							.addField("키워드 주인", Markdown.mention(modifyJson.getUserId(key)), false)
							.setColor(Color.YELLOW)
							.build();
						
						Button yesButton = Button.success("study-manger-keyword-delete-yes", "네");
						Button noButton = Button.danger("study-manger-keyword-delete-no", "아니요");
						
						e.replyEmbeds(areYouSureEmbed).setActionRow(yesButton, noButton).setEphemeral(true).queue();
						return;
					}
					
					MessageEmbed failEmbed = new EmbedBuilder()
						.setTitle(Markdown.bold("실패!"))
						.setDescription(Markdown.code(key) + " 키워드는 당신이 만든 게 아니에요!")
						.setColor(Color.RED)
						.build();
					
					e.replyEmbeds(failEmbed).queue();
				}
			}
		}
	}
	
	@Override
	public void onButtonInteraction(ButtonInteractionEvent e) {
		Button yesButton = Button.success("study-manger-keyword-delete-yes", "네").asDisabled();
		Button noButton = Button.danger("study-manger-keyword-delete-no", "아니요").asDisabled();
		
		if (e.getComponentId().equals("study-manger-keyword-delete-yes")) {
			modifyJson.remove(key);
			
			MessageEmbed successEmbed = new EmbedBuilder()
				.setTitle(Markdown.bold("성공!"))
				.setDescription("키워드를 삭제했어요!")
				.addField("키워드", key, false)
				.setColor(Color.GREEN)
				.build();
			
			e.editMessageEmbeds(successEmbed).setActionRow(yesButton, noButton).queue();
		} else if (e.getComponentId().equals("study-manger-keyword-delete-no")) {
			MessageEmbed successEmbed = new EmbedBuilder()
				.setTitle(Markdown.bold("성공!"))
				.setDescription("키워드 삭제를 취소했어요!")
				.addField("키워드", key, false)
				.setColor(Color.GREEN)
				.build();
			
			e.editMessageEmbeds(successEmbed).setActionRow(yesButton, noButton).queue();
		}
	}
}
