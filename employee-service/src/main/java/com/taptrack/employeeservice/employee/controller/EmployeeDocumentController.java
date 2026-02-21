package com.taptrack.employeeservice.employee.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeDocumentRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeDocumentResponseDto;
import com.taptrack.employeeservice.employee.service.EmployeeDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeDocumentController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeDocumentController.class);

    private final EmployeeDocumentService employeeDocumentService;

    public EmployeeDocumentController(EmployeeDocumentService employeeDocumentService) {
        this.employeeDocumentService = employeeDocumentService;
    }

    @PostMapping("/{employeeId}/documents")
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> uploadDocument(
            @PathVariable Long employeeId, @RequestBody EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("REST request to upload document for employeeId: {}",
                employeeDocumentRequestDto.getDocumentNumber());
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.createEmployeeDocument(employeeId, employeeDocumentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        responseDto,
                        "Employee document uploaded successfully"
                ));
    }

    @GetMapping("/{employeeId}/documents/{documentId}")
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> getDocumentById(
            @PathVariable Long employeeId, @PathVariable Long documentId) {

        logger.info("REST request to get documents for documentId: {}", documentId);
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.getEmployeeDocumentById(documentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(responseDto)
        );
    }

    @GetMapping("/{employeeId}/documents")
    public ResponseEntity<ApiResponseDto<List<EmployeeDocumentResponseDto>>> getAllDocumentsByEmployeeId(
            @PathVariable Long employeeId) {

        logger.info("REST request to get all documents for employeeId: {}", employeeId);
        List<EmployeeDocumentResponseDto> responseList =
                employeeDocumentService.getDocumentsByEmployeeId(employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(responseList)
        );
    }

    @PutMapping("/{employeeId}/documents/{documentId}")
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> updateDocument(
            @PathVariable Long employeeId, @PathVariable Long documentId,
            @RequestBody EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("REST request to update employee document ID: {}", documentId);
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.updateEmployeeDocument(employeeId, documentId, employeeDocumentRequestDto);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        responseDto,
                        "Employee document updated successfully"
                )
        );
    }

    @DeleteMapping("/{employeeId}/documents/{documentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteDocument(
            @PathVariable Long employeeId, @PathVariable Long documentId) {

        logger.info("REST request to delete document ID: {}", documentId);
        employeeDocumentService.deleteEmployeeDocument(employeeId, documentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        null,
                        "Employee document deleted successfully"
                )
        );
    }
}
