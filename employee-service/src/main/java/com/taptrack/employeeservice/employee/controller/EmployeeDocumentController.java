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
@RequestMapping("/api/v1/documents")
public class EmployeeDocumentController {

    private final Logger logger = LoggerFactory.getLogger(EmployeeDocumentController.class);

    private final EmployeeDocumentService employeeDocumentService;

    public EmployeeDocumentController(EmployeeDocumentService employeeDocumentService) {
        this.employeeDocumentService = employeeDocumentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> uploadDocument(
            @RequestBody EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("REST request to upload document for employeeId: {}",
                employeeDocumentRequestDto.getDocumentNumber());
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.createEmployeeDocument(employeeDocumentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        responseDto,
                        "Employee document uploaded successfully"
                ));
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> getDocumentById(
            @PathVariable Long documentId) {

        logger.info("REST request to get document by ID: {}");
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.getEmployeeDocumentById(documentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(responseDto)
        );
    }

    /**
     * Get all documents for employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponseDto<List<EmployeeDocumentResponseDto>>> getDocumentsByEmployeeId(
            @PathVariable Long employeeId) {

        logger.info("REST request to get documents for employeeId: {}", employeeId);

        List<EmployeeDocumentResponseDto> responseList =
                employeeDocumentService.getDocumentsByEmployeeId(employeeId);

        return ResponseEntity.ok(
                ApiResponseDto.success(responseList)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeDocumentResponseDto>>> getAllDocuments() {

        logger.info("REST request to get all employee documents");

        List<EmployeeDocumentResponseDto> responseList =
                employeeDocumentService.getEmployeeDocumentsByEmployeeId();

        return ResponseEntity.ok(
                ApiResponseDto.success(responseList)
        );
    }

    @PutMapping("/{documentId}")
    public ResponseEntity<ApiResponseDto<EmployeeDocumentResponseDto>> updateDocument(
            @PathVariable Long documentId,
            @RequestBody EmployeeDocumentRequestDto employeeDocumentRequestDto) {

        logger.info("REST request to update document ID: {}");
        EmployeeDocumentResponseDto responseDto =
                employeeDocumentService.updateEmployeeDocument(documentId, employeeDocumentRequestDto);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        responseDto,
                        "Employee document updated successfully"
                )
        );
    }

    @DeleteMapping("/{documentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteDocument(
            @PathVariable Long documentId) {

        logger.info("REST request to delete document ID: {}");
        employeeDocumentService.deleteEmployeeDocument(documentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        null,
                        "Employee document deleted successfully"
                )
        );
    }
}
