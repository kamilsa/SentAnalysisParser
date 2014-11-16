package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.database.DBHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.StringTokenizer;

/**
 * Created by kamil on 16.11.14.
 */
public class Main {

    public static void main(String args[]) throws SQLException, ClassNotFoundException {
        DBHelper.getConnection();
        File file = new File("res/DepecheMood_freq.txt");
        System.out.println("Hello");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            line = br.readLine();
            System.out.println(line);
            while(line != null){
                StringTokenizer st = new StringTokenizer(line);
                String lemma = st.nextToken();
                float afraid = Float.parseFloat(st.nextToken());
                float amused = Float.parseFloat(st.nextToken());
                float angry = Float.parseFloat(st.nextToken());
                float annoyed = Float.parseFloat(st.nextToken());
                float dontCare = Float.parseFloat(st.nextToken());
                float happy = Float.parseFloat(st.nextToken());
                float insipred = Float.parseFloat((st.nextToken()));
                float sad = Float.parseFloat(st.nextToken());

//                System.out.println(lemma);
//                System.out.println(afraid);
//                System.out.println(amused);
//                System.out.println(angry);
//                System.out.println(annoyed);
//                System.out.println(dontCare);
//                System.out.println(happy);
//                System.out.println(insipred);
//                System.out.println(sad);

                DBHelper.insertWord(lemma, afraid, amused, angry, annoyed, dontCare, happy, insipred, sad);

                line = br.readLine();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        DBHelper.closeConnection();

    }
}
