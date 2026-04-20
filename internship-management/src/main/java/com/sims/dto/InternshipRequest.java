package com.sims.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class InternshipRequest {
    private String companyName;
    private String role;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
}