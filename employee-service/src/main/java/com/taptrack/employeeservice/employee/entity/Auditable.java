package com.taptrack.employeeservice.employee.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SOLID — Open/Closed Principle:
 *   All entities EXTEND this base class to get audit fields.
 *   We never modify existing entities to add created_at/updated_at —
 *   they inherit it from here.
 *
 * SOLID — DRY (Don't Repeat Yourself):
 *   createdAt, updatedAt, createdBy, updatedBy written ONCE here.
 *   All 6 entities extend this instead of duplicating these 4 fields.
 *
 * Usage:
 *   @Entity
 *   public class Employee extends Auditable { ... }
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "created_by", updatable = false, length = 20)
    private String createdBy;

    @Column(name = "updated_by", length = 20)
    private String updatedBy;

    public Auditable() {
    }

    public Auditable(LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Auditable auditable = (Auditable) o;
        return Objects.equals(createdAt, auditable.createdAt) && Objects.equals(updatedAt, auditable.updatedAt) && Objects.equals(createdBy, auditable.createdBy) && Objects.equals(updatedBy, auditable.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, updatedAt, createdBy, updatedBy);
    }

    @Override
    public String toString() {
        return "Auditable{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
