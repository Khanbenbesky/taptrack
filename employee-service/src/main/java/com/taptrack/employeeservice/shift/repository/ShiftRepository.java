package com.taptrack.employeeservice.shift.repository;

import com.taptrack.employeeservice.shift.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    Optional<Shift> findByShiftCode(String code);
    List<Shift> findByIsActiveTrue();
    boolean existsByShiftCode(String code);
}
