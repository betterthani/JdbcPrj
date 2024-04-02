import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcExample {
	// static 로드될 때 딱 한 번 실행
	// 1-1. 드라이브 로딩 - 드라이브 클래스파일 (build path잡아야함)
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 클래스가 로드되었습니다.");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 파일을 찾을 수 없어요." + e.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("데이터베이스 연결을 테스트 합니다.");
		
		// 1. 드라이브 로딩 - 드라이브 클래스파일 (build path잡아야함) -> static 아래에 바로 넣을 수 있음
//		Class.forName("oracle.jdbc.OracleDriver");
//		System.out.println("드라이버 클래스가 로드되었습니다.");
		
		// 2. Connection생성
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String user = "hr";
		String password = "hr";
		 Connection con = null;
		 try {
			con = DriverManager.getConnection(url, user, password);
			System.out.println(con);
			
			// 2-1) Statement방식 - SQL Injection 취약점 발생
//			Statement stmt = con.createStatement();
//			int deptno = 60; 
////			String sql = "select employee_id, first_name, salary from employees";
//			String sql = "select first_name, hire_date, salary, department_id"
//						+ " from employees where department_id = 60";
//			ResultSet rs = stmt.executeQuery(sql);
			
			// 2-2) PreparedStatement 방식
			String sql = "select first_name, hire_date, salary, department_id"
					+ " from employees where department_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, 60); // 첫번째 물음표에 60 지정
			ResultSet rs = stmt.executeQuery();
			
			System.out.println(rs);
			
			while (rs.next()) {
//				System.out.print(rs.getInt("employee_id") + "\t"); // 열 이름 넣는게 좋다.
				System.out.print(rs.getString("first_name") + "\t");
				System.out.println(rs.getDouble("salary"));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try { con.close(); } catch (Exception e2) {	}
		}
		 
		 
		 
		
	}

}
