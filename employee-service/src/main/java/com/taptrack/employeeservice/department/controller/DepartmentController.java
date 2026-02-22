package com.taptrack.employeeservice.department.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.department.dto.DepartmentRequestDto;
import com.taptrack.employeeservice.department.dto.DepartmentResponseDto;
import com.taptrack.employeeservice.department.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDto<DepartmentResponseDto>> createDepartment(
            @RequestBody DepartmentRequestDto departmentRequestDto) {

        logger.info("REST ENDPOINT request to create department");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        departmentService.createDepartment(departmentRequestDto),
                        "Department created successfully"
                ));
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDto<DepartmentResponseDto>> findDepartment(@PathVariable Long departmentId) {
        logger.info("REST ENDPOINT request to get department");
        DepartmentResponseDto departmentResponseDto = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(ApiResponseDto.success(departmentResponseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<DepartmentResponseDto>>> findAllDepartments() {
        logger.info("REST ENDPOINT request to get all department");
        return ResponseEntity.ok(ApiResponseDto.success(departmentService.getAllDepartments()));
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDto<DepartmentResponseDto>> updateDepartment(
            @PathVariable Long departmentId,
            @RequestBody DepartmentRequestDto departmentRequestDto) {
        logger.info("REST ENDPOINT request to update department");
        return ResponseEntity.ok(ApiResponseDto.success(departmentService.
                updateDepartmentById(departmentId, departmentRequestDto)));
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteDepartment(@PathVariable Long departmentId) {
        logger.info("REST ENDPOINT request to delete department");
        departmentService.deleteDepartmentById(departmentId);
        return ResponseEntity.ok(ApiResponseDto.success(null));
    }
}
