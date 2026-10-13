package com.example.EventPass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private EventRepository eventRepository;

    // Register for an event
    @PostMapping("/register")
    public Object registerForEvent(@RequestBody Tickets ticket) {

        boolean alreadyRegistered = ticketRepository.existsByAttendeeEmailAndEventId(
                ticket.getAttendeeEmail(),
                ticket.getEventId());

        if (alreadyRegistered) {
            return "You are already registered for this event";
        }

        Events event = eventRepository.findById(ticket.getEventId()).orElse(null);

        if (event == null) {
            return "Event not found";
        }

        String ticketCode = "EVP-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

        ticket.setTicketCode(ticketCode);
        ticket.setEventName(event.getName());
        ticket.setStatus("ACTIVE");

        return ticketRepository.save(ticket);
    }

    // Get all tickets
    @GetMapping
    public List<Tickets> getTickets() {
        return ticketRepository.findAll();
    }

    // Get tickets of one attendee
    @GetMapping("/attendee/{email}")
    public List<Tickets> getAttendeeTickets(
            @PathVariable String email) {

        return ticketRepository.findByAttendeeEmail(email);
    }

    // Get ticket by ID
    @GetMapping("/{id}")
    public Tickets getTicket(@PathVariable Long id) {

        return ticketRepository.findById(id).orElse(null);
    }
}