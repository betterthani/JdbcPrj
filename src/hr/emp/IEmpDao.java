package hr.emp;

import java.util.List;

public interface IEmpDao {
	// 기능 - CRUD(관리)
	// 사원정보 입력, 수정, 삭제, 조회, 전체조회
	// 기능 구현을 위한 메서드 정의 - 구현은 클래스(EmpDao)에서
	void insertEmp(EmpVo emp);
	void updateEmpSalary(EmpVo emp);
//	void updateEmpSalary(int employeeId, double salary);
	void deleteEmp(int employeeId, String email);
	EmpVo selectEmp(int employeeId);
	List<EmpVo> selectAllEmps();
}
