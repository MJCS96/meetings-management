package com.example.hrmeetings.controller;

import com.example.hrmeetings.model.Employee;
import com.example.hrmeetings.model.Meeting;
import com.example.hrmeetings.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @GetMapping
    public ResponseEntity<List<Meeting>> getAllMeetings() {
        return new ResponseEntity<>(meetingService.getAllMeetings(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable("id") Long id) {
        Meeting meeting = meetingService.getMeetingById(id);
        if (meeting != null) {
            return new ResponseEntity<>(meeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return new ResponseEntity<>(meetingService.createMeeting(meeting), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Meeting> updateMeeting(@PathVariable("id") Long id, @RequestBody Meeting meetingDetails) {
        Meeting updatedMeeting = meetingService.updateMeeting(id, meetingDetails);
        if (updatedMeeting != null) {
            return new ResponseEntity<>(updatedMeeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMeeting(@PathVariable("id") Long id) {
        meetingService.deleteMeeting(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Meeting>> searchMeetings(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "isFinalized", required = false) Boolean isFinalized) {

        List<Meeting> meetings = meetingService.searchMeetings(title, startDate, endDate, isFinalized);
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    @PostMapping("/{meetingId}/participants")
    public ResponseEntity<Meeting> addParticipant(@PathVariable Long meetingId, @RequestBody Employee employee) {
        Meeting meeting = meetingService.addParticipantToMeeting(meetingId, employee);
        if (meeting != null) {
            return new ResponseEntity<>(meeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{meetingId}/participants")
    public ResponseEntity<Meeting> removeParticipant(@PathVariable Long meetingId, @RequestBody Employee employee) {
        Meeting meeting = meetingService.removeParticipantFromMeeting(meetingId, employee);
        if (meeting != null) {
            return new ResponseEntity<>(meeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/finalize")
    public ResponseEntity<?> finalizeMeeting(@PathVariable("id") Long id) {
        try {
            Meeting finalizedMeeting = meetingService.finalizeMeeting(id);
            if (finalizedMeeting != null) {
                return new ResponseEntity<>(finalizedMeeting, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
