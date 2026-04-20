package com.sims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progress_reports")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProgressReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "internship_id", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler", "student", "approvedBy"})
    private Internship internship;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private User student;

    private String title;
    private String fileName;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SUBMITTED;

    private String feedback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reviewed_by")
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private User reviewedBy;

    @Column(updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
    private LocalDateTime reviewedAt;

    public enum Status { SUBMITTED, REVIEWED, APPROVED, REJECTED }
}