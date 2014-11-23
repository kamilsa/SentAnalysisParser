package ru.kamil.innopolis.sentiment.gui.listeners;

import ru.kamil.innopolis.sentiment.gui.AnalysisPanel;
import ru.kamil.innopolis.sentiment.gui.AskClassDialog;
import ru.kamil.innopolis.sentiment.gui.ListPanel;
import ru.kamil.innopolis.sentiment.gui.TextPanel;
import ru.kamil.innopolis.sentiment.parser.New;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by kamil on 23.11.14.
 */
public class MySelectionListener implements ListSelectionListener {
    private String last = null;

    private JFrame frame;
    private ListPanel lp;
    private TextPanel tp;
    private AnalysisPanel ap;

    public MySelectionListener(JFrame frame, ListPanel lp, TextPanel tp, AnalysisPanel ap) {
        this.frame = frame;
        this.lp = lp;
        this.tp = tp;
        this.ap = ap;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        JList titleList = lp.getTitleList();
        if(last == null){
            last = (String)titleList.getSelectedValue();
            for(New _new : lp.getNews()){
                if(_new.getTitle().equals(last)){
                    //ToDo
                    new AskClassDialog(frame).setVisible(true);
                    tp.getTp().setText(_new.getText());
                    ap.setData(_new);
                    break;
                }
            }
            System.out.println(titleList.getSelectedValue());
            return;
        }
        if(!last.equals(titleList.getSelectedValue())){
            System.out.println(titleList.getSelectedValue());
            for(New _new : lp.getNews()){
                if(_new.getTitle().equals(titleList.getSelectedValue())){
                    tp.getTp().setText(_new.getText());
                    ap.setData(_new);
                    break;
                }
            }
        }
        last = (String) titleList.getSelectedValue();
    }
}
