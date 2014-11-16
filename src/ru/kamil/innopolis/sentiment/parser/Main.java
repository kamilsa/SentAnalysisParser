package ru.kamil.innopolis.sentiment.parser;

import ru.kamil.innopolis.sentiment.database.DBHelper;

import java.sql.*;

/**
 * Created by kamil on 16.11.14.
 */
public class Main {

    public static void main() throws SQLException, ClassNotFoundException {
        DBHelper.getConnection();
        DBHelper.insertWord("hello", 1.0f, 2.2f, 3.3f, 4.5f,5.5f, 6.6f, 7.7f, 8.8f);
        DBHelper.closeConnection();
    }
}
