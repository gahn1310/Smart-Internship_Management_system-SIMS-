package com.sims.dto;
import lombok.Data;
@Data
public class AuthRequest {
    private String email;
    private String password;
    private String name;
    private String role; // STUDENT, FACULTY, MENTOR, ADMIN
    private String company;
}