package com.sims.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "final_reports")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FinalReport {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
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
    @JoinColumn(name = "approved_by")
    @JsonIgnoreProperties({"password", "hibernateLazyInitializer", "handler"})
    private User approvedBy;

    @Column(updatable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
    private LocalDateTime approvedAt;

    public enum Status { SUBMITTED, APPROVED, REJECTED }
}