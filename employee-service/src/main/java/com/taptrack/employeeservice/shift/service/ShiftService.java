package com.taptrack.employeeservice.shift.service;

import com.taptrack.employeeservice.exception.DuplicateResourceException;
import com.taptrack.employeeservice.exception.ResourceNotFoundException;
import com.taptrack.employeeservice.shift.dto.ShiftRequestDto;
import com.taptrack.employeeservice.shift.dto.ShiftResponseDto;
import com.taptrack.employeeservice.shift.entity.Shift;
import com.taptrack.employeeservice.shift.mapper.ShiftMapper;
import com.taptrack.employeeservice.shift.repository.ShiftRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftService {

    private final Logger logger = LoggerFactory.getLogger(ShiftService.class);

    private final ShiftRepository shiftRepository;
    private final ShiftMapper shiftMapper;

    public ShiftService(ShiftRepository shiftRepository, ShiftMapper shiftMapper) {
        this.shiftRepository = shiftRepository;
        this.shiftMapper = shiftMapper;
    }

    public ShiftResponseDto createShift(ShiftRequestDto shiftRequestDto) {

        logger.info("Creating shift with code: {}", shiftRequestDto.getShiftCode());
        if(shiftRepository.existsByShiftCode(shiftRequestDto.getShiftCode())) {
            logger.error("Shift already exists with code: {}", shiftRequestDto.getShiftCode());
            throw new DuplicateResourceException("Shift code already exists"+shiftRequestDto.getShiftCode());
        }
        Shift shift = shiftMapper.toEntity(shiftRequestDto);
        shiftRepository.save(shift);
        return shiftMapper.toDTO(shift);
    }

    public ShiftResponseDto getByShiftId(Long shiftId) {

        logger.info("Fetching shift with id: {}", shiftId);
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> {
                    logger.error("Shift not found with id: {}", shiftId);
                    return new ResourceNotFoundException("Shift not found with: "+shiftId);
                });
        return shiftMapper.toDTO(shift);
    }

    public List<ShiftResponseDto> getAllShifts() {

        logger.info("Fetching all departments");
        return shiftRepository.findAll()
                .stream()
                .map(shiftMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ShiftResponseDto updateShift(Long shiftId, ShiftRequestDto shiftRequestDto) {

        logger.info("Attempting to update shift with id: {}", shiftId);
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> {
                    logger.error("Updating shift with id: {}", shiftId);
                    return new ResourceNotFoundException("Shift not found with id: "+shiftId);
                });
        shift.setShiftName(shiftRequestDto.getShiftName());
        shift.setShiftStart(shiftRequestDto.getShiftStart());
        shift.setShiftEnd(shiftRequestDto.getShiftEnd());
        shift.setGraceMinutes(shiftRequestDto.getGraceMinutes());
        shiftRepository.save(shift);
        return shiftMapper.toDTO(shift);
    }

    public void deleteShift(Long shiftId) {

        logger.info("Attempting to delete shift with id: {}", shiftId);
        Shift shift = shiftRepository.findById(shiftId)
                .orElseThrow(() -> {
                    logger.error("Shift not found for deletion with id: {}", shiftId);
                    return new ResourceNotFoundException("Shift not found with id: "+shiftId);
                });
        shift.setActive(false);
        shiftRepository.save(shift);
    }

}
