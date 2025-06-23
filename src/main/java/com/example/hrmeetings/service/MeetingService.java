package com.example.hrmeetings.service;

import com.example.hrmeetings.model.Employee;
import com.example.hrmeetings.model.Meeting;
import com.example.hrmeetings.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    public Meeting getMeetingById(Long id) {
        return meetingRepository.findById(id).orElse(null);
    }

    public Meeting createMeeting(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    public Meeting updateMeeting(Long id, Meeting meetingDetails) {
        Meeting meeting = meetingRepository.findById(id).orElse(null);
        if (meeting != null) {
            meeting.setTitle(meetingDetails.getTitle());
            meeting.setStartTime(meetingDetails.getStartTime());
            meeting.setEndTime(meetingDetails.getEndTime());
            meeting.setFinalized(meetingDetails.isFinalized());
            meeting.setParticipants(meetingDetails.getParticipants());
            return meetingRepository.save(meeting);
        }
        return null;
    }

    public void deleteMeeting(Long id) {
        meetingRepository.deleteById(id);
    }

    public List<Meeting> searchMeetings(String title, LocalDateTime startDate, LocalDateTime endDate, Boolean isFinalized) {
        return meetingRepository.searchMeetings(title, startDate, endDate, isFinalized);
    }

    @Transactional
    public Meeting addParticipantToMeeting(Long meetingId, Employee employee) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if (meeting != null) {
            meeting.addParticipant(employee);
            return meetingRepository.save(meeting);
        }
        return null;
    }

    @Transactional
    public Meeting removeParticipantFromMeeting(Long meetingId, Employee employee) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if (meeting != null) {
            meeting.removeParticipant(employee);
            return meetingRepository.save(meeting);
        }
        return null;
    }

    public Meeting finalizeMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId).orElse(null);
        if (meeting != null) {
            LocalDateTime startTime = meeting.getStartTime();
            LocalDateTime endTime = meeting.getEndTime();

            // Check for overlapping finalized meetings
            List<Meeting> overlappingMeetings = meetingRepository.findAll().stream()
                    .filter(m -> m.isFinalized() &&
                            m.getId() != meetingId &&
                            ((m.getStartTime().isBefore(endTime) && m.getEndTime().isAfter(startTime)) ||
                                    (m.getStartTime().isEqual(endTime)) ||
                                    (m.getEndTime().isEqual(startTime))))
                    .toList();

            if (!overlappingMeetings.isEmpty()) {
                throw new IllegalStateException("Cannot finalize this meeting due to overlapping finalized meetings.");
            }

            meeting.setFinalized(true);
            return meetingRepository.save(meeting);
        }
        return null;
    }
}
