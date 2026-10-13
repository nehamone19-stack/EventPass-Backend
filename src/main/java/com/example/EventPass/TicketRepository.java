package com.example.EventPass;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Tickets, Long> {

    List<Tickets> findByAttendeeEmail(String attendeeEmail);

    boolean existsByAttendeeEmailAndEventId(
            String attendeeEmail,
            Long eventId);
}