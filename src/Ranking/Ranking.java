package Ranking;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Ranking extends JFrame {
    public Ranking() {
        setTitle("랭킹");
        setLayout(new BorderLayout());
        List<String[]> list = DBManager.getTop5();

        String[] cols = {"ID", "Score"};
        String[][] data = list.toArray(new String[0][0]);
        JTable table = new JTable(data, cols);

        add(new JScrollPane(table), BorderLayout.CENTER);
        setSize(250, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}

