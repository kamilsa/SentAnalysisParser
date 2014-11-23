package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.analysis.AnalisysText;
import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.gui.SentAnFrame;
import ru.kamil.innopolis.sentiment.stemmer.Stemmer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;



// * Created by kamil on 16.11.14.
 //*/
public class Main {

    public static void main(String args[]) throws Exception {
//        createDatabaseFromTxt("res/DepecheMood_normfreq.txt"); //createDatabase from txt if it necessary. If not comment this line!! Maybe db has already made.

//        ArrayList<New> list = SentParser.getNews();

//        AnalisysText at = new AnalisysText();
//        at.procedureNew(at.someNew);


        //----------------------------------------------------------------
        //test GUI
        String someTitle1 = "Die"; //-
        String someText1 = "Everybody has to dye. Get ready to dye. Badly injured people over 1 million.";
        String someUrl1 = "http://someurl1.com";
        New new1 = new New(someTitle1,someText1,someUrl1);

        String someTitle2 = "Putin kills Obama"; //-
        String someText2 = "Putin kills Obama. Obama cried for help. But nobody listened.";
        String someUrl2 = "http://someurl2.com";
        New new2 = new New(someTitle2,someText2,someUrl2);

        String someTitle3 = "Kamil becomes a billionaire."; //-
        String someText3 = "Kamil has got his first billion dollars. He was very happy.";
        String someUrl3 = "http://someurl3.com";
        New new3 = new New(someTitle3,someText3,someUrl3);


        String someTitle4 = "Free lunch for everyone! Go get it now "; //-
        String someText4 = "Free lunch for everyone! Go get it now.";
        String someUrl4 = "http://someurl3.com";
        New new4 = new New(someTitle4,someText4,someUrl4);

        String someTitle5 = "Important "; //-
        String someText5 = "Very important news! We have tomorrow a lot of fun. There will be beautiful day and weather. We will make picnic!";
        String someUrl5 = "http://someurl3.com";
        New new5 = new New(someTitle5,someText5,someUrl5);

        String someTitle6 = "Sad news "; //-
        String someText6 = "Very bad news! A good man died. He was good guy wgo helped many people. Now we are crying without him";
        String someUrl6 = "http://someurl3.com";
        New new6 = new New(someTitle6,someText6,someUrl6);


        ArrayList<New> news = new ArrayList<New>();
        news.add(new1);
        news.add(new2);
        news.add(new3);
        news.add(new4);
        news.add(new5);
        news.add(new6);


        SentAnFrame frame = new SentAnFrame(news);
        //------------------------------------------------------------------



    }

    public static void createDatabaseFromTxt(String fileStr) throws SQLException, ClassNotFoundException {
        DBHelper.getConnection();
        File file = new File(fileStr);
        String line = null;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            line = br.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        while (line != null) {
            try {
                StringTokenizer st = new StringTokenizer(line);

                String lemmaWord = st.nextToken();//слово + часть речи
                StringTokenizer stLemmaWord = new StringTokenizer(lemmaWord, "#");
                String lemma = stLemmaWord.nextToken();
//                lemma = Stemmer.stemWord(lemma);
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
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    line = br.readLine();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    ;
                }
            }
        }
        DBHelper.closeConnection();
    }
}
