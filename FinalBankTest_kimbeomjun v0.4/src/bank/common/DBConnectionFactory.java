package bank.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
    
    // JDBC 드라이버 클래스 이름
    private static final String JDBC_DRIVER_CLASS_NAME = "oracle.jdbc.OracleDriver";
    // 데이터베이스 URL
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    // 데이터베이스 사용자 ID
    private static final String USER_ID = "hr";
    // 데이터베이스 비밀번호
    private static final String USER_PWD = "hr";

    // 데이터베이스 연결 메소드
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // JDBC 드라이버 로드
            Class.forName(JDBC_DRIVER_CLASS_NAME);
            // 데이터베이스 연결
            conn = DriverManager.getConnection(URL, USER_ID, USER_PWD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver Class not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
        return conn;
    }
}
