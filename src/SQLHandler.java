import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class SQLHandler {
    private Connection connection;

    public SQLHandler() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:password_manager.db");
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS passwords (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "password TEXT NOT NULL)";
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
    }

    public void savePassword(String encryptedPassword) throws SQLException {
        String sql = "INSERT INTO passwords (password) VALUES (?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, encryptedPassword);
        pstmt.executeUpdate();
    }

    public List<String> loadPasswords() throws SQLException {
        List<String> passwords = new ArrayList<>();
        String sql = "SELECT password FROM passwords";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            passwords.add(rs.getString("password"));
        }
        return passwords;
    }

    public void deletePassword(int index) throws SQLException {
        String sql = "DELETE FROM passwords WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        String selectSql = "SELECT id FROM passwords LIMIT 1 OFFSET ?";
        PreparedStatement selectPstmt = connection.prepareStatement(selectSql);
        selectPstmt.setInt(1, index);
        ResultSet rs = selectPstmt.executeQuery();
        if (rs.next()) {
            int id = rs.getInt("id");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
