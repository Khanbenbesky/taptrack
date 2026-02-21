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

    public EmployeeContactResponseDto createContact(
            Long employeeId, EmployeeContactRequestDto employeeContactRequestDto) {

        logger.info("Creating contact for employeeId: {}", employeeId);

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> {
                    logger.error("Employee not found with id: {}", employeeId);
                    return new ResourceNotFoundException(
                            "Employee not found with id: " + employeeId);
                });
        EmployeeContact contact = employeeContactMapper.toEntity(employeeContactRequestDto);
        contact.setEmployee(employee);
        employeeContactRepository.save(contact);
        logger.info("Contact created successfully for employeeId: {}", employeeId);
        return employeeContactMapper.toDTO(contact);
    }


    @Transactional(readOnly = true)
    public EmployeeContactResponseDto getContactById(Long contactId) {

        logger.info("Fetching contact with id: {}", contactId);
        EmployeeContact contact = employeeContactRepository.findById(contactId)
                .orElseThrow(() -> {
                    logger.error("Employee Contact not found with id: {}", contactId);
                    return new ResourceNotFoundException(
                            "Contact not found with id: " + contactId);
                });
        return employeeContactMapper.toDTO(contact);
    }

    @Transactional(readOnly = true)
    public List<EmployeeContactResponseDto> getContactsByEmployeeId(Long employeeId) {

        logger.info("Fetching contacts for employeeId: {}", employeeId);

        return employeeContactRepository.findByEmployeeEmployeeId(employeeId)
                .stream()
                .map(employeeContactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeContactResponseDto updateContact(
            Long contactId, EmployeeContactRequestDto employeeContactRequestDto) {

        logger.info("Updating contact with id: {}", contactId);
        EmployeeContact employeeContact = employeeContactRepository.findById(contactId)
                .orElseThrow(() -> {
                    logger.error("Contact not found for updating with id: {}", contactId);
                    return new ResourceNotFoundException(
                            "Contact not found with id: " + contactId);
                });

        employeeContact.setContactType(employeeContactRequestDto.getContactType());
        employeeContact.setContactName(employeeContactRequestDto.getContactName());
        employeeContact.setContactPhone(employeeContactRequestDto.getContactPhone());
        employeeContact.setContactEmail(employeeContactRequestDto.getContactEmail());
        employeeContact.setRelationship(employeeContactRequestDto.getRelationship());
        employeeContact.setAddressLine1(employeeContactRequestDto.getAddressLine1());
        employeeContact.setAddressLine2(employeeContactRequestDto.getAddressLine2());
        employeeContact.setCity(employeeContactRequestDto.getCity());
        employeeContact.setState(employeeContactRequestDto.getState());
        employeeContact.setCountry(employeeContactRequestDto.getCountry());
        employeeContact.setPincode(employeeContactRequestDto.getPincode());
        employeeContact.setPrimary(employeeContactRequestDto.getPrimary());
        employeeContactRepository.save(employeeContact);
        logger.info("Contact updated successfully with id: {}", contactId);
        return employeeContactMapper.toDTO(employeeContact);
    }

    public void deleteContact(Long contactId) {

        logger.info("Deleting contact with id: {}", contactId);
        EmployeeContact contact = employeeContactRepository.findById(contactId)
                .orElseThrow(() -> {
                    logger.error("Contact not found for deleting with id: {}", contactId);
                    return new ResourceNotFoundException(
                            "Contact not found for deleting with id: " + contactId);
                });
        employeeContactRepository.delete(contact);
        logger.info("Contact deleted successfully with id: {}", contactId);
    }
}
