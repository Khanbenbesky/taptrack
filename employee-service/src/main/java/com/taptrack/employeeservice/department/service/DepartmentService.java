package com.taptrack.employeeservice.department.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.taptrack.employeeservice.department.dto.DepartmentRequestDto;
import com.taptrack.employeeservice.department.dto.DepartmentResponseDto;
import com.taptrack.employeeservice.department.entity.Department;
import com.taptrack.employeeservice.exception.ResourceNotFoundException;
import com.taptrack.employeeservice.department.mapper.DepartmentMapper;
import com.taptrack.employeeservice.department.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {

        logger.info("Creating department with code: {}", departmentRequestDto.getDepartmentCode());

        if(departmentRepository.existsByDepartmentCode(departmentRequestDto.getDepartmentCode())) {
            logger.error("Department already exists with code: {}", departmentRequestDto.getDepartmentCode());
            throw new DuplicateRequestException("Department code already exists: "+departmentRequestDto.getDepartmentCode());
        }
        Department department = new Department();
        department.setDepartmentCode(departmentRequestDto.getDepartmentCode());
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setLocation(departmentRequestDto.getLocation());
        department.setDepartmentHeadId(departmentRequestDto.getDepartmentHeadId());
        department.setDescription(departmentRequestDto.getDescription());
        Department savedDepartment = departmentRepository.save(department);
        logger.info("Department created successfully with id: {}", savedDepartment.getId());
        return departmentMapper.toDTO(savedDepartment);
    }

    public DepartmentResponseDto getDepartmentById(Long departmentId) {

        logger.info("Fetching department with id: {}", departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.error("Department not found with id: {}", departmentId);
                    return new ResourceNotFoundException("Department not found: "+departmentId);
                });
        return departmentMapper.toDTO(department);
    }

    public List<DepartmentResponseDto> getAllDepartments() {

        logger.info("Fetching all departments");
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDTO)
                .toList();
    }

    public DepartmentResponseDto updateDepartmentById(Long departmentId, DepartmentRequestDto departmentRequestDto) {

        logger.info("Updating department id: {}", departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.error("Department not found for update: {}", departmentId);
                    return new ResourceNotFoundException("Department not found");
                });
        department.setDepartmentCode(departmentRequestDto.getDepartmentCode());
        department.setDepartmentName(departmentRequestDto.getDepartmentName());
        department.setLocation(departmentRequestDto.getLocation());
        department.setDepartmentHeadId(departmentRequestDto.getDepartmentHeadId());
        department.setDescription(departmentRequestDto.getDescription());
        Department updatedDepartment = departmentRepository.save(department);
        logger.info("Department updated successfully id: {}", updatedDepartment.getId());
        return departmentMapper.toDTO(updatedDepartment);
    }

    public void deleteDepartmentById(Long departmentId) {

        logger.info("Deleting department id: {}", departmentId);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.error("Department not found for delete: {}", departmentId);
                    return new ResourceNotFoundException("Department not found");
                });
        departmentRepository.delete(department);
        logger.info("Department deleted successfully id: {}", departmentId);
    }
}
