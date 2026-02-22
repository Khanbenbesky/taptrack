package com.taptrack.employeeservice.employee.service;

import com.taptrack.employeeservice.department.entity.Department;
import com.taptrack.employeeservice.department.repository.DepartmentRepository;
import com.taptrack.employeeservice.designation.entity.Designation;
import com.taptrack.employeeservice.designation.repository.DesignationRepository;
import com.taptrack.employeeservice.employee.dto.EmployeeRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeResponseDto;
import com.taptrack.employeeservice.employee.dto.EmployeeSummaryDto;
import com.taptrack.employeeservice.employee.entity.Employee;
import com.taptrack.employeeservice.employee.entity.EmployeeSearchDto;
import com.taptrack.employeeservice.employee.mapper.EmployeeMapper;
import com.taptrack.employeeservice.employee.repository.EmployeeRepository;
import com.taptrack.employeeservice.exception.DuplicateResourceException;
import com.taptrack.employeeservice.exception.ResourceNotFoundException;
import com.taptrack.employeeservice.shift.entity.Shift;
import com.taptrack.employeeservice.shift.repository.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final ShiftRepository shiftRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            DesignationRepository designationRepository,
            ShiftRepository shiftRepository,
            EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.shiftRepository = shiftRepository;
        this.employeeMapper = employeeMapper;
    }

    // ─── CREATE ───────────────────────────────────────────────────

    /**
     * Creates a new employee after validating uniqueness of employeeId and email,
     * and resolving all FK references (department, designation, shift, manager).
     */
    public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {

        logger.info("Creating employee with employeeId: {}", dto.getEmployeeId());

        // Uniqueness guards
        if (employeeRepository.existsByEmployeeId(dto.getEmployeeId())) {
            logger.error("Duplicate employeeId: {}", dto.getEmployeeId());
            throw new DuplicateResourceException(
                    "Employee already exists with employeeId: " + dto.getEmployeeId());
        }
        if (employeeRepository.existsByEmail(dto.getEmail())) {
            logger.error("Duplicate email: {}", dto.getEmail());
            throw new DuplicateResourceException(
                    "Employee already exists with email: " + dto.getEmail());
        }

        Employee employee = new Employee();
        mapRequestToEntity(dto, employee);

        Employee saved = employeeRepository.save(employee);
        logger.info("Employee created successfully with id: {}", saved.getId());
        return employeeMapper.toDTO(saved);
    }

    // ─── READ ─────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(Long id) {

        logger.info("Fetching employee with id: {}", id);
        return employeeMapper.toDTO(findEmployeeOrThrow(id));
    }

    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByEmployeeId(String employeeId) {

        logger.info("Fetching employee with employeeId: {}", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> {
                    logger.error("Employee not found with employeeId: {}", employeeId);
                    return new ResourceNotFoundException(
                            "Employee not found with employeeId: " + employeeId);
                });
        return employeeMapper.toDTO(employee);
    }

    /**
     * Paginated list of all employees (active + inactive).
     * Use search endpoint for filtering.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getAllEmployees(Pageable pageable) {

        logger.info("Fetching all employees - page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toDTO);
    }

    /**
     * Paginated list of only ACTIVE employees.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getActiveEmployees(Pageable pageable) {

        logger.info("Fetching active employees");
        return employeeRepository.findByIsActiveTrue(pageable)
                .map(employeeMapper::toDTO);
    }

    /**
     * Search/filter employees by department, designation, shift, or name.
     * All filters are optional — omit any to skip that filter.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> searchEmployees(EmployeeSearchDto searchDto, Pageable pageable) {

        logger.info("Searching employees with filters: {}", searchDto);
        return employeeRepository.searchEmployees(
                searchDto.getDepartmentId(),
                searchDto.getDesignationId(),
                searchDto.getShiftId(),
                searchDto.getActive(),
                searchDto.getKeyword(),
                pageable
        ).map(employeeMapper::toDTO);
    }

    /**
     * Returns direct subordinates (reportees) of a manager.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSummaryDto> getSubordinates(Long managerId) {

        logger.info("Fetching subordinates for managerId: {}", managerId);
        findEmployeeOrThrow(managerId); // validate manager exists
        return employeeRepository.findByManagerId(managerId)
                .stream()
                .map(employeeMapper::toSummaryDTO)
                .toList();
    }

    /**
     * Returns all employees in a department.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDto> getEmployeesByDepartment(Long departmentId, Pageable pageable) {

        logger.info("Fetching employees for departmentId: {}", departmentId);
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        }
        return employeeRepository.findByDepartmentId(departmentId, pageable)
                .map(employeeMapper::toDTO);
    }

    // ─── UPDATE ───────────────────────────────────────────────────

    /**
     * Full update of employee fields.
     * employeeId (business key) is immutable after creation.
     * Email uniqueness is re-validated if it changed.
     */
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto dto) {

        logger.info("Updating employee with id: {}", id);
        Employee employee = findEmployeeOrThrow(id);

        // If email changed, check uniqueness
        if (!employee.getEmail().equalsIgnoreCase(dto.getEmail())
                && employeeRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            logger.error("Email already in use: {}", dto.getEmail());
            throw new DuplicateResourceException(
                    "Email already in use by another employee: " + dto.getEmail());
        }

        mapRequestToEntity(dto, employee);
        Employee updated = employeeRepository.save(employee);
        logger.info("Employee updated successfully with id: {}", updated.getId());
        return employeeMapper.toDTO(updated);
    }

    /**
     * Transfers an employee to a new department + designation.
     * Keeps all other fields intact.
     */
    public EmployeeResponseDto transferEmployee(Long id, Long newDepartmentId, Long newDesignationId) {

        logger.info("Transferring employee id: {} to department: {}", id, newDepartmentId);
        Employee employee = findEmployeeOrThrow(id);

        Department department = departmentRepository.findById(newDepartmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + newDepartmentId));

        Designation designation = designationRepository.findById(newDesignationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Designation not found with id: " + newDesignationId));

        employee.setDepartment(department);
        employee.setDesignation(designation);
        logger.info("Employee id: {} transferred successfully", id);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    /**
     * Reassigns an employee to a new shift.
     */
    public EmployeeResponseDto changeShift(Long id, Long newShiftId) {

        logger.info("Changing shift for employee id: {} to shiftId: {}", id, newShiftId);
        Employee employee = findEmployeeOrThrow(id);

        Shift shift = shiftRepository.findById(newShiftId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shift not found with id: " + newShiftId));

        employee.setShift(shift);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    /**
     * Assigns or changes the reporting manager for an employee.
     * Guards against self-assignment.
     */
    public EmployeeResponseDto assignManager(Long employeeId, Long managerId) {

        logger.info("Assigning manager id: {} to employee id: {}", managerId, employeeId);

        if (employeeId.equals(managerId)) {
            throw new IllegalArgumentException("An employee cannot be their own manager");
        }

        Employee employee = findEmployeeOrThrow(employeeId);
        Employee manager = findEmployeeOrThrow(managerId);

        employee.setManager(manager);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    // ─── SOFT DELETE / DEACTIVATE ─────────────────────────────────

    /**
     * Soft delete — sets isActive = false.
     * Hard delete is intentionally not exposed via API.
     * Use deactivateEmployee for offboarding.
     */
    public void deactivateEmployee(Long id) {

        logger.info("Deactivating employee with id: {}", id);
        Employee employee = findEmployeeOrThrow(id);
        employee.setIsActive(false);
        employeeRepository.save(employee);
        logger.info("Employee deactivated successfully with id: {}", id);
    }

    /**
     * Reactivate a previously deactivated employee (rehire scenario).
     */
    public EmployeeResponseDto reactivateEmployee(Long id) {

        logger.info("Reactivating employee with id: {}", id);
        Employee employee = findEmployeeOrThrow(id);
        employee.setIsActive(true);
        return employeeMapper.toDTO(employeeRepository.save(employee));
    }

    // ─── PRIVATE HELPERS ──────────────────────────────────────────

    /**
     * Shared mapping logic used by both create and update.
     * Resolves all FK references and applies all scalar fields.
     */
    private void mapRequestToEntity(EmployeeRequestDto dto, Employee employee) {

        // Scalar fields
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setDateOfJoining(dto.getDateOfJoining());
        employee.setDateOfBirth(dto.getDateOfBirth());
        employee.setGender(dto.getGender());
        employee.setBloodGroup(dto.getBloodGroup());
        employee.setProfilePhotoUrl(dto.getProfilePhotoUrl());

        // Only set employeeId on create (it will already be set during create path)
        if (dto.getEmployeeId() != null) {
            employee.setEmployeeId(dto.getEmployeeId());
        }

        // Resolve Department (required)
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> {
                    logger.error("Department not found with id: {}", dto.getDepartmentId());
                    return new ResourceNotFoundException(
                            "Department not found with id: " + dto.getDepartmentId());
                });
        employee.setDepartment(department);

        // Resolve Designation (required)
        Designation designation = designationRepository.findById(dto.getDesignationId())
                .orElseThrow(() -> {
                    logger.error("Designation not found with id: {}", dto.getDesignationId());
                    return new ResourceNotFoundException(
                            "Designation not found with id: " + dto.getDesignationId());
                });
        employee.setDesignation(designation);

        // Resolve Shift (required)
        Shift shift = shiftRepository.findById(dto.getShiftId())
                .orElseThrow(() -> {
                    logger.error("Shift not found with id: {}", dto.getShiftId());
                    return new ResourceNotFoundException(
                            "Shift not found with id: " + dto.getShiftId());
                });
        employee.setShift(shift);

        // Resolve Manager (optional)
        if (dto.getManagerId() != null) {
            Employee manager = employeeRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> {
                        logger.error("Manager not found with id: {}", dto.getManagerId());
                        return new ResourceNotFoundException(
                                "Manager not found with id: " + dto.getManagerId());
                    });
            employee.setManager(manager);
        } else {
            employee.setManager(null); // allow clearing manager
        }
    }

    private Employee findEmployeeOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });
    }
}
