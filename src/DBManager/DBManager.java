package DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/java_game";
    private static final String USER = "root";
    private static final String PASS = "1234";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); } catch (Exception e) {}
    }

    public static boolean login(String id, String pw) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM ranking WHERE id=? AND password=?");
            ps.setString(1, id); 
            ps.setString(2, pw);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) { return false; }
    }

    public static void saveScore(String id, int score) {
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement ps = c.prepareStatement("UPDATE ranking SET score=? WHERE id=?");
            ps.setInt(1, score); 
            ps.setString(2, id);
            if (ps.executeUpdate() == 0) {
                ps = c.prepareStatement("INSERT INTO ranking(id, password, score) VALUES (?, '', ?)");
                ps.setString(1, id); 
                ps.setInt(2, score);
                ps.executeUpdate();
            }
        } catch (Exception e) {}
    }

    public static List<String[]> getTop5() {
        List<String[]> list = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(URL, USER, PASS)) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, score FROM ranking ORDER BY score DESC LIMIT 5");
            while (rs.next()) list.add(new String[]{rs.getString(1), String.valueOf(rs.getInt(2))});
        } catch (Exception e) {}
        return list;
    }
}