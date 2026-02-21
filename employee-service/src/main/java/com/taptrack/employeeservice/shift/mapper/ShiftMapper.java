package com.taptrack.employeeservice.shift.mapper;

import com.taptrack.employeeservice.designation.dto.DesignationRequestDto;
import com.taptrack.employeeservice.designation.dto.DesignationResponseDto;
import com.taptrack.employeeservice.designation.entity.Designation;
import com.taptrack.employeeservice.shift.dto.ShiftRequestDto;
import com.taptrack.employeeservice.shift.dto.ShiftResponseDto;
import com.taptrack.employeeservice.shift.entity.Shift;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShiftMapper {

    ShiftResponseDto toDTO(Shift shift);
    Shift toEntity(ShiftRequestDto shiftRequestDto);
}
