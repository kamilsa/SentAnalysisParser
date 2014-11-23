package ru.kamil.innopolis.sentiment.gui;

/**
 * Created by kamil on 23.11.14.
 */
import ru.kamil.innopolis.sentiment.parser.New;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.*;

/* ListDemo.java requires no other files. */
public class TextPanel extends JPanel{

    private JTextPane tp;
    private JScrollPane sp;
    private ArrayList<New> news;

    public TextPanel(ArrayList<New> news) {
        super(new BorderLayout());
        this.news = news;

        tp =  new JTextPane();

        tp.setEditable(false);
        sp = new JScrollPane(tp);

        this.add(sp);


    }

    public JTextPane getTp(){
        return tp;
    }
}
