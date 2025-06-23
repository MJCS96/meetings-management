package com.example.hrmeetings.dto;

import java.time.LocalDateTime;

public class MeetingSearchCriteria {

    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isFinalized;

    public MeetingSearchCriteria() {
    }

    public MeetingSearchCriteria(String title, LocalDateTime startDate, LocalDateTime endDate, Boolean isFinalized) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFinalized = isFinalized;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsFinalized() {
        return isFinalized;
    }

    public void setIsFinalized(Boolean finalized) {
        isFinalized = finalized;
    }
}
