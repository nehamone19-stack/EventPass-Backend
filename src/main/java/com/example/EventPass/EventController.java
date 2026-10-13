package com.example.EventPass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    // =========================
    // Create Event
    // =========================

    @PostMapping
    public Events addEvent(@RequestBody Events event) {
        return eventRepository.save(event);
    }

    // =========================
    // Get All Events
    // =========================

    @GetMapping
    public List<Events> getEvents() {
        return eventRepository.findAll();
    }

    // =========================
    // Get Event By ID
    // =========================

    @GetMapping("/{id}")
    public Events getEventById(@PathVariable Long id) {

        return eventRepository
                .findById(id)
                .orElse(null);
    }

    // =========================
    // Update Event
    // =========================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        Events existingEvent = eventRepository.findById(id).orElse(null);

        if (existingEvent == null) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        String userName = (String) request.get("userName");

        String userRole = (String) request.get("userRole");

        // =========================
        // ADMIN
        // =========================

        if ("ADMIN".equals(userRole)) {

            existingEvent.setName(
                    (String) request.get("name"));

            existingEvent.setDescription(
                    (String) request.get("description"));

            existingEvent.setVenue(
                    (String) request.get("venue"));

            existingEvent.setDate(
                    (String) request.get("date"));

            existingEvent.setTime(
                    (String) request.get("time"));

            Number capacity = (Number) request.get("capacity");

            existingEvent.setCapacity(
                    capacity.intValue());

            return ResponseEntity.ok(
                    eventRepository.save(existingEvent));
        }

        // =========================
        // ORGANIZER
        // =========================

        if ("ORGANIZER".equals(userRole)) {

            // Check event ownership
            if (!existingEvent
                    .getOrganizer()
                    .equals(userName)) {

                return ResponseEntity
                        .status(403)
                        .body(
                                "You can only edit your own events");
            }

            existingEvent.setName(
                    (String) request.get("name"));

            existingEvent.setDescription(
                    (String) request.get("description"));

            existingEvent.setVenue(
                    (String) request.get("venue"));

            existingEvent.setDate(
                    (String) request.get("date"));

            existingEvent.setTime(
                    (String) request.get("time"));

            Number capacity = (Number) request.get("capacity");

            existingEvent.setCapacity(
                    capacity.intValue());

            return ResponseEntity.ok(
                    eventRepository.save(existingEvent));
        }

        // =========================
        // NOT ALLOWED
        // =========================

        return ResponseEntity
                .status(403)
                .body(
                        "You are not allowed to edit events");
    }

    // =========================
    // Delete Event
    // =========================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Events existingEvent = eventRepository.findById(id).orElse(null);

        if (existingEvent == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        String userName = request.get("userName");

        String userRole = request.get("userRole");

        // =========================
        // ADMIN
        // =========================

        if ("ADMIN".equals(userRole)) {

            eventRepository.deleteById(id);

            return ResponseEntity.ok(
                    "Event deleted successfully");
        }

        // =========================
        // ORGANIZER
        // =========================

        if ("ORGANIZER".equals(userRole)) {

            if (!existingEvent
                    .getOrganizer()
                    .equals(userName)) {

                return ResponseEntity
                        .status(403)
                        .body(
                                "You can only delete your own events");
            }

            eventRepository.deleteById(id);

            return ResponseEntity.ok(
                    "Event deleted successfully");
        }

        // =========================
        // NOT ALLOWED
        // =========================

        return ResponseEntity
                .status(403)
                .body(
                        "You are not allowed to delete events");
    }
}