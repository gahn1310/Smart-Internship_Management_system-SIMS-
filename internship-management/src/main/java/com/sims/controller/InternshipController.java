package com.sims.controller;

import com.sims.dto.*;
import com.sims.entity.*;
import com.sims.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {
    private final InternshipService internshipService;

    @PostMapping
    public ResponseEntity<Internship> register(
            @AuthenticationPrincipal User user,
            @RequestBody InternshipRequest req) {
        System.out.println("Register internship for user: " + (user != null ? user.getId() + " " + user.getEmail() : "NULL"));
        return ResponseEntity.ok(internshipService.register(user, req));
    }

    @GetMapping("/my")
    public ResponseEntity<List<Internship>> myInternships(@AuthenticationPrincipal User user) {
        System.out.println("Get my internships for user: " + (user != null ? user.getId() + " " + user.getEmail() : "NULL"));
        List<Internship> list = internshipService.getByStudent(user);
        System.out.println("Found: " + list.size() + " internships");
        return ResponseEntity.ok(list);
    }

    @GetMapping
    public ResponseEntity<List<Internship>> allInternships() {
        return ResponseEntity.ok(internshipService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(internshipService.getById(id));
    }

    @PatchMapping("/{id}/review")
    public ResponseEntity<Internship> review(
            @PathVariable Long id,
            @AuthenticationPrincipal User reviewer,
            @RequestBody ReviewRequest req) {
        return ResponseEntity.ok(internshipService.review(id, reviewer, req));
    }
}