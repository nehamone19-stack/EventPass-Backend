package com.example.EventPass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Verify QR and mark attendance
    @PostMapping("/verify")
    public String verifyTicket(@RequestBody Tickets ticketData) {

        String ticketCode = ticketData.getTicketCode();

        Tickets ticket = ticketRepository
                .findAll()
                .stream()
                .filter(t -> t.getTicketCode().equals(ticketCode))
                .findFirst()
                .orElse(null);

        if (ticket == null) {
            return "Invalid Ticket";
        }

        if ("USED".equals(ticket.getStatus())) {
            return "Ticket Already Used";
        }

        Attendance attendance = new Attendance();

        attendance.setTicketCode(ticket.getTicketCode());
        attendance.setAttendeeName(ticket.getAttendeeName());
        attendance.setAttendeeEmail(ticket.getAttendeeEmail());
        attendance.setEventId(ticket.getEventId());
        attendance.setEventName(ticket.getEventName());
        attendance.setCheckInTime(
                LocalDateTime.now().toString());
        attendance.setStatus("CHECKED_IN");

        attendanceRepository.save(attendance);

        ticket.setStatus("USED");

        ticketRepository.save(ticket);

        return "Check-in Successful";
    }

    // View all attendance
    @GetMapping
    public List<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }

    // View attendance for an event
    @GetMapping("/event/{eventId}")
    public List<Attendance> getEventAttendance(
            @PathVariable Long eventId) {

        return attendanceRepository.findByEventId(eventId);
    }
}