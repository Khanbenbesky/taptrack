package com.taptrack.employeeservice.employee.service;

import com.sun.jdi.request.DuplicateRequestException;
import com.taptrack.employeeservice.employee.dto.DepartmentRequestDto;
import com.taptrack.employeeservice.employee.dto.DepartmentResponseDto;
import com.taptrack.employeeservice.employee.dto.DesignationRequestDto;
import com.taptrack.employeeservice.employee.dto.DesignationResponseDto;
import com.taptrack.employeeservice.employee.entity.Department;
import com.taptrack.employeeservice.employee.entity.Designation;
import com.taptrack.employeeservice.employee.exception.ResourceNotFoundException;
import com.taptrack.employeeservice.employee.mapper.DepartmentMapper;
import com.taptrack.employeeservice.employee.mapper.DesignationMapper;
import com.taptrack.employeeservice.employee.repository.DepartmentRepository;
import com.taptrack.employeeservice.employee.repository.DesignationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignationService {

    private final Logger logger = LoggerFactory.getLogger(DesignationService.class);

    private final DesignationRepository designationRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationMapper designationMapper;

    public DesignationService(
            DesignationRepository designationRepository,
            DepartmentRepository departmentRepository,
            DesignationMapper designationMapper) {

        this.designationRepository = designationRepository;
        this.departmentRepository = departmentRepository;
        this.designationMapper = designationMapper;
    }

    public DesignationResponseDto createDesignation(DesignationRequestDto designationRequestDto) {

        logger.info("Creating designation with code: {}", designationRequestDto.getDesignationCode());
        if (designationRepository.existsByDesignationCodeAndIsDeleteFalse(designationRequestDto.getDesignationCode())) {
            logger.error("Department already exists with code: {}", designationRequestDto.getDesignationCode());
            throw new RuntimeException("Designation code already exists");
        }
        Department department = departmentRepository.findById(designationRequestDto.getDepartmentId())
                .orElseThrow(() -> {
                    logger.error("Department not found with id: {}", designationRequestDto.getDepartmentId());
                    return new ResourceNotFoundException("Department not found with: "+designationRequestDto.getDepartmentId());
                });

        Designation designation = designationMapper.toEntity(designationRequestDto);
        designation.setDepartment(department);
        Designation savedDesignation = designationRepository.save(designation);
        logger.info("Department created successfully with id: {}", savedDesignation.getId());
        return designationMapper.toDTO(savedDesignation);
    }

    public DesignationResponseDto getDesignationById(Long designationId) {

        logger.info("Fetching designation with id: {}", designationId);
        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> {
            logger.error("Designation not found with id: {}", designationId);
            return new ResourceNotFoundException("Designation not found with: "+designationId);
        });
        return designationMapper.toDTO(designation);
    }

    public List<DesignationResponseDto> getAllDesignations() {

        logger.info("Fetching all departments");
        return designationRepository.findAll()
                .stream()
                .map(designationMapper::toDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    public DesignationResponseDto updateDesignationById(Long designationId, DesignationRequestDto designationRequestDto) {

        logger.info("Updating designation id: {}", designationId);
        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> {
                    logger.error("Designation not found for update: {}", designationId);
                    return new ResourceNotFoundException("designation not found with: "+designationId);
                });

        designation.setDesignationName(designationRequestDto.getDesignationName());
        designation.setGrade(designationRequestDto.getGrade());
        designation.setDescription(designationRequestDto.getDescription());

        logger.info("Updating designation with department id: {}", designationRequestDto.getDepartmentId());
        Department department = departmentRepository.findById(designationId)
                .orElseThrow(() -> {
                    logger.error("Department not found for updating designation: {}", designationRequestDto.getDepartmentId());
                    return new ResourceNotFoundException("Department not found with: "+designationRequestDto.getDepartmentId());
                });

        designation.setDepartment(department);
        Designation updatedDesignation = designationRepository.save(designation);
        logger.info("Designation updated successfully id: {}", updatedDesignation.getId());
        return designationMapper.toDTO(
                designationRepository.save(designation)
        );
    }

    public void deleteDesignationById(Long designationId) {

        logger.info("Deleting designation with id: {}", designationId);
        Designation designation = designationRepository.findById(designationId)
                .orElseThrow(() -> {
                    logger.error("Designation not found for delete: {}", designationId);
                    return new ResourceNotFoundException("Designation not found with id: "+designationId);
                });
        designation.setDelete(true);
        designationRepository.save(designation);
    }
}
