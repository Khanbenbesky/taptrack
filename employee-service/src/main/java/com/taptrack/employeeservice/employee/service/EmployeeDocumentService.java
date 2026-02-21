package com.taptrack.employeeservice.employee.service;

import com.taptrack.employeeservice.employee.dto.EmployeeDocumentRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeDocumentResponseDto;
import com.taptrack.employeeservice.employee.entity.Employee;
import com.taptrack.employeeservice.employee.entity.EmployeeDocument;
import com.taptrack.employeeservice.employee.mapper.EmployeeDocumentMapper;
import com.taptrack.employeeservice.employee.repository.EmployeeDocumentRepository;
import com.taptrack.employeeservice.employee.repository.EmployeeRepository;
import com.taptrack.employeeservice.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeDocumentService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDocumentService.class);

    private final EmployeeDocumentRepository employeeDocumentRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeDocumentMapper employeeDocumentMapper;

    public EmployeeDocumentService(EmployeeDocumentRepository employeeDocumentRepository, EmployeeRepository employeeRepository, EmployeeDocumentMapper employeeDocumentMapper) {
        this.employeeDocumentRepository = employeeDocumentRepository;
        this.employeeRepository = employeeRepository;
        this.employeeDocumentMapper = employeeDocumentMapper;
    }


    public EmployeeDocumentResponseDto createEmployeeDocument(
            Long employeeId, EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("Uploading document for employeeId: {}", employeeId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", employeeId);
                    return new ResourceNotFoundException(
                            "Employee not found with id: " + employeeId);
                });
        EmployeeDocument document = employeeDocumentMapper.toEntity(employeeDocumentRequestDto);
        document.setEmployee(employee);
        employeeDocumentRepository.save(document);
        logger.info("Document uploaded successfully for employeeId: {}", employeeId);
        return employeeDocumentMapper.toDTO(document);
    }

    @Transactional(readOnly = true)
    public EmployeeDocumentResponseDto getEmployeeDocumentById(Long documentId) {

        logger.info("Fetching document with id: {}", documentId);
        EmployeeDocument document = employeeDocumentRepository.findById(documentId)
                .orElseThrow(() -> {
                    logger.error("Document not found with id: {}", documentId);
                    return new ResourceNotFoundException(
                            "Document not found with id: " + documentId);
                });
        return employeeDocumentMapper.toDTO(document);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDocumentResponseDto> getEmployeeDocumentsByEmployeeId(Long employeeId) {

        logger.info("Fetching documents for employeeId: {}", employeeId);
        return employeeDocumentRepository.findByEmployeeId(employeeId)
                .stream()
                .map(employeeDocumentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDocumentResponseDto updateEmployeeDocument(
            Long documentId, EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("Updating document with id: {}", documentId);
        EmployeeDocument document = employeeDocumentRepository.findById(documentId)
                .orElseThrow(() -> {
                    logger.error("Document not found for updating with id: {}", documentId);
                    return new ResourceNotFoundException(
                            "Document not found with id: " + documentId);
                });
        document.setDocumentType(employeeDocumentRequestDto.getDocumentType());
        document.setDocumentNumber(employeeDocumentRequestDto.getDocumentNumber());
        document.setFilePath(employeeDocumentRequestDto.getFilePath());
        document.setFileName(employeeDocumentRequestDto.getFileName());
        document.setFileSizeKb(employeeDocumentRequestDto.getFileSizeKb());
        document.setMimeType(employeeDocumentRequestDto.getMimeType());
        document.setExpiryDate(employeeDocumentRequestDto.getExpiryDate());
        document.setRemarks(employeeDocumentRequestDto.getRemarks());
        employeeDocumentRepository.save(document);
        logger.info("Document updated successfully with id: {}", documentId);
        return employeeDocumentMapper.toDTO(document);
    }

    public EmployeeDocumentResponseDto verifyEmployeeDocument(
            Long documentId, String verifiedByEmployeeId) {

        logger.info("Verifying document with id: {}", documentId);
        EmployeeDocument document = employeeDocumentRepository.findById(documentId)
                .orElseThrow(() -> {
                    logger.error("Document not found for verifying with id: {}", documentId);
                    return new ResourceNotFoundException(
                            "Document not found with id: " + documentId);
                });
        document.verify(verifiedByEmployeeId);
        employeeDocumentRepository.save(document);
        logger.info("Document verified successfully with id: {}", documentId);
        return employeeDocumentMapper.toDTO(document);
    }

    public void deleteEmployeeDocument(Long documentId) {

        logger.info("Deleting document with id: {}", documentId);
        EmployeeDocument document = employeeDocumentRepository.findById(documentId)
                .orElseThrow(() -> {
                    logger.error("Document not found for deleting with id: {}", documentId);
                    return new ResourceNotFoundException(
                            "Document not found with id: " + documentId);
                });
        employeeDocumentRepository.delete(document);
        logger.info("Document deleted successfully with id: {}", documentId);
    }
}