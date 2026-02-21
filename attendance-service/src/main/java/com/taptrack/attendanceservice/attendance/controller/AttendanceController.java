package com.taptrack.attendanceservice.attendance.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

}
