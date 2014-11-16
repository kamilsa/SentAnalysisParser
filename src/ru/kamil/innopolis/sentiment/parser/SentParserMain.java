package ru.kamil.innopolis.sentiment.parser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;

public class SentParserMain {

	public static void getNews() throws Exception {
		
//		String ac = "AC/DC";
//		ac = ac.replace("/", "");
//		System.out.println(ac);
//		System.exit(0);
		
		File path = new File(new java.util.Date().toString() + "/");
		System.out.println(path.getAbsolutePath());
		System.out.println(path.mkdirs());
		
		TheGuardianPageParser guardian = new TheGuardianPageParser();
		HashSet<String> newsUrls = guardian
				.getNewsUrls("http://www.theguardian.com/uk");
		int i = 0;
		PrintWriter out;
		for (String urlStr : newsUrls) {
			New _new = guardian.getNew(urlStr);
			if (_new == null)
				continue;
			System.out.println("i = " + i++);
			System.out.println(_new.getUrlStr());
			System.out.println(_new.getTitle());
			System.out.println(_new.getText());
			
			String fileName = _new.getTitle().split("\\|")[0];
			fileName = fileName.replaceAll("/", "");
			File newFile = new File(path.getAbsolutePath() + "/" + fileName + ".txt");
			out = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));
			out.print(_new.getText());
			out.flush();
			System.out.println("finished");
			// break;
		}
		
	}

}
