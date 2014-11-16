package ru.kamil.innopolis.sentiment.parser;

public class New {
	private String title;
	private String text;
	private String urlStr;
	
	
	public New(String title, String text, String urlStr) {
		super();
		this.title = title;
		this.text = text;
		this.urlStr = urlStr;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrlStr() {
		return urlStr;
	}
	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}
	
}
