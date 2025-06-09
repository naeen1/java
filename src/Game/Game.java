package Game;
import javax.swing.*;

public class Game extends JFrame {
    public Game(String id) {
        setTitle("벽돌깨기 게임");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(new Game(id));
        setSize(400, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
