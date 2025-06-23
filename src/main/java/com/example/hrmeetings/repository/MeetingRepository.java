package com.example.hrmeetings.repository;

import com.example.hrmeetings.model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query("SELECT m FROM Meeting m WHERE " +
            "(:title is null or lower(m.title) like lower(concat('%', :title, '%'))) and " +
            "(m.startTime >= :startDate and m.endTime <= :endDate) and " +
            "(:isFinalized is null or m.finalized = :isFinalized)")
    List<Meeting> searchMeetings(
            @Param("title") String title,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("isFinalized") Boolean isFinalized
    );
}
