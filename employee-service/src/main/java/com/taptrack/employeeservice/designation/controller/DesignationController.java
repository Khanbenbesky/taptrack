package com.taptrack.employeeservice.designation.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.designation.dto.DesignationRequestDto;
import com.taptrack.employeeservice.designation.dto.DesignationResponseDto;
import com.taptrack.employeeservice.designation.service.DesignationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/designations")
public class DesignationController {

    private final Logger logger = LoggerFactory.getLogger(DesignationController.class);

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponseDto<DesignationResponseDto>> create(
            @RequestBody DesignationRequestDto designationRequestDto) {

        logger.info("REST ENDPOINT request to create designation");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        designationService.createDesignation(designationRequestDto),
                        "Designation created successfully"
                ));
    }

    @GetMapping("/{designationId}")
    public ResponseEntity<ApiResponseDto<DesignationResponseDto>> findDesignation(@PathVariable Long designationId) {
        logger.info("REST ENDPOINT request to get designation");
        DesignationResponseDto designationResponseDto = designationService.getDesignationById(designationId);
        return ResponseEntity.ok(ApiResponseDto.success(designationResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DesignationResponseDto>>> findAllDesignations() {
        logger.info("REST ENDPOINT request to get all designation");
        return ResponseEntity.ok(ApiResponseDto.success(designationService.getAllDesignations()));
    }

    // UPDATE
    @PutMapping("/{designationId}")
    public ResponseEntity<ApiResponseDto<DesignationResponseDto>> updateDesignation(
            @PathVariable Long designationId,
            @RequestBody DesignationRequestDto designationRequestDto) {
        logger.info("REST ENDPOINT request to update designation");
        return ResponseEntity.ok(ApiResponseDto.success(designationService.
                updateDesignationById(designationId, designationRequestDto)));
    }

    @DeleteMapping("/{designationId}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable Long designationId) {
        logger.info("REST ENDPOINT request to delete designation");
        designationService.deleteDesignationById(designationId);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }

}
