package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.DepartmentRequestDto;
import com.taptrack.employeeservice.employee.dto.DepartmentResponseDto;
import com.taptrack.employeeservice.employee.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponseDto toDTO(Department department);
    Department toEntity(DepartmentRequestDto departmentRequestDto);
}
