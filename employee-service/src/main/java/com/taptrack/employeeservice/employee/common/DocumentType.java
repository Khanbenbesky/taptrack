package com.taptrack.employeeservice.employee.common;

public enum DocumentType {
    AADHAR,          // Aadhar card — mandatory in India
    PAN,             // PAN card — tax ID
    PASSPORT,        // Passport — for international travel
    OFFER_LETTER,    // Company offer letter
    EXPERIENCE_LETTER, // Previous employer experience letter
    EDUCATION_CERT,  // Degree/diploma certificate
    DRIVING_LICENSE, // Optional
    VISA,            // For employees on work visa
    BANK_DETAILS,    // Bank account proof for payroll
    OTHER            // Any other document
}
