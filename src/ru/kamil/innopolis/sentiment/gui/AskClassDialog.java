package ru.kamil.innopolis.sentiment.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kamil on 23.11.14.
 */
public class AskClassDialog extends JDialog {

    private final JTable table;
    private Object[][] data = {
            {null, null, null, null, null, null, null, null, null},
    };
    private String[] columnNames = {"number of members",
            "Afraid",
            "Amused",
            "Angry",
            "Annoyed",
            "Don't care",
            "Happy",
            "Inpired",
            "Sad"
    };
    public AskClassDialog(JFrame frame){
        super(frame);

        this.setSize(new Dimension(800, 300));

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        add(table.getTableHeader(), BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
