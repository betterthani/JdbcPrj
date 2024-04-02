package hr.emp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.DataSource;

public class EmpDao implements IEmpDao{

	@Override
	public void insertEmp(EmpVo emp) {
		Connection con = null;
		try {
			con = DataSource.getConnection();
			String sql = "insert into employees(employee_id, first_name, last_name, email,"
					+ "phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id) "
					+ "values(?,?,?,?,?,sysdate,?,?,?,?,?)";
			
			PreparedStatement stmt = con.prepareStatement(sql); // 예외 발생할 수 있음. -> runtimeException잡기
			stmt.setInt(1, emp.getEmployeeId());
			stmt.setString(2, emp.getFirstName());
			stmt.setString(3, emp.getLastName());
			stmt.setString(4, emp.getEmail());
			stmt.setString(5, emp.getPhoneNumber());
			stmt.setString(6, emp.getJobId());
			stmt.setDouble(7, emp.getSalary());
			stmt.setDouble(8, emp.getCommissionPct());
			stmt.setInt(9, emp.getManagerId());
			stmt.setInt(10, emp.getDepartmentId());
			stmt.executeUpdate(); // 변경된 행의 개수 return값 가짐
			
		} catch (Exception e) {
			throw new RuntimeException(e); 
		} finally {
			DataSource.closeConnection(con);
		}
	}

	@Override
	public void updateEmpSalary(EmpVo emp) {
		Connection con = null;
		try {
			con = DataSource.getConnection();
			String sql = "update employees set salary = ? where employee_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql); // 예외 발생할 수 있음. -> runtimeException잡기
			stmt.setDouble(1, emp.getSalary());
			stmt.setInt(2, emp.getEmployeeId());
			stmt.executeUpdate();
			
		} catch (Exception e) {
			throw new RuntimeException(e); 
			
		} finally {
			DataSource.closeConnection(con);
		}
	}

	@Override
	public void deleteEmp(int employeeId, String email) {
		Connection con = null;
		try {
			con = DataSource.getConnection();
			con.setAutoCommit(false);
			// job_history테이블에 데이터 삭제 -- 업무 변경이 안 이뤄진적도 있을 수 있다 즉, employee_id의 값들이 없을 수 있기 때문에 정상 삭제 여부 판단 안 해도 된다.
			// employees테이블에 데이터 삭제
			String sql1 = "delete from job_history where employee_id =?";
			PreparedStatement stmt1 = con.prepareStatement(sql1); // 예외 발생할 수 있음. -> runtimeException잡기
			stmt1.setInt(1, employeeId);
			stmt1.executeUpdate();
			
			String sql2 = "delete from employees where  employee_id =? and email = ?";
			PreparedStatement stmt2 = con.prepareStatement(sql2); // 예외 발생할 수 있음. -> runtimeException잡기
			stmt2.setInt(1, employeeId);
			stmt2.setString(2, email);
			int row = stmt2.executeUpdate();
			if(row == 0) {
				// 정상 삭제 되지 않았을때
				throw new RuntimeException("사원 정보가 삭제되지 않았습니다."); // catch문으로 들어가 rollback처리됨.
			}
			con.commit();
		} catch (Exception e) {
			try { con.rollback(); } catch(Exception e2) { }
			throw new RuntimeException(e); 
		} finally {
			try { con.setAutoCommit(true);} catch(Exception e) {}
			DataSource.closeConnection(con);
		}
	}

	@Override
	public EmpVo selectEmp(int employeeId) {
		Connection con = null;
		try {
			con = DataSource.getConnection();
			String sql = "select employee_id, first_name, last_name, email, phone_number"
					+ ",hire_date, job_id, salary, commission_pct, manager_id, department_id "
					+ "from employees where employee_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql); // 예외 발생할 수 있음. -> runtimeException잡기
			stmt.setInt(1, employeeId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				
				// 조회한 데이터가 있을 경우
				EmpVo emp = new EmpVo();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				return emp;
			} else {
				// 조회한 데이터가 없을 경우
				throw new RuntimeException("조회한 사원의 정보가 없습니다.");// null return 할경우 nullpointexception ui에 뿌려지므로 직접 예외처리 해줌
			}
		} catch (Exception e) {
			throw new RuntimeException(e); 
			
		} finally {
			DataSource.closeConnection(con);
		}
	}

	@Override
	public List<EmpVo> selectAllEmps() {
		Connection con = null;
		List<EmpVo> empList = new ArrayList<>();
		
		try {
			con = DataSource.getConnection();
			String sql = "select employee_id, first_name, last_name, email, phone_number"
					+ ",hire_date, job_id, salary, commission_pct, manager_id, department_id "
					+ "from employees";
			
			PreparedStatement stmt = con.prepareStatement(sql); // 예외 발생할 수 있음. -> runtimeException잡기
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				// rs에서 정보를 조회해서 emp객체에 저장하고 emp객체를 empList에 add()해야 한다.
				EmpVo emp = new EmpVo();
				emp.setEmployeeId(rs.getInt("employee_id"));
				emp.setFirstName(rs.getString("first_name"));
				emp.setLastName(rs.getString("last_name"));
				emp.setEmail(rs.getString("email"));
				emp.setPhoneNumber(rs.getString("phone_number"));
				emp.setHireDate(rs.getDate("hire_date"));
				emp.setJobId(rs.getString("job_id"));
				emp.setSalary(rs.getDouble("salary"));
				emp.setCommissionPct(rs.getDouble("commission_pct"));
				emp.setManagerId(rs.getInt("manager_id"));
				emp.setDepartmentId(rs.getInt("department_id"));
				
				empList.add(emp);
			}
		} catch (Exception e) {
			throw new RuntimeException(e); 
		} finally {
			DataSource.closeConnection(con);
		}
		
		return empList;
	}
	
}
