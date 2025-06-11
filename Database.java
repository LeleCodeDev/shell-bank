import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
  static final String DB_URL = "jdbc:mysql://localhost:3306/bank_app";
  static final String DB_USER = "root";
  static final String DB_PASS = "nathan0308";

  private Connection conn;

  public Database() {
    try {
      conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
      System.out.println("Connected to Database!");
    } catch (SQLException e) {
      System.out.println("Connection failed : " + e);
    }
  }

  public void signUp(String name, String pass) {
    try {
      String sql = "INSERT INTO accounts (name, password, balance) VALUES (?, ?, 0)";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

      stmt.setString(1, name);
      stmt.setString(2, pass);

      int affectedRow = stmt.executeUpdate();
      if (affectedRow > 0) {
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next()) {
          int id = rs.getInt(1);
          System.out.println("Account created!");
          System.out.println("Your account id is : " + id + "\n");
        }
      } else {
        System.out.println("Account creation failed!");
      }

    } catch (SQLException e) {
      System.out.println("Failed to create user : " + e);
    }
  }

  public int login(int id, String pass) {
    try {

      String sql = "SELECT password FROM accounts WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        String dbPass = rs.getString("password");

        if (dbPass.equals(pass)) {
          System.out.println("Login Sucessful!\n");
          return 0;
        } else {
          System.out.println("password didn't match\n");
          return 1;
        }
      } else {
        System.out.println("Account not found!\n");
        return 1;
      }
    } catch (SQLException e) {
      System.out.println("Login Failed : " + e);
      return 1;
    }

  };

  public double getBalance(int accountId) {
    double balance = 0;

    try {
      String sql = "SELECT balance FROM accounts WHERE ID = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);

      stmt.setInt(1, accountId);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        balance = rs.getDouble("balance");
      }
    } catch (SQLException e) {
      System.out.println("Failed to get balance : " + e);
    }

    return balance;
  }

  public void updateBalance(int accountId, double newBalance) {
    try {

      String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
      PreparedStatement stmt = conn.prepareStatement(sql);

      stmt.setDouble(1, newBalance);
      stmt.setInt(2, accountId);

      int rows = stmt.executeUpdate();
      if (rows > 0) {
        System.out.println("Balance updated!");
      } else {
        System.out.println("No account found with id: " + accountId);
      }
    } catch (SQLException e) {
      System.out.println("Failed to update balance : " + e);
    }
  }

  public void close() {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      System.out.println("Failed to close database : " + e);
    }
  }
}
