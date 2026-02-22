package com.taptrack.employeeservice.employee.service;

import com.taptrack.employeeservice.employee.dto.EmployeeContactRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactResponseDto;
import com.taptrack.employeeservice.employee.entity.Employee;
import com.taptrack.employeeservice.employee.entity.EmployeeContact;
import com.taptrack.employeeservice.employee.mapper.EmployeeContactMapper;
import com.taptrack.employeeservice.employee.repository.EmployeeContactRepository;
import com.taptrack.employeeservice.employee.repository.EmployeeRepository;
import com.taptrack.employeeservice.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeContactService {

    private final Logger logger = LoggerFactory.getLogger(EmployeeContactService.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeContactRepository employeeContactRepository;
    private final EmployeeContactMapper employeeContactMapper;

    public EmployeeContactService(EmployeeRepository employeeRepository, EmployeeContactRepository employeeContactRepository, EmployeeContactMapper employeeContactMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeContactRepository = employeeContactRepository;
        this.employeeContactMapper = employeeContactMapper;
    }

    @Transactional
    public EmployeeContactResponseDto createEmployeeContact(Long employeeId, EmployeeContactRequestDto dto) {

        logger.info("Creating contact for employeeId={}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    logger.error("Employee not found: {}", employeeId);
                    return new ResourceNotFoundException("Employee not found");
                });

        if (Boolean.TRUE.equals(dto.getPrimary())) {
            employeeContactRepository.findByEmployeeIdAndIsPrimaryTrue(employeeId)
                    .ifPresent(existing -> {
                        logger.info("Removing existing primary contact for employeeId={}", employeeId);
                        existing.setPrimary(false);
                    });
        }

        EmployeeContact contact = employeeContactMapper.toEntity(dto);
        contact.setEmployee(employee);
        contact = employeeContactRepository.save(contact);
        logger.info("Contact created successfully id={}", contact.getId());
        return employeeContactMapper.toDTO(contact);
    }

    @Transactional(readOnly = true)
    public Page<EmployeeContactResponseDto> getContactsAllByEmployeeId(Long employeeId, Pageable pageable) {

        logger.info("Fetching contacts for employeeId={}", employeeId);
        return employeeContactRepository.findByEmployeeId(employeeId, pageable)
                .map(employeeContactMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public EmployeeContactResponseDto getContactByEmployeeIdAndContactId(Long employeeId, Long contactId) {

        logger.info("Fetching contact id={} for employeeId={}", contactId, employeeId);
        EmployeeContact contact = employeeContactRepository
                .findByIdAndEmployeeId(contactId, employeeId)
                .orElseThrow(() -> {
                    logger.error("Contact not found id={} employeeId={}", contactId, employeeId);
                    return new ResourceNotFoundException("Contact not found");
                });
        return employeeContactMapper.toDTO(contact);
    }

    @Transactional
    public EmployeeContactResponseDto updateContactByEmployeeIdAndContactId(Long employeeId, Long contactId, EmployeeContactRequestDto dto) {

        logger.info("Updating contact id={} for employeeId={}", contactId, employeeId);
        EmployeeContact contact = employeeContactRepository
                .findByIdAndEmployeeId(contactId, employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

        if (Boolean.TRUE.equals(dto.getPrimary())) {
            employeeContactRepository.findByEmployeeIdAndIsPrimaryTrue(employeeId)
                    .ifPresent(existing -> existing.setPrimary(false));
        }
        employeeContactMapper.updateEntity(contact, dto);
        logger.info("Contact updated id={}", contactId);
        return employeeContactMapper.toDTO(contact);
    }

    @Transactional
    public void deleteContactByEmployeeIdAndContactId(Long employeeId, Long contactId) {

        logger.info("Deleting contact id={} for employeeId={}", contactId, employeeId);

        EmployeeContact contact = employeeContactRepository
                .findByIdAndEmployeeId(contactId, employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));

        employeeContactRepository.delete(contact);
        logger.info("Contact deleted id={}", contactId);
    }
}
