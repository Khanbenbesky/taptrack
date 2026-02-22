package com.taptrack.employeeservice.employee.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeSummaryDto;
import com.taptrack.employeeservice.employee.entity.EmployeeSearchDto;
import com.taptrack.employeeservice.employee.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private static final Logger  logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ─── CREATE ───────────────────────────────────────────────────

    /**
     * POST /api/v1/employees
     * Onboard a new employee. Requires departmentId, designationId, shiftId.
     * managerId is optional.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> createEmployee(
            @RequestBody EmployeeRequestDto employeeRequestDto) {

        logger.info("REST request to create employee: {}", employeeRequestDto.getEmployeeId());
        EmployeeResponseDto response = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(response, "Employee created successfully"));
    }

    // ─── READ ─────────────────────────────────────────────────────

    /**
     * GET /api/v1/employees/{id}
     * Fetch employee by internal DB id.
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> getEmployeeById(
            @PathVariable Long employeeId) {

        logger.info("REST request to get employee by id: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getEmployeeById(employeeId)));
    }

    /**
     * GET /api/v1/employees/by-employee-id/{employeeId}
     * Fetch by business key (e.g. EMP001). Useful for cross-service lookups.
     */
    @GetMapping("/by-employee-id/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> getByEmployeeId(
            @PathVariable String employeeId) {

        logger.info("REST request to get employee by employeeId: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getEmployeeByEmployeeId(employeeId)));
    }

    /**
     * GET /api/v1/employees?page=0&size=20&sort=firstName,asc
     * Paginated list of ALL employees (active + inactive).
     */
    @GetMapping
    public ResponseEntity<ApiResponseDto<Page<EmployeeResponseDto>>> getAllEmployees(
            @PageableDefault(size = 20, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        logger.info("REST request to get all employees - page: {}", pageable.getPageNumber());
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getAllEmployees(pageable)));
    }

    /**
     * GET /api/v1/employees/active?page=0&size=20
     * Paginated list of ACTIVE employees only.
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponseDto<Page<EmployeeResponseDto>>> getActiveEmployees(
            @PageableDefault(size = 20, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        logger.info("REST request to get active employees");
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getActiveEmployees(pageable)));
    }

    /**
     * GET /api/v1/employees/search?departmentId=1&designationId=2&isActive=true&keyword=John
     * Dynamic search with optional filters. All params are optional.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<Page<EmployeeResponseDto>>> searchEmployees(
            @ModelAttribute EmployeeSearchDto searchDto,
            @PageableDefault(size = 20, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        logger.info("REST request to search employees with filters: {}", searchDto);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.searchEmployees(searchDto, pageable)));
    }

    /**
     * GET /api/v1/employees/{id}/subordinates
     * Returns direct reportees of a manager.
     */
    @GetMapping("/{employeeId}/subordinates")
    public ResponseEntity<ApiResponseDto<List<EmployeeSummaryDto>>> getSubordinates(
            @PathVariable Long employeeId) {

        logger.info("REST request to get subordinates for managerId: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getSubordinates(employeeId)));
    }

    /**
     * GET /api/v1/employees/by-department/{departmentId}?page=0&size=20
     * All employees in a department (paginated).
     */
    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<ApiResponseDto<Page<EmployeeResponseDto>>> getByDepartment(
            @PathVariable Long departmentId,
            @PageableDefault(size = 20, sort = "firstName", direction = Sort.Direction.ASC)
            Pageable pageable) {

        logger.info("REST request to get employees by departmentId: {}", departmentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(employeeService.getEmployeesByDepartment(departmentId, pageable)));
    }

    // ─── UPDATE ───────────────────────────────────────────────────

    /**
     * PUT /api/v1/employees/{id}
     * Full update. employeeId (business key) is immutable.
     * Email uniqueness is re-validated if changed.
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> updateEmployee(
            @PathVariable Long employeeId,
            @RequestBody EmployeeRequestDto dto) {

        logger.info("REST request to update employee with id: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        employeeService.updateEmployee(employeeId, dto),
                        "Employee updated successfully"));
    }

    /**
     * PATCH /api/v1/employees/{id}/transfer
     * Transfer employee to a new department and designation.
     * Query params: newDepartmentId, newDesignationId
     */
    @PatchMapping("/{id}/transfer")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> transferEmployee(
            @PathVariable Long id,
            @RequestParam Long newDepartmentId,
            @RequestParam Long newDesignationId) {

        logger.info("REST request to transfer employee id: {} to departmentId: {}",
                id, newDepartmentId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        employeeService.transferEmployee(id, newDepartmentId, newDesignationId),
                        "Employee transferred successfully"));
    }

    /**
     * PATCH /api/v1/employees/{id}/shift
     * Change the shift for an employee.
     * Query param: newShiftId
     */
    @PatchMapping("/{employeeId}/shift")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> changeShift(
            @PathVariable Long employeeId,
            @RequestParam Long newShiftId) {

        logger.info("REST request to change shift for employee id: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        employeeService.changeShift(employeeId, newShiftId),
                        "Employee shift updated successfully"));
    }

    /**
     * PATCH /api/v1/employees/{id}/manager
     * Assign or change the reporting manager.
     * Query param: managerId
     */
    @PatchMapping("/{employeeId}/manager")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> assignManager(
            @PathVariable Long employeeId,
            @RequestParam Long managerId) {

        logger.info("REST request to assign manager id: {} to employee id: {}", managerId, employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        employeeService.assignManager(employeeId, managerId),
                        "Manager assigned successfully"));
    }

    // ─── DEACTIVATE / REACTIVATE ──────────────────────────────────

    /**
     * PATCH /api/v1/employees/{id}/deactivate
     * Soft delete — marks employee as inactive (offboarding).
     * Does NOT delete any DB record.
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponseDto<Void>> deactivateEmployee(@PathVariable Long id) {

        logger.info("REST request to deactivate employee id: {}", id);
        employeeService.deactivateEmployee(id);
        return ResponseEntity.ok(
                ApiResponseDto.success(null, "Employee deactivated successfully"));
    }

    /**
     * PATCH /api/v1/employees/{id}/reactivate
     * Reactivate a previously deactivated employee (rehire scenario).
     */
    @PatchMapping("/{employeeId}/reactivate")
    public ResponseEntity<ApiResponseDto<EmployeeResponseDto>> reactivateEmployee(
            @PathVariable Long employeeId) {

        logger.info("REST request to reactivate employee id: {}", employeeId);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        employeeService.reactivateEmployee(employeeId),
                        "Employee reactivated successfully"));
    }
}
