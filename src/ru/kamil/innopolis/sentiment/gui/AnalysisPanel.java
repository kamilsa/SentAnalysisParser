package ru.kamil.innopolis.sentiment.gui;

import ru.kamil.innopolis.sentiment.parser.New;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by kamil on 23.11.14.
 */
public class AnalysisPanel extends JPanel {

    private String[] columnNames = {"",
            "Afraid",
            "Amused",
            "Angry",
            "Annoyed",
            "Don't care",
            "Happy",
            "Inpired",
            "Sad"
    };
    public Object[][] data = {
//            {"Prob max", null,null, null, null, null, null, null, null},
            {" max", null,null, null, null, null, null, null, null},
//            {"Prob mean", null,null, null, null, null, null, null, null},
            {"mean ", null,null, null, null, null, null, null, null},
            {"class", null,null, null, null, null, null, null, null}
    };

    private final JTable table;
    public AnalysisPanel() {
        super();
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLUE);
        this.setPreferredSize(new Dimension(this.getWidth(), 100));

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }

    public void setData(New _new){
        HashMap<String, Double> probs = _new.getProbs();
        HashMap<String, Double> meanOfMax = _new.getMeanOfMax();
        HashMap<String, Double> varOfMaxs = _new.getVarOfMaxs();
        HashMap<String, Double> meanOfVars = _new.getMeanOfVars();
        HashMap<String, Double> varOfVars = _new.getVarOfVars();

        String names[] = {"afraid: ","amused: ","angry: ","annoyed: ","dont_care: ","happy: ","inspired: ","sad: "};

        Double totMeanOfMax = 0.0;
        Double totMeanOfVars = 0.0;
        for(String name : names){
            totMeanOfMax += meanOfMax.get(name);
            totMeanOfVars += meanOfVars.get(name);
        }

        for(int i = 1; i < data[0].length; i++){
         //   table.getModel().setValueAt(probs.get(names[i - 1]),     0, i);
            table.getModel().setValueAt(meanOfMax.get(names[i - 1])*100/totMeanOfMax, 0, i);
         //   table.getModel().setValueAt(varOfMaxs.get(names[i - 1]), 2, i);
            table.getModel().setValueAt(meanOfVars.get(names[i - 1])*100/totMeanOfVars,1, i);
//            table.getModel().setValueAt(varOfVars.get(names[i-1]),   4, i);

        }

//        data = {
//                {"Prob", probs.get("afraid"),probs.get("amused"), probs.get("angry"), probs.get("annoyed"), probs.get("dont_care"), probs.get("happy"), probs.get("inspired"), probs.get("sad")},
//                {"mean of max", meanOfMax.get("afraid"),meanOfMax.get("amused"), meanOfMax.get("angry"), meanOfMax.get("annoyed"), meanOfMax.get("dont_care"), meanOfMax.get("happy"), meanOfMax.get("inspired"), meanOfMax.get("sad")},
//                {"var of maxs", varOfMaxs.get("afraid"),varOfMaxs.get("amused"), varOfMaxs.get("angry"), varOfMaxs.get("annoyed"), varOfMaxs.get("dont_care"), varOfMaxs.get("happy"), varOfMaxs.get("inspired"), varOfMaxs.get("sad")},
//                {"mean of vars", meanOfVars.get("afraid"),meanOfVars.get("amused"), meanOfVars.get("angry"), meanOfVars.get("annoyed"), meanOfVars.get("dont_care"), meanOfVars.get("happy"), meanOfVars.get("inspired"), meanOfVars.get("sad")},
//                {"var of vars", varOfVars.get("afraid"),varOfVars.get("amused"), varOfVars.get("angry"), varOfVars.get("annoyed"), varOfVars.get("dont_care"), varOfVars.get("happy"), varOfVars.get("inspired"), varOfVars.get("sad")}
//        };

    }

    public void setClassData(HashMap<String, Double> info) {
        String names[] = {"afraid","amused","angry","annoyed","dont_care","happy","inspired", "sad"};
        System.out.println(info.get(names[2]));
        for(int i = 1; i < data[0].length; i++){
            table.getModel().setValueAt(info.get(names[i - 1]), 2, i);
        }
    }
}
