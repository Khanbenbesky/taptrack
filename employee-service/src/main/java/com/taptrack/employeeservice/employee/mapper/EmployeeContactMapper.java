package com.taptrack.employeeservice.employee.mapper;

import com.taptrack.employeeservice.employee.dto.EmployeeContactRequestDto;
import com.taptrack.employeeservice.employee.dto.EmployeeContactResponseDto;
import com.taptrack.employeeservice.employee.entity.EmployeeContact;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeContactMapper {

    public EmployeeContactResponseDto toDTO(EmployeeContact contact) {
        if (contact == null) return null;
        EmployeeContactResponseDto dto = new EmployeeContactResponseDto();
        dto.setId(contact.getId());
        dto.setContactType(contact.getContactType());
        dto.setContactName(contact.getContactName());
        dto.setContactPhone(contact.getContactPhone());
        dto.setContactEmail(contact.getContactEmail());
        dto.setRelationship(contact.getRelationship());
        dto.setAddressLine1(contact.getFullAddress() == null ? "" : contact.getFullAddress());
        dto.setAddressLine2(contact.getAddressLine2());
        dto.setCity(contact.getCity());
        dto.setState(contact.getState());
        dto.setCountry(contact.getCountry());
        dto.setPincode(contact.getPincode());
        dto.setPrimary(contact.getPrimary());
        dto.setCreatedAt(contact.getCreatedAt());

        // Expose only minimal employee info — avoid circular serialization
        if (contact.getEmployee() != null) {
            dto.setId(contact.getEmployee().getId());
            //dto.setEmployeeCode(contact.getEmployee().getEmployeeId());
        }

        return dto;
    }

    public EmployeeContact toEntity(EmployeeContactRequestDto dto) {
        if (dto == null) return null;

        EmployeeContact contact = new EmployeeContact();
        mapRequestToEntity(dto, contact);
        return contact;
    }

    // Used by update — modifies existing entity in-place
    public void updateEntity(EmployeeContact contact, EmployeeContactRequestDto dto) {
        if (dto == null || contact == null) return;
        mapRequestToEntity(dto, contact);
    }

    // ─── Shared mapping logic ─────────────────────────────────────
    private void mapRequestToEntity(EmployeeContactRequestDto dto, EmployeeContact contact) {
        contact.setContactType(dto.getContactType());
        contact.setContactName(dto.getContactName());
        contact.setContactPhone(dto.getContactPhone());
        contact.setContactEmail(dto.getContactEmail());
        contact.setRelationship(dto.getRelationship());
        contact.setAddressLine1(dto.getAddressLine1());
        contact.setAddressLine2(dto.getAddressLine2());
        contact.setCity(dto.getCity());
        contact.setState(dto.getState());
        contact.setCountry(dto.getCountry() != null ? dto.getCountry() : "India");
        contact.setPincode(dto.getPincode());
        contact.setPrimary(dto.getPrimary() != null ? dto.getPrimary() : false);
        // employee is intentionally NOT set here — handled in service
    }
}
