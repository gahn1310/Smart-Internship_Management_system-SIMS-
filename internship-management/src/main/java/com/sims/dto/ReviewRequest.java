package com.sims.dto;
import lombok.Data;
@Data
public class ReviewRequest {
    private String status;   // APPROVED or REJECTED
    private String feedback;
}