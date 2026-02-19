package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.DesignationRequestDto;
import com.taptrack.employeeservice.employee.dto.DesignationResponseDto;
import com.taptrack.employeeservice.employee.entity.Designation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DesignationMapper {
    DesignationResponseDto toDTO(Designation designation);
    Designation toEntity(DesignationRequestDto designationResponseDto);
}
