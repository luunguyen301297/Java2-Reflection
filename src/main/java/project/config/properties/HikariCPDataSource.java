package project.config.properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPDataSource {
  private static final HikariConfig config = new HikariConfig();
  private static final HikariDataSource ds;

  static {
    config.setJdbcUrl("jdbc:mysql://localhost:3306/t2302");
    config.setUsername("root");
    config.setPassword("Lyh30121501");
    ds = new HikariDataSource(config);
  }

  public static Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

  public static void main(String[] args) throws SQLException {
    Connection conn = getConnection();
    if (conn != null) System.err.println("Hacking FBI by CSS...");
  }

  private HikariCPDataSource() {
  }
}
