package com.taptrack.employeeservice.employee.mapper;


import com.taptrack.employeeservice.employee.dto.EmployeeDocumentRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeDocumentResponseDto;
import com.taptrack.employeeservice.employee.entity.EmployeeDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeDocumentMapper {
    EmployeeDocumentResponseDto toDTO(EmployeeDocument employeeDocument);
    EmployeeDocument toEntity(EmployeeDocumentRequestDto employeeDocumentRequestDto);
}
