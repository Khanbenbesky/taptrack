package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.EmployeeRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeResponseDto;
import com.taptrack.employeeservice.employee.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponseDto toDTO(Employee employee);
    Employee toEntity(EmployeeRequestDto employeeRequestDto);
}
