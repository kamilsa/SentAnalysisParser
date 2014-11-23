package ru.kamil.innopolis.sentiment.gui;

import ru.kamil.innopolis.sentiment.analysis.AnalisysText;
import ru.kamil.innopolis.sentiment.gui.listeners.MySelectionListener;
import ru.kamil.innopolis.sentiment.parser.New;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kamil on 23.11.14.
 */
public class SentAnFrame extends JFrame {
    private ListPanel listPanel;
    private TextPanel textPanel;
    private AnalysisPanel analysisPanel;
    private ArrayList<New> news;

    public SentAnFrame(ArrayList<New> news){
        this.news = news;
//        this.setLocationRelativeTo(null);

        this.setSize(new Dimension(800, 600));
        this.setLayout(new BorderLayout());



        listPanel = new ListPanel(news);
        this.add(listPanel, BorderLayout.WEST);

        textPanel = new TextPanel(news);
        this.add(textPanel, BorderLayout.CENTER);

        analysisPanel = new AnalysisPanel();

        this.add(analysisPanel, BorderLayout.SOUTH);

        MySelectionListener selListener = new MySelectionListener(this, listPanel, textPanel, analysisPanel);
        listPanel.getTitleList().addListSelectionListener(selListener);


        this.setVisible(true);
    }
}
