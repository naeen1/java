package Ranking;
import javax.swing.*;

import DBManager.DBManager;

import java.awt.*;
import java.util.List;

public class Ranking extends JFrame {
    public Ranking() {
    	getContentPane().setBackground(Color.WHITE);
        setTitle("랭킹");
        List<String[]> list = DBManager.getTop5();

        String[] cols = {"ID", "Score"};
        String[][] data = list.toArray(new String[0][0]);
        getContentPane().setLayout(null);
        JTable table = new JTable(data, cols);
        table.setFont(new Font("굴림", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(12, 76, 198, 108);
        getContentPane().add(scrollPane);
        setSize(235, 301);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

