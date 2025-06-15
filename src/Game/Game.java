package Game;
import javax.swing.*;

import GamePane.GamePane;

public class Game extends JFrame {
    public Game(String id) {
        setTitle("벽돌깨기 게임");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new GamePane(id, this));  // 두 번째 인자로 자기 자신 전달
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
