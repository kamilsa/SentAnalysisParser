package ru.kamil.innopolis.sentiment.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by kamil on 16.11.14.
 */

public class DBHelper {

    private static Connection conn;
    private static PreparedStatement insertSt;


    public static void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.
                getConnection("jdbc:h2:~/test", "sa", "123");
        // add application code here
        insertSt = conn.prepareStatement("insert into depeche_mood_freq values(?,?,?,?,?,?,?,?,?,?)");
    }

    public static void closeConnection() throws SQLException {
        conn.close();
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


}
