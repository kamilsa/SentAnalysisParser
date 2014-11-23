package ru.kamil.innopolis.sentiment.gui;

import ru.kamil.innopolis.sentiment.gui.listeners.MySelectionListener;
import ru.kamil.innopolis.sentiment.parser.New;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by kamil on 23.11.14.
 */
public class ListPanel extends JPanel {
    private ArrayList<New> news;
    private ArrayList<String> newsTitles;


    private JList titleList;
    private JScrollPane sp;

    private MySelectionListener selListener;
    public ListPanel(ArrayList<New> news){

        this.news = news;
        this.newsTitles = new ArrayList<String>();
        for(New _new : news){
            newsTitles.add(_new.getTitle());
        }

        titleList = new JList(newsTitles.toArray());
        sp = new JScrollPane(titleList);
        sp.createHorizontalScrollBar();
        this.add(sp);
        this.setBackground(Color.RED);
        this.setPreferredSize(new Dimension(200, this.getHeight()));

    }

    public ArrayList<String> getNewsTitles(){
        return newsTitles;
    }
    public JList getTitleList(){
        return titleList;
    }
    public ArrayList<New> getNews(){
        return news;
    }
}
