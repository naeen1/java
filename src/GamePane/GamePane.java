package GamePane;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import DBManager.DBManager;
import Game.Game;
import Lobby.Lobby;

import java.awt.*;
import java.awt.event.*;
import java.util.Timer;

public class GamePane extends JPanel implements ActionListener, KeyListener {
    Timer timer = new Timer(10, this);
    int ballX=200, ballY=300, ballDX=2, ballDY=3;
    int paddleX=160;
    int score = 0;
    String id;
    JFrame frame;

    public GamePane(String id, JFrame frame) {
        this.id = id; this.frame = frame;
        setFocusable(true);
        addKeyListener(this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(paddleX, 550, 80, 10); // Paddle
        g.fillOval(ballX, ballY, 20, 20); // Ball
        // 벽돌, 점수 등 추가 구현 필요
        g.drawString("Score: " + score, 10, 20);
    }

    public void actionPerformed(ActionEvent e) {
        ballX += ballDX; ballY += ballDY;
        if (ballX < 0 || ballX > getWidth()-20) ballDX = -ballDX;
        if (ballY < 0) ballDY = -ballDY;
        if (ballY > getHeight()-20) {
            timer.stop();
            DBManager.saveScore(id, score);
            int sel = JOptionPane.showOptionDialog(this, "게임 오버! 점수: " + score, "결과",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"다시하기", "나가기"}, "다시하기");
            if (sel == 0) {
                frame.dispose();
                new Game(id);
            } else {
                frame.dispose();
                new Lobby();
            }
        }
        // Paddle 충돌 등 추가
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) paddleX -= 10;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < getWidth()-80) paddleX += 10;
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}
