package com.taptrack.employeeservice.shift.controller;

import com.taptrack.employeeservice.employee.dto.ApiResponseDto;
import com.taptrack.employeeservice.shift.dto.ShiftRequestDto;
import com.taptrack.employeeservice.shift.dto.ShiftResponseDto;
import com.taptrack.employeeservice.shift.service.ShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shifts")
public class ShiftController {

    private final Logger logger = LoggerFactory.getLogger(ShiftController.class);

    private final ShiftService shiftService;

    public ShiftController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<ShiftResponseDto>> createShift(
            @RequestBody ShiftRequestDto request) {

        logger.info("REST request to create Shift: {}", request.getShiftCode());
        ShiftResponseDto shiftResponseDto = shiftService.createShift(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(
                        shiftResponseDto,
                        "Shift created successfully"
                ));
    }

    @GetMapping("/{shiftId}")
    public ResponseEntity<ApiResponseDto<ShiftResponseDto>> getShift(
            @PathVariable Long shiftId) {

        logger.info("REST request to get Shift by ID: {}", shiftId);
        ShiftResponseDto shiftResponseDto = shiftService.getByShiftId(shiftId);
        return ResponseEntity.ok(
                ApiResponseDto.success(shiftResponseDto)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<List<ShiftResponseDto>>> getAllShifts() {

        logger.info("REST request to get all Shifts");
        List<ShiftResponseDto> shiftResponseDto =
                shiftService.getAllShifts();
        return ResponseEntity.ok(
                ApiResponseDto.success(shiftResponseDto)
        );
    }

    @PutMapping("/{shiftId}")
    public ResponseEntity<ApiResponseDto<ShiftResponseDto>> updateShift(
            @PathVariable Long shiftId,
            @RequestBody ShiftRequestDto shiftRequestDto) {

        logger.info("REST request to update Shift ID: {}", shiftId);
        ShiftResponseDto shiftResponseDto =
                shiftService.updateShift(shiftId, shiftRequestDto);
        return ResponseEntity.ok(
                ApiResponseDto.success(
                        shiftResponseDto,
                        "Shift updated successfully"
                )
        );
    }

    @DeleteMapping("/{shiftId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteShift(
            @PathVariable Long shiftId) {

        logger.info("REST request to delete Shift ID: {}", shiftId);
        shiftService.deleteShift(shiftId);

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        null,
                        "Shift deleted successfully"
                )
        );
    }
}
