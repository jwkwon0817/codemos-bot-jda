package com.codemos.bot;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.codemos.bot.bots.Bot;
import com.codemos.bot.commands.Ping;
import com.codemos.bot.commands.api.Animal;
import com.codemos.bot.commands.api.Weather;
import com.codemos.bot.commands.dict.Dictionary;
import com.codemos.bot.commands.http.HttpStatus;
import com.codemos.bot.commands.json.ConvertJson;
import com.codemos.bot.commands.json.PrintJson;
import com.codemos.bot.commands.meme.Meme;
import com.codemos.bot.commands.study.Forget;
import com.codemos.bot.commands.study.List;
import com.codemos.bot.commands.study.Teach;
import com.codemos.bot.commands.study.Utilize;
import com.codemos.bot.listeners.Mention;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.LoggerFactory;

public class Main extends ListenerAdapter {
	
	public static void main(String[] args) throws Exception {
		Logger jdaLogger = (Logger)LoggerFactory.getLogger("net.dv8tion.jda");
		Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		jdaLogger.setLevel(Level.OFF);
		
		JDA guild = JDABuilder.createDefault(Bot.TOKEN)
			.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE)
			.setBulkDeleteSplittingEnabled(false)
			.setMemberCachePolicy(MemberCachePolicy.VOICE.or(MemberCachePolicy.OWNER))
			.setChunkingFilter(ChunkingFilter.NONE)
			.disableIntents(GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
			.setLargeThreshold(50)
			
			.addEventListeners(
				new Ping(),
				new ConvertJson(),
				new PrintJson(),
				new HttpStatus(),
				new Weather(),
				new Animal()
			)
			.addEventListeners(
				new Mention()
			)
			.addEventListeners(
				new Teach(),
				new Forget(),
				new Utilize(),
				new List()
			)
			.addEventListeners(
				new Dictionary(),
				new Meme()
			)
			
			.setStatus(OnlineStatus.ONLINE)
			.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_TYPING)
			.build();
		
		Bot.setBot(guild.getSelfUser());
		
		guild.awaitReady();
		
		guild.updateCommands().addCommands(
			new CommandData[] {
				Commands.slash("지연시간", "봇 지연시간을 알려줍니다."),
				Commands.slash("json", "JSON 관련 유틸리티 기능입니다.")
					.addSubcommands(
					new SubcommandData("정렬", "JSON을 정렬합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "json", "JSON").setRequired(true)
						),
					new SubcommandData("압축", "JSON을 압축합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "json", "JSON").setRequired(true)
						),
					new SubcommandData("키", "JSON의 모든 키를 출력합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "json", "JSON").setRequired(true)
						),
					new SubcommandData("값", "JSON의 모든 값을 출력합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "json", "JSON").setRequired(true)
						)
				),
				Commands.message("JSON 정렬하기"),
				Commands.message("JSON 압축하기"),
				Commands.message("JSON 키 출력하기"),
				Commands.message("JSON 값 출력하기"),
				Commands.slash("http", "HTTP 관련 유틸리티 기능입니다.")
					.addSubcommands(
					new SubcommandData("상태코드", "HTTP 상태 코드를 조회합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "url", "URL", true),
							new OptionData(OptionType.STRING, "method", "HTTP 메서드", false)
								.addChoices(
									new Command.Choice("GET (기본)", "GET"),
									new Command.Choice("POST", "POST"),
									new Command.Choice("PUT", "PUT"),
									new Command.Choice("DELETE", "DELETE"),
									new Command.Choice("PATCH", "PATCH"),
									new Command.Choice("HEAD", "HEAD"),
									new Command.Choice("OPTIONS", "OPTIONS")
								)
						)
				),
				Commands.slash("날씨", "날씨 정보를 조회합니다.")
					.addOptions(
					new OptionData(OptionType.STRING, "location", "위치 (영어)").setRequired(true)
				),
				Commands.slash("동물", "동물 관련 기능입니다.")
					.addSubcommands(
					new SubcommandData("강아지", "랜덤 강아지 이미지를 조회합니다."),
					new SubcommandData("고양이", "랜덤 고양이 이미지를 조회합니다.")
				),
				Commands.slash("단어", "단어를 봇에게 가르치고 까먹게하는 명령어입니다.").
					addSubcommands(
					new SubcommandData("가르치기", "봇에게 단어를 가르칩니다."),
					new SubcommandData("까먹기", "봇에게 가르친 단어를 까먹게 합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "word", "단어", true)
						),
					new SubcommandData("목록", "봇이 가르친 단어의 목록을 출력합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "type", "종류", false)
								.addChoices(
									new Command.Choice("전체", "all"),
									new Command.Choice("자신", "mine")
								)
						)
				),
				Commands.slash("사전", "단어를 검색합니다.")
					.addSubcommands(
					new SubcommandData("국어", "국어 사전에서 단어를 검색합니다.")
						.addOptions(
							new OptionData(OptionType.STRING, "word", "단어", true)
						)
				),
				Commands.slash("밈", "무작위로 밈을 보여줍니다.")
			}).queue();
		
		logger.info(guild.getSelfUser().getAsTag() + " is ready!");
	}
}