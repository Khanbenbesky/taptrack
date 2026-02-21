package com.taptrack.employeeservice.department.mapper;

import com.taptrack.employeeservice.department.dto.DepartmentRequestDto;
import com.taptrack.employeeservice.department.dto.DepartmentResponseDto;
import com.taptrack.employeeservice.department.entity.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentResponseDto toDTO(Department department);
    Department toEntity(DepartmentRequestDto departmentRequestDto);
}
