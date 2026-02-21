package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.EmployeeContactRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactResponseDto;
import com.taptrack.employeeservice.employee.entity.EmployeeContact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeContactMapper {
    EmployeeContactResponseDto toDTO(EmployeeContact employeeContact);
    EmployeeContact toEntity(EmployeeContactRequestDto employeeContactRequestDto);
}
