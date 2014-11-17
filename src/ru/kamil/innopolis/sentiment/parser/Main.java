package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.stemmer.Stemmer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by kamil on 16.11.14.
 */
public class Main {

    public static void main(String args[]) throws Exception {
        createDatabaseFromTxt("res/DepecheMood_freq.txt"); //createDatabase from txt if it necessary. If not comment this line!! Maybe db has already made.

//        ArrayList<File> list = SentParser.getNews();
//        File file[] = {list.get(0)};
//        Stemmer.stemFile(file);

    }

    public static void createDatabaseFromTxt(String fileStr) throws SQLException, ClassNotFoundException {
        DBHelper.getConnection();
        File file = new File(fileStr);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            line = br.readLine();
            while(line != null){
                StringTokenizer st = new StringTokenizer(line);

                String lemmaWord = st.nextToken();//слово + часть речи
                StringTokenizer stLemmaWord = new StringTokenizer(lemmaWord, "#");
                String lemma = stLemmaWord.nextToken();
                String word = stLemmaWord.nextToken();

                float afraid = Float.parseFloat(st.nextToken());
                float amused = Float.parseFloat(st.nextToken());
                float angry = Float.parseFloat(st.nextToken());
                float annoyed = Float.parseFloat(st.nextToken());
                float dontCare = Float.parseFloat(st.nextToken());
                float happy = Float.parseFloat(st.nextToken());
                float insipred = Float.parseFloat((st.nextToken()));
                float sad = Float.parseFloat(st.nextToken());

//                System.out.println(lemma);
//                System.out.println(word);
//                System.out.println(afraid);
//                System.out.println(amused);
//                System.out.println(angry);
//                System.out.println(annoyed);
//                System.out.println(dontCare);
//                System.out.println(happy);
//                System.out.println(insipred);
//                System.out.println(sad);

                DBHelper.insertWord(lemma, word, afraid, amused, angry, annoyed, dontCare, happy, insipred, sad);

                line = br.readLine();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        DBHelper.closeConnection();
    }
}
