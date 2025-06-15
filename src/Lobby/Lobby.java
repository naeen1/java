package Lobby;

import java.awt.*;
import java.awt.EventQueue;
import java.net.URL;
import javax.swing.*;

import DBManager.DBManager;
import Game.Game;
import Ranking.Ranking;
import JPanel.BackgroundPanel; // ← 패키지명에 맞게 import

public class Lobby extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField idField;
    private JPasswordField pwField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Lobby frame = new Lobby();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Lobby() {
        setTitle("벽돌깨기 로비");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 544, 341);

        // ★ 배경 이미지 패널로 contentPane 생성
        ImageIcon bgIcon;
        try {
            // 프로젝트 루트에서 실행할 때, imgs/brick.png로 접근
            bgIcon = new ImageIcon("imgs/brick.png");
            System.out.println("이미지 파일 로드 성공: imgs/brick.png");
        } catch (Exception e) {
            System.out.println("이미지를 불러올 수 없습니다.");
            e.printStackTrace();
            bgIcon = new ImageIcon(); // 빈 이미지라도 생성
        }


        contentPane = new BackgroundPanel(bgIcon);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel idLabel = new JLabel("아이디:");
        idLabel.setBackground(new Color(255, 255, 255));
        idLabel.setFont(new Font("궁서", Font.BOLD, 13));
        idLabel.setForeground(Color.YELLOW);
        idLabel.setBounds(126, 75, 60, 25);
        contentPane.add(idLabel);

        idField = new JTextField();
        idField.setBounds(196, 75, 180, 25);
        contentPane.add(idField);

        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setForeground(Color.YELLOW);
        pwLabel.setFont(new Font("HY궁서B", Font.BOLD, 13));
        pwLabel.setBounds(126, 115, 60, 25);
        contentPane.add(pwLabel);

        pwField = new JPasswordField();
        pwField.setBounds(196, 115, 180, 25);
        contentPane.add(pwField);

        JButton loginBtn = new JButton("로그인 후 시작");
        loginBtn.setBounds(126, 165, 120, 35);
        contentPane.add(loginBtn);
        loginBtn.addActionListener(e -> performLogin());

        idField.addActionListener(e -> performLogin());
        pwField.addActionListener(e -> performLogin());

        JButton rankBtn = new JButton("랭킹보기");
        rankBtn.setBounds(256, 165, 120, 35);
        contentPane.add(rankBtn);
        rankBtn.addActionListener(e -> new Ranking());

        idField.setName("idField");
        pwField.setName("pwField");
        loginBtn.setName("loginBtn");
        rankBtn.setName("rankBtn");
        idLabel.setName("idLabel");
        pwLabel.setName("pwLabel");

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void performLogin() {
        String id = idField.getText();
        String pw = new String(pwField.getPassword());
        if (DBManager.login(id, pw)) {
            new Game(id);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "로그인 실패");
        }
    }
}
