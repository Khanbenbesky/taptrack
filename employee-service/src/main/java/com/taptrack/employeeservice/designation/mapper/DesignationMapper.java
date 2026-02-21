package com.taptrack.employeeservice.designation.mapper;

import com.taptrack.employeeservice.designation.dto.DesignationRequestDto;
import com.taptrack.employeeservice.designation.dto.DesignationResponseDto;
import com.taptrack.employeeservice.designation.entity.Designation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DesignationMapper {
    DesignationResponseDto toDTO(Designation designation);
    Designation toEntity(DesignationRequestDto designationResponseDto);
}
