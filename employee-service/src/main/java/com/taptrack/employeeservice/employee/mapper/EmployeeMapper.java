package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.EmployeeResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeSummaryDto;
import com.taptrack.employeeservice.employee.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class  EmployeeMapper {

    public EmployeeResponseDto toDTO(Employee e) {
        if (e == null) return null;

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(e.getId());
        dto.setEmployeeId(e.getEmployeeId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setFullName(e.getFullName());
        dto.setEmail(e.getEmail());
        dto.setPhone(e.getPhone());
        dto.setDateOfJoining(e.getDateOfJoining());
        dto.setDateOfBirth(e.getDateOfBirth());
        dto.setGender(e.getGender());
        dto.setBloodGroup(e.getBloodGroup());
        dto.setProfilePhotoUrl(e.getProfilePhotoUrl());
        dto.setActive(e.getIsActive());
        dto.setCreatedAt(e.getCreatedAt());
        dto.setUpdatedAt(e.getUpdatedAt());

        // Department (LAZY — already loaded by service)
        if (e.getDepartment() != null) {
            dto.setDepartmentId(e.getDepartment().getId());
            dto.setDepartmentName(e.getDepartment().getDepartmentName());
        }

        // Designation
        if (e.getDesignation() != null) {
            dto.setDesignationId(e.getDesignation().getId());
            dto.setDesignationName(e.getDesignation().getDesignationName());
            dto.setDesignationGrade(e.getDesignation().getGrade());
        }

        // Shift
        if (e.getShift() != null) {
            dto.setShiftId(e.getShift().getId());
            dto.setShiftName(e.getShift().getShiftName());
            dto.setShiftStart(e.getShift().getShiftStart());
            dto.setShiftEnd(e.getShift().getShiftEnd());
        }

        // Manager summary — avoid deep nesting
        if (e.getManager() != null) {
            dto.setManagerId(e.getManager().getId());
            dto.setManagerName(e.getManager().getFullName());
            dto.setManagerEmployeeId(e.getManager().getEmployeeId());
        }

        return dto;
    }

    public EmployeeSummaryDto toSummaryDTO(Employee e) {
        if (e == null) return null;

        EmployeeSummaryDto dto = new EmployeeSummaryDto();
        dto.setId(e.getId());
        dto.setEmployeeId(e.getEmployeeId());
        dto.setFullName(e.getFullName());
        dto.setEmail(e.getEmail());
        dto.setActive(e.getIsActive());
        if (e.getDepartment() != null) {
            dto.setDepartmentName(e.getDepartment().getDepartmentName());
        }
        if (e.getDesignation() != null) {
            dto.setDesignationName(e.getDesignation().getDesignationName());
        }
        return dto;
    }

}
