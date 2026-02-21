package com.taptrack.employeeservice.employee.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactResponseDto;
import com.taptrack.employeeservice.employee.service.EmployeeContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee/contacts")
public class EmployeeContactController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeContactController.class);

    private final EmployeeContactService employeeContactService;

    public EmployeeContactController(EmployeeContactService employeeContactService) {
        this.employeeContactService = employeeContactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> createContact(
            @RequestBody EmployeeContactRequestDto requestDto) {

        logger.info("REST request to create contact for employeeId: {}", requestDto.getEmployeeId());

        EmployeeContactResponseDto responseDto =
                employeeContactService.createContact(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        responseDto,
                        "Employee contact created successfully"
                ));
    }

    /**
     * Get contact by contactId
     */
    @GetMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> getContactById(
            @PathVariable Long contactId) {

        logger.info("REST request to get contact by ID: {}", contactId);

        EmployeeContactResponseDto responseDto =
                employeeContactService.getContactById(contactId);

        return ResponseEntity.ok(
                ApiResponseDto.success(responseDto)
        );
    }

    /**
     * Get contact by employeeId
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> getContactByEmployeeId(
            @PathVariable Long employeeId) {

        logger.info("REST request to get contact by employeeId: {}", employeeId);

        EmployeeContactResponseDto responseDto =
                employeeContactService.getContactByEmployeeId(employeeId);

        return ResponseEntity.ok(
                ApiResponseDto.success(responseDto)
        );
    }

    /**
     * Get all employee contacts
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<EmployeeContactResponseDto>>> getAllContacts() {

        logger.info("REST request to get all employee contacts");

        List<EmployeeContactResponseDto> responseList =
                employeeContactService.getAllContacts();

        return ResponseEntity.ok(
                ApiResponseDto.success(responseList)
        );
    }

    /**
     * Update employee contact
     */
    @PutMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> updateContact(
            @PathVariable Long contactId,
            @RequestBody EmployeeContactRequestDto requestDto) {

        logger.info("REST request to update contact ID: {}", contactId);

        EmployeeContactResponseDto responseDto =
                employeeContactService.updateContact(contactId, requestDto);

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        responseDto,
                        "Employee contact updated successfully"
                )
        );
    }

    /**
     * Delete employee contact
     */
    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteContact(
            @PathVariable Long contactId) {

        logger.info("REST request to delete contact ID: {}", contactId);

        employeeContactService.deleteContact(contactId);

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        null,
                        "Employee contact deleted successfully"
                )
        );
    }
}
