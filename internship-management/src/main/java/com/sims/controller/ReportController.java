package com.sims.controller;

import com.sims.dto.ReviewRequest;
import com.sims.entity.*;
import com.sims.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/progress")
    public ResponseEntity<ProgressReport> submitProgress(
            @AuthenticationPrincipal User user,
            @RequestParam Long internshipId,
            @RequestParam String title,
            @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(reportService.submitProgress(user, internshipId, title, file));
    }

    @PostMapping("/final")
    public ResponseEntity<FinalReport> submitFinal(
            @AuthenticationPrincipal User user,
            @RequestParam Long internshipId,
            @RequestParam String title,
            @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(reportService.submitFinal(user, internshipId, title, file));
    }

    @GetMapping("/progress/my")
    public ResponseEntity<List<ProgressReport>> myProgress(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getProgressByStudent(user));
    }

    @GetMapping("/final/my")
    public ResponseEntity<List<FinalReport>> myFinal(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getFinalByStudent(user));
    }

    @GetMapping("/progress/internship/{id}")
    public ResponseEntity<List<ProgressReport>> progressByInternship(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getProgressByInternship(id));
    }

    @PatchMapping("/progress/{id}/review")
    public ResponseEntity<ProgressReport> reviewProgress(
            @PathVariable Long id,
            @AuthenticationPrincipal User reviewer,
            @RequestBody ReviewRequest req) {
        return ResponseEntity.ok(reportService.reviewProgress(id, reviewer, req));
    }

    @PatchMapping("/final/{id}/review")
    public ResponseEntity<FinalReport> reviewFinal(
            @PathVariable Long id,
            @AuthenticationPrincipal User reviewer,
            @RequestBody ReviewRequest req) {
        return ResponseEntity.ok(reportService.reviewFinal(id, reviewer, req));
    }

    @GetMapping("/progress/all")
    public ResponseEntity<List<ProgressReport>> allProgress() {
        return ResponseEntity.ok(reportService.getAllProgress());
    }

    @GetMapping("/final/all")
    public ResponseEntity<List<FinalReport>> allFinal() {
        return ResponseEntity.ok(reportService.getAllFinal());
    }
}