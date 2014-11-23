package ru.kamil.innopolis.sentiment.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TheGuardianPageParser {
	private URL url;
	private ArrayList<URL> newsUrlsList;
	private String html;

	public TheGuardianPageParser(URL url) throws Exception {
		this.url = url;
		html = getContentOfHTTPPage();
	}

	public TheGuardianPageParser(String urlStr) throws Exception {
		this.url = new URL(urlStr);
		html = getContentOfHTTPPage();

	}

	public TheGuardianPageParser() throws Exception {
		this.url = new URL("http://www.theguardian.com/uk");
		html = getContentOfHTTPPage();
	}

	public New getNew(String newsUrlStr) throws IOException {
		try {
			Document doc = Jsoup.connect(newsUrlStr).get();
			String text = doc.select("div.flexible-content-body").first().text();
			String title = doc.select("title").first().text();
			return new New(title, text, newsUrlStr);
		} catch (SocketTimeoutException ste) {
			return null;
		}
		catch (NullPointerException npe){
			return null;
		}
	}

	private String getContentOfHTTPPage() throws Exception {
		StringBuilder sb = new StringBuilder();
		URL pageURL = url;
		URLConnection uc = pageURL.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				uc.getInputStream(), "utf-8"));
		try {
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	private String getContentOfHTTPPage(URL urll) throws Exception {
		StringBuilder sb = new StringBuilder();
		URL pageurll = urll;
		URLConnection uc = pageurll.openConnection();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				uc.getInputStream(), "utf-8"));
		try {
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	public HashSet<String> getNewsUrls(String urlStr) throws IOException {
		/*
		 * newsUrlsList = new ArrayList<URL>();
		 * 
		 * Pattern p =
		 * Pattern.compile("<h3>.*<a href=\"(http://www.theguardian.com/(.*))\""
		 * );
		 * 
		 * Matcher m = p.matcher(html); while(m.find()){ String newsUrl =
		 * m.group(1); if(newsUrl.contains("/video/")) continue;
		 * newsUrlsList.add(new URL(newsUrl)); } return newsUrlsList;
		 */
		HashSet<String> newsUrls = new HashSet<String>();
		Document doc = Jsoup.connect(urlStr).get();
		int i = 0;
		Pattern p = Pattern
				.compile("http://www.theguardian.com/.*/.*/.*/.*/.*");
		for (Element e : doc.select("a")) {
			String link = e.attr("href");
			Matcher m = p.matcher(link);
			if (m.find()) {
				newsUrls.add(link);
				i++;
			}
		}

		return newsUrls;
	}

}
