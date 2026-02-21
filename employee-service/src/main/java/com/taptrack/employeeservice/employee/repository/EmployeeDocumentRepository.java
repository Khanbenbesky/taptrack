package com.taptrack.employeeservice.employee.repository;

import com.taptrack.employeeservice.employee.common.DocumentType;
import com.taptrack.employeeservice.employee.entity.EmployeeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeDocumentRepository extends JpaRepository<EmployeeDocument, Long> {

    Optional<EmployeeDocument> findByEmployeeIdAndId(Long employeeId, Long documentId);
    List<EmployeeDocument> findByEmployeeId(Long employeeId);
    List<EmployeeDocument> findByEmployeeEmployeeIdAndDocumentType(
            String employeeId, DocumentType documentType);
    List<EmployeeDocument> findByVerifiedFalse();
    // Find documents expiring within next 30 days — for HR alerts
    @Query("SELECT d FROM EmployeeDocument d WHERE d.expiryDate BETWEEN :today AND :thirtyDaysLater")
    List<EmployeeDocument> findExpiringDocuments(LocalDate today, LocalDate thirtyDaysLater);
}
