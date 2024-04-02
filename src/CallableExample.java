import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

public class CallableExample {
	
	// 1-1. 드라이브 로딩 - 드라이브 클래스파일 (build path잡아야함)
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 클래스가 로드되었습니다.");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 클래스 파일을 찾을 수 없어요." + e.getMessage());
		}
	}

	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521/xe";
		String user = "hr";
		String password = "hr";
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, user, password);
			
			// 프로시저 변수 2개 생성했으므로 / 입력파라미터, 출력 파라미터 사용될 곳에 들어갈 타입을 지정
			String sql = "{call GETSALARY(?,?)}";
			CallableStatement cstmt = con.prepareCall(sql);
			
			// 입력파라미터
			cstmt.setInt(1, 103);
			// 출력파라미터
			cstmt.registerOutParameter(2, java.sql.Types.DOUBLE); // 급여는 소수점 이하 반환하므로 double형
			
			cstmt.execute(); // 이미 쿼리문을 갖고있으므로 executeQuery안 해도됨.
			System.out.println(cstmt.getDouble(2)); // 출력 파라미터 2번째꺼로 등록되어있기 때문
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try { con.close(); } catch (Exception e2) {	}
		}
		
	}

}
