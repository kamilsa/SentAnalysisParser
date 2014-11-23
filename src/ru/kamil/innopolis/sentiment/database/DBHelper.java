package ru.kamil.innopolis.sentiment.database;

import ru.kamil.innopolis.sentiment.analysis.StemmedWord;

import java.sql.*;
import java.util.Vector;

/**
 * Created by kamil on 16.11.14.
 */

public class DBHelper {



    private DBHelper(){}

    private static Connection conn;
    private static PreparedStatement insertSt;
    private static PreparedStatement selectSt;
    private static PreparedStatement selectStStem;


    private static boolean connected = false;

    public boolean isConnected(){
        return connected;
    }

    public static void getConnection() throws SQLException, ClassNotFoundException {
        if(connected) return;
        Class.forName("org.h2.Driver");
        conn = DriverManager.
                getConnection("jdbc:h2:~/test", "sa", "123");
        // add application code here
        insertSt = conn.prepareStatement("insert into depeche_mood_freq values(?,?,?,?,?,?,?,?,?,?)");
        selectSt = conn.prepareStatement("select * from depeche_mood_freq where lemma = ? and pos = ?");
        selectStStem = conn.prepareStatement("select * from depeche_mood_freq_stem where lemma = ? and pos = ?");
        connected = true;
    }

    public static void closeConnection() throws SQLException {
        conn.close();
        connected = false;
    }

    public static void insertWord(String lemma, String word, float afraid, float amused, float angry,
                                  float annoyed, float dont_care, float happy, float inspired, float sad) throws SQLException {
        insertSt.setString(1, lemma);
        insertSt.setString(2, word);
        insertSt.setFloat(3, afraid);
        insertSt.setFloat(4, amused);
        insertSt.setFloat(5, angry);
        insertSt.setFloat(6, annoyed);
        insertSt.setFloat(7, dont_care);
        insertSt.setFloat(8, happy);
        insertSt.setFloat(9, inspired);
        insertSt.setFloat(10, sad);

        insertSt.execute();
    }



    public static Vector<Float> getWord(StemmedWord sw){
        Vector<Float> res = new Vector<Float>();
        if(!connected) return null;
        try {
            selectSt.setString(1, sw.getOrigText());
            selectSt.setString(2, sw.getTag());

            System.out.println(selectSt.toString());

            ResultSet rs = selectSt.executeQuery();
            if(!rs.next()){
                selectStStem.setString(1, sw.getStemText());
                selectStStem.setString(2, sw.getTag());
                System.out.println(selectStStem.toString());

                ResultSet rs1 = selectStStem.executeQuery();
                if(rs1.next() == false){
                    Vector<Float> res1 = new Vector<Float>(8);
                    return null;
                }
                rs = rs1;
            }
            float adding = 0;
            if (sw.getTag().equals("v"))
                adding +=0.05;

            res.add(rs.getFloat("afraid"));
            res.add(rs.getFloat("amused"));
            res.add(rs.getFloat("angry"));
            res.add(rs.getFloat(("annoyed")));
            res.add(rs.getFloat("dont_care"));
            res.add(rs.getFloat("happy"));
            res.add(rs.getFloat("inspired"));
            res.add(rs.getFloat("sad"));

            float max = 0;
            int maxi = 0;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i) > max)
                {
                 maxi = i;
                    max = res.get(i);
                }
            }
            res.set(maxi, max+adding);

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
