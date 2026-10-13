package com.example.EventPass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    boolean existsByTicketCode(String ticketCode);

    List<Attendance> findByEventId(Long eventId);
}