package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.analysis.AnalisysText;
import ru.kamil.innopolis.sentiment.database.DBHelper;
import ru.kamil.innopolis.sentiment.stemmer.Stemmer;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;


// * Created by kamil on 16.11.14.
 //*/
public class Main {

    public static void main(String args[]) throws Exception {
//        createDatabaseFromTxt("res/DepecheMood_normfreq.txt"); //createDatabase from txt if it necessary. If not comment this line!! Maybe db has already made.

//        ArrayList<New> list = SentParser.getNews();

        AnalisysText at = new AnalisysText();
        at.procedureNew(at.someNew);



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
