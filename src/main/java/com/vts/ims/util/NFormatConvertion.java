package com.vts.ims.util;

import java.util.List;
import java.util.Optional;

import com.vts.ims.master.dto.EmployeeDto;

public class NFormatConvertion {

		public static EmployeeDto getEmployeeDetails(Long empId,List<EmployeeDto> totalEmployee) {
			Optional<EmployeeDto> employeeOpt =	totalEmployee.stream().filter(emp -> emp.getEmpId().equals(empId)).findFirst();
			if(employeeOpt.isPresent()) {
				return employeeOpt.get();
			}else {
				return null;
			}
		}
	}
