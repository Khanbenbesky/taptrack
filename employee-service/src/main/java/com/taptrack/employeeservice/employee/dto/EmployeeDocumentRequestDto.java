package com.taptrack.employeeservice.employee.dto;

import com.taptrack.employeeservice.employee.common.DocumentType;

import java.time.LocalDate;

public class EmployeeDocumentRequestDto {

    private DocumentType documentType;
    private String documentNumber;
    private String filePath;
    private String fileName;
    private Long fileSizeKb;
    private String mimeType;
    private LocalDate expiryDate;
    private String remarks;

    public EmployeeDocumentRequestDto() {
    }

    public EmployeeDocumentRequestDto(DocumentType documentType, String documentNumber, String filePath, String fileName, Long fileSizeKb, String mimeType, LocalDate expiryDate, String remarks) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileSizeKb = fileSizeKb;
        this.mimeType = mimeType;
        this.expiryDate = expiryDate;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "EmployeeDocumentRequestDto{" +
                "documentType=" + documentType +
                ", documentNumber='" + documentNumber + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileSizeKb=" + fileSizeKb +
                ", mimeType='" + mimeType + '\'' +
                ", expiryDate=" + expiryDate +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
