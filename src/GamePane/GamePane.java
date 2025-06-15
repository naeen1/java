package GamePane;

import javax.swing.*;
import java.util.Random;

import DBManager.DBManager;
import Game.Game;
import Lobby.Lobby;

import java.awt.*;
import java.awt.event.*;

public class GamePane extends JPanel implements ActionListener, KeyListener {
    // 상수 선언
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 12;
    private static final int PADDLE_Y_OFFSET = 60; // 아래에서 60픽셀 위
    private static final int BALL_SIZE = 20;
    private static final int BALL_START_X = 240;
    private static final int BALL_START_DX = 4;
    private static final int BALL_START_DY = 3;
    private static final int PADDLE_SPEED = 18;

    Timer timer = new Timer(10, this);
    int ballX = BALL_START_X;
    int ballY; // 생성자에서 초기화
    int ballDX = BALL_START_DX, ballDY = BALL_START_DY;
    int paddleX = 220;
    int score = 0;
    String id;
    JFrame frame;

    int brickRows = 7, brickCols = 10;
    int brickWidth = 45, brickHeight = 20;
    boolean[][] bricks = new boolean[brickRows][brickCols];
    Color[][] brickColors = new Color[brickRows][brickCols];

    Color[] rowColors = {
        Color.ORANGE, Color.CYAN, Color.PINK, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.BLUE
    };

    Random rand = new Random();

    public GamePane(String id, JFrame frame) {
        this.id = id;
        this.frame = frame;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        // 벽돌 배열 및 색상 초기화
        for (int i = 0; i < brickRows; i++)
            for (int j = 0; j < brickCols; j++) {
                bricks[i][j] = true;
                brickColors[i][j] = rowColors[i % rowColors.length];
            }
        // 공을 벽돌 아래 90픽셀에서 시작 (패들과 벽돌 사이)
        ballY = brickRows * brickHeight + 40 + 90;
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int paddleY = getHeight() - PADDLE_Y_OFFSET;

        // 패들 그리기
        g.setColor(Color.BLACK);
        g.fillRect(paddleX, paddleY, PADDLE_WIDTH, PADDLE_HEIGHT);

        // 공 그리기
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        // 벽돌 그리기
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                if (bricks[i][j]) {
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j * brickWidth + 10, i * brickHeight + 40, brickWidth - 2, brickHeight - 2);
                }
            }
        }

        // 점수 표시
        g.setColor(Color.BLUE);
        g.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballDX;
        ballY += ballDY;
        if (ballX < 0 || ballX > getWidth() - BALL_SIZE) ballDX = -ballDX;
        if (ballY < 0) ballDY = -ballDY;

        int paddleY = getHeight() - PADDLE_Y_OFFSET;

        // 패들 충돌
        if (ballY + BALL_SIZE >= paddleY && ballY + BALL_SIZE <= paddleY + PADDLE_HEIGHT) {
            if (ballX + BALL_SIZE >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
                ballDY = -ballDY;
                // 반사각 랜덤(더 자연스럽게 하고 싶으면 아래 주석 해제)
                // ballDX += rand.nextInt(3) - 1;
                ballY = paddleY - BALL_SIZE;
            }
        }

        // 벽돌 충돌
        boolean brickBroken = false;
        A:
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickCols; j++) {
                if (bricks[i][j]) {
                    int bx = j * brickWidth + 10;
                    int by = i * brickHeight + 40;
                    Rectangle brickRect = new Rectangle(bx, by, brickWidth, brickHeight);
                    Rectangle ballRect = new Rectangle(ballX, ballY, BALL_SIZE, BALL_SIZE);
                    if (brickRect.intersects(ballRect)) {
                        bricks[i][j] = false;
                        ballDY = -ballDY;
                        score += 10;
                        brickBroken = true;
                        break A;
                    }
                }
            }
        }

        // 벽돌이 깨졌을 때 랜덤으로 빈 칸에 벽돌 재생성 (15% 확률, 색상 무작위)
        if (brickBroken && rand.nextInt(100) < 15) {
            for (int t = 0; t < 10; t++) {
                int ri = rand.nextInt(brickRows);
                int rj = rand.nextInt(brickCols);
                if (!bricks[ri][rj]) {
                    bricks[ri][rj] = true;
                    brickColors[ri][rj] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                    break;
                }
            }
        }

        // 게임 오버 처리
        if (ballY > getHeight() - BALL_SIZE) {
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
        repaint();
    }

    // 패들 이동을 더 부드럽게 (빠르게)
    @Override
    public void keyPressed(KeyEvent e) {
        int paddleY = getHeight() - PADDLE_Y_OFFSET;
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) paddleX -= PADDLE_SPEED;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < getWidth() - PADDLE_WIDTH) paddleX += PADDLE_SPEED;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
