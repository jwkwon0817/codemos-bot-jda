package com.codemos.bot.utils;

public class Markdown {
	
	public static String bold(String text) {
		return "**" + text + "**";
	}
	
	public static String italic(String text) {
		return "_" + text + "_";
	}
	
	public static String underline(String text) {
		return "__" + text + "__";
	}
	
	public static String mention(String id) {
		return "<@" + id + ">";
	}
	
	public static String strike(String text) {
		return "~~" + text + "~~";
	}
	
	public static String code(String text) {
		return "`" + text + "`";
	}
	
	public static String codeBlock(String text) {
		return "```\n" + text + "\n```";
	}
	
	public static String codeBlock(String text, String language) {
		return "```" + language + "\n" + text + "\n```";
	}
	
	public static String blockQuote(String text) {
		return "> " + text;
	}
	
	public static String spoiler(String text) {
		return "||" + text + "||";
	}
	
	public static String timeUnit(long time) {
		return "<t:" + time + ">";
	}
	
	public static String timeUnit(long time, String style) {
		return "<t:" + time + ":" + style + ">";
	}
}
