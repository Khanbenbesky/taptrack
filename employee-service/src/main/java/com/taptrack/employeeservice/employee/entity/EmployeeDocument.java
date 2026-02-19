package com.taptrack.employeeservice.employee.entity;

import com.taptrack.employeeservice.employee.common.DocumentType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SOLID — Single Responsibility:
 *   Manages ONLY employee documents.
 *   Verification logic, file paths, and document types
 *   are NOT part of the Employee entity.
 *
 * SOLID — Open/Closed:
 *   New document types (VISA, DRIVING_LICENSE) can be added
 *   to the DocumentType enum without changing any other entity.
 */
@Entity
@Table(
    name = "employee_documents",
    schema = "employee_schema",
    indexes = {
        @Index(name = "idx_doc_employee",  columnList = "employee_id"),
        @Index(name = "idx_doc_type",      columnList = "document_type"),
        @Index(name = "idx_doc_verified",  columnList = "verified")
    }
)
public class EmployeeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Type of document uploaded
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, length = 30)
    private DocumentType documentType;

    /**
     * Official document number
     * Example: AADHAR → "1234 5678 9012", PAN → "ABCDE1234F"
     */
    @Column(name = "document_number", length = 50)
    private String documentNumber;

    /**
     * File storage path (S3 URL or local path)
     * Example: s3://cognizant-docs/emp/CTS001234/aadhar.pdf
     */
    @Column(name = "file_path", length = 500)
    private String filePath;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "file_size_kb")
    private Long fileSizeKb;

    @Column(name = "mime_type", length = 50)
    private String mimeType;

    /**
     * Document expiry date — for passport, visa etc.
     */
    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    /**
     * Whether HR has verified this document
     */
    @Column(name = "verified", nullable = false)
    private Boolean verified = false;

    /**
     * Employee ID of HR person who verified
     */
    @Column(name = "verified_by", length = 20)
    private String verifiedBy;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @Column(name = "remarks", length = 300)
    private String remarks;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;

    // ─── Relationships ────────────────────────────────────────────

    /**
     * MANY Documents → ONE Employee
     *
     * An employee can have multiple documents
     * (Aadhar + PAN + Passport + Offer Letter etc.)
     *
     * FetchType.LAZY: Don't load employee data when
     *   we only need to verify a document.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "employee_id",
        nullable = false,
        referencedColumnName = "id",
        foreignKey = @ForeignKey(name = "fk_document_employee")
    )
    private Employee employee;

    // ─── Business Methods ─────────────────────────────────────────

    /**
     * SRP: Document knows if it is expired — no service needed for this check
     */
    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Document is ready to use — verified and not expired
     */
    public boolean isValid() {
        return Boolean.TRUE.equals(verified) && !isExpired();
    }

    /**
     * Verify this document — called by HR service
     */
    public void verify(String verifiedByEmployeeId) {
        this.verified = true;
        this.verifiedBy = verifiedByEmployeeId;
        this.verifiedAt = LocalDateTime.now();
    }

    public EmployeeDocument() {
    }

    public EmployeeDocument(Long id, DocumentType documentType, String documentNumber, String filePath, String fileName, Long fileSizeKb, String mimeType, LocalDate expiryDate, Boolean verified, String verifiedBy, LocalDateTime verifiedAt, String remarks, LocalDateTime uploadedAt, Employee employee) {
        this.id = id;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSizeKb = fileSizeKb;
        this.mimeType = mimeType;
        this.expiryDate = expiryDate;
        this.verified = verified;
        this.verifiedBy = verifiedBy;
        this.verifiedAt = verifiedAt;
        this.remarks = remarks;
        this.uploadedAt = uploadedAt;
        this.employee = employee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSizeKb() {
        return fileSizeKb;
    }

    public void setFileSizeKb(Long fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDocument that = (EmployeeDocument) o;
        return Objects.equals(id, that.id) && documentType == that.documentType && Objects.equals(documentNumber, that.documentNumber) && Objects.equals(filePath, that.filePath) && Objects.equals(fileName, that.fileName) && Objects.equals(fileSizeKb, that.fileSizeKb) && Objects.equals(mimeType, that.mimeType) && Objects.equals(expiryDate, that.expiryDate) && Objects.equals(verified, that.verified) && Objects.equals(verifiedBy, that.verifiedBy) && Objects.equals(verifiedAt, that.verifiedAt) && Objects.equals(remarks, that.remarks) && Objects.equals(uploadedAt, that.uploadedAt) && Objects.equals(employee, that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, documentType, documentNumber, filePath, fileName, fileSizeKb, mimeType, expiryDate, verified, verifiedBy, verifiedAt, remarks, uploadedAt, employee);
    }

    @Override
    public String toString() {
        return "EmployeeDocument{" +
                "id=" + id +
                ", documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSizeKb=" + fileSizeKb +
                ", mimeType='" + mimeType + '\'' +
                ", expiryDate=" + expiryDate +
                ", verified=" + verified +
                ", verifiedBy='" + verifiedBy + '\'' +
                ", verifiedAt=" + verifiedAt +
                ", remarks='" + remarks + '\'' +
                ", uploadedAt=" + uploadedAt +
                ", employee=" + employee +
                '}';
    }
}
