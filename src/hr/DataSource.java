package hr;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataSource {
	// 드라이버 로딩
	static{
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} 
	} 
	
	// 커넥션을 생성해서 반환하는 메서드 
	private static String url = "jdbc:oracle:thin:@localhost:1521/xe";
	private static String user = "hr";
	private static String password = "hr";
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url,user,password);
			return con;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// 커넥션 반납하는 close() 메서드
	public static void closeConnection(Connection con) {
		try {
			con.close();
		}catch (Exception e) {
			// nothing
		}
	}
	
	
}
