package com.taptrack.employeeservice.employee.dto;

import com.taptrack.employeeservice.employee.common.DocumentType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmployeeDocumentResponseDto {

    private Long id;
    private DocumentType documentType;
    private String documentNumber;
    private String filePath;
    private String fileName;
    private Long fileSizeKb;
    private String mimeType;
    private LocalDate expiryDate;
    private Boolean verified;
    private String verifiedBy;
    private LocalDateTime verifiedAt;
    private Boolean expired;
    private Boolean valid;
    private LocalDateTime uploadedAt;

    public EmployeeDocumentResponseDto() {
    }

    public EmployeeDocumentResponseDto(Long id, DocumentType documentType, String documentNumber, String filePath, String fileName, Long fileSizeKb, String mimeType, LocalDate expiryDate, Boolean verified, String verifiedBy, LocalDateTime verifiedAt, Boolean expired, Boolean valid, LocalDateTime uploadedAt) {
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
        this.expired = expired;
        this.valid = valid;
        this.uploadedAt = uploadedAt;
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

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    @Override
    public String toString() {
        return "EmployeeDocumentResponseDto{" +
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
                ", expired=" + expired +
                ", valid=" + valid +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
