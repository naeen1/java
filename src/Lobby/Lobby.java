package Lobby;

import java.awt.EventQueue;
import javax.swing.*;

import DBManager.DBManager;
import Game.Game;

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
        setBounds(100, 100, 450, 250); // 창 크기
        contentPane = new JPanel();
        contentPane.setLayout(null); // 절대 위치 배치
        setContentPane(contentPane);

        // 아이디 라벨
        JLabel idLabel = new JLabel("아이디:");
        idLabel.setBounds(40, 30, 60, 25); // x, y, width, height
        contentPane.add(idLabel);

        // 아이디 입력 필드
        idField = new JTextField();
        idField.setBounds(110, 30, 180, 25);
        contentPane.add(idField);

        // 비밀번호 라벨
        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setBounds(40, 70, 60, 25);
        contentPane.add(pwLabel);

        // 비밀번호 입력 필드
        pwField = new JPasswordField();
        pwField.setBounds(110, 70, 180, 25);
        contentPane.add(pwField);

        // 로그인 버튼
        JButton loginBtn = new JButton("로그인 후 시작");
        loginBtn.setBounds(40, 120, 120, 35);
        contentPane.add(loginBtn);
        
        loginBtn.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());
            if (DBManager.login(id, pw)) {
                // 게임 화면 띄우기
                new Game(id); // GameFrame이 JFrame을 상속받는 경우
                dispose(); // 현재 로비 창 닫기
            } else {
                JOptionPane.showMessageDialog(this, "로그인 실패");
            }
        });

        // 랭킹보기 버튼
        JButton rankBtn = new JButton("랭킹보기");
        rankBtn.setBounds(170, 120, 120, 35);
        contentPane.add(rankBtn);

        // 필요시: 컴포넌트 이름 지정 (테스트 자동화 등)
        idField.setName("idField");
        pwField.setName("pwField");
        loginBtn.setName("loginBtn");
        rankBtn.setName("rankBtn");
        idLabel.setName("idLabel");
        pwLabel.setName("pwLabel");

        setLocationRelativeTo(null); // 화면 중앙 배치
        setVisible(true);
    }
}

