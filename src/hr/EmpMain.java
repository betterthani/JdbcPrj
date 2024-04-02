package hr;

import java.util.List;

import hr.emp.EmpDao;
import hr.emp.EmpVo;
import hr.emp.IEmpDao;

public class EmpMain {

	public static void main(String[] args) {
		// UI(CUI) & 제어로직
		
		// insert 문
//		EmpVo emp = new EmpVo(300,"JinKyoung","Heo","HEOJK", "010",
//								null, "IT_PROG", 7000, 0, 103, 60);
		IEmpDao dao = new EmpDao();  
//		dao.insertEmp(emp);
//		System.out.println("고객 정보가 입력되었습니다.");
		
		// 일부 사원 select문
		System.out.println("고객의 정보를 조회합니다.");
		try {
			EmpVo emp = dao.selectEmp(300);
			System.out.println(emp);
			System.out.println("---절취---");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// 모든 사원의 정보를 조회합니다.
//		List<EmpVo> empList = dao.selectAllEmps();
//		for(EmpVo emp : empList) {
//			System.out.println(emp);
//		}
		
		// update문
//		EmpVo updateEmp = new EmpVo();
//		updateEmp.setEmployeeId(300);
//		updateEmp.setSalary(9000);
//		dao.updateEmpSalary(updateEmp);
		
		dao.deleteEmp(100, "SKING");
		
		// 일부 사원 select문
		System.out.println("삭제 후 해당 고객의 정보를 조회합니다.");
		try {
			EmpVo emp = dao.selectEmp(300);
			System.out.println(emp);
			System.out.println("---절취---");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}

}
