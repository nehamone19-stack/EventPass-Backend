package com.example.EventPass;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ticketCode;
    private String attendeeName;
    private String attendeeEmail;
    private Long eventId;
    private String eventName;
    private String status;

    public Tickets() {
    }

    public Tickets(String ticketCode, String attendeeName,
            String attendeeEmail, Long eventId,
            String eventName, String status) {

        this.ticketCode = ticketCode;
        this.attendeeName = attendeeName;
        this.attendeeEmail = attendeeEmail;
        this.eventId = eventId;
        this.eventName = eventName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeEmail() {
        return attendeeEmail;
    }

    public void setAttendeeEmail(String attendeeEmail) {
        this.attendeeEmail = attendeeEmail;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}