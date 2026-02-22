package com.taptrack.employeeservice.employee.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactResponseDto;
import com.taptrack.employeeservice.employee.service.EmployeeContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees/{employeeId}/contacts")
public class EmployeeContactController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeContactController.class);

    private final EmployeeContactService employeeContactService;

    public EmployeeContactController(EmployeeContactService employeeContactService) {
        this.employeeContactService = employeeContactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> createContact(
            @PathVariable Long employeeId,
            @RequestBody EmployeeContactRequestDto employeeContactRequestDto) {

        logger.info("REST request to create contact for employeeId: {}", employeeId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(employeeContactService.createEmployeeContact(employeeId, employeeContactRequestDto)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<EmployeeContactResponseDto>>> getContacts(
            @PathVariable Long employeeId,
            Pageable pageable) {

        logger.info("REST request to get all employee contacts");
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeContactService.getContactsAllByEmployeeId(employeeId, pageable)));
    }


    @GetMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> getContact(
            @PathVariable Long employeeId,
            @PathVariable Long contactId) {

        logger.info("REST request to get employee contacts");
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeContactService.getContactByEmployeeIdAndContactId(employeeId, contactId)));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<EmployeeContactResponseDto>> updateContact(
            @PathVariable Long employeeId,
            @PathVariable Long contactId,
            @RequestBody EmployeeContactRequestDto request) {

        logger.info("REST request to update contact ID: {}", contactId);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeContactService.updateContactByEmployeeIdAndContactId(employeeId, contactId, request)));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteContact(
            @PathVariable Long employeeId,
            @PathVariable Long contactId) {

        logger.info("REST request to delete contact ID: {}", contactId);
        employeeContactService.deleteContactByEmployeeIdAndContactId(employeeId, contactId);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

}
