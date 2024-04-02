import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class MetaDataExample {

	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String user = "hr";
		String password = "hr";
		Connection con = DriverManager.getConnection(url, user, password);
		
		String sql = "SELECT * FROM employees";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData metaData = rs.getMetaData(); // 메타데이터 정보만 따로 얻을 수 있다.
		
		int colCount = metaData.getColumnCount();
		System.out.println(colCount);
		for(int i = 1; i <= colCount; i++) {
			System.out.println(metaData.getColumnName(i) + " " + metaData.getColumnType(i));
		}
		
	}

}
