package com.codemos.bot.commands.study;

import com.codemos.bot.utils.Markdown;
import com.codemos.bot.utils.json.ModifyJson;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class Teach extends ListenerAdapter {
	
	ModifyJson modifyJson = new ModifyJson();
	
	
	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent e) {
		if (e.getName().equals("단어")) {
			if (e.getSubcommandName().equals("가르치기")) {
				TextInput key = TextInput.create("study-keyword", "키워드", TextInputStyle.SHORT)
					.setRequired(true)
					.setPlaceholder("키워드를 입력해주세요.")
					.setMaxLength(20)
					.build();
				
				TextInput value = TextInput.create("study-response", "응답", TextInputStyle.PARAGRAPH)
					.setRequired(true)
					.setPlaceholder("응답을 입력해주세요.")
					.build();
				
				Modal studyModal = Modal.create("study-modal", "가르치기")
					.addActionRows(ActionRow.of(key), ActionRow.of(value))
					.build();
				
				e.replyModal(studyModal).queue();
			}
		}
	}
	
	@Override
	public void onModalInteraction(ModalInteractionEvent e) {
		if (e.getModalId().equals("study-modal")) {
			String key = e.getValue("study-keyword").getAsString();
			String value = e.getValue("study-response").getAsString();
			
			value = value.replace("@", "@\u200B");
			
			if (modifyJson.hasKey(key)) {
				MessageEmbed failEmbed = new EmbedBuilder()
					.setTitle(Markdown.bold("실패!"))
					.setDescription("이미 그런 키워드가 있어요!")
					.addField("키워드", key, false)
					.addField("응답", modifyJson.getValue(key), false)
					.addField("키워드 주인", Markdown.mention(modifyJson.getUserId(key)), false)
					.setColor(Color.RED)
					.build();
				
				e.replyEmbeds(failEmbed).queue();
				return;
			}
			
			modifyJson.add(e.getUser(), key, value);
			
			MessageEmbed successEmbed = new EmbedBuilder()
				.setTitle(Markdown.bold("성공!"))
				.setDescription("키워드를 추가했어요!")
				.addField("키워드", key, false)
				.addField("응답", value, false)
				.setColor(Color.GREEN)
				.build();
			
			e.replyEmbeds(successEmbed).queue();
		}
	}
}
