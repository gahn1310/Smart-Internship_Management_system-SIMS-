package com.sims.service;

import com.sims.dto.ReviewRequest;
import com.sims.entity.*;
import com.sims.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ProgressReportRepository progressRepo;
    private final FinalReportRepository finalRepo;
    private final InternshipRepository internshipRepo;
    private final ActivityLogService logService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private String saveFile(MultipartFile file) throws IOException {
        Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(dir);
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), dir.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
        return filename;
    }

    public ProgressReport submitProgress(User student, Long internshipId, String title, MultipartFile file)
            throws IOException {
        Internship internship = internshipRepo.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        String filename = saveFile(file);
        ProgressReport report = ProgressReport.builder()
                .internship(internship).student(student)
                .title(title).fileName(filename).filePath(uploadDir + "/" + filename)
                .status(ProgressReport.Status.SUBMITTED)
                .build();
        progressRepo.save(report);
        logService.log(student, "SUBMIT_PROGRESS_REPORT", "Submitted progress report: " + title,
                "ProgressReport", report.getId());
        return report;
    }

    public FinalReport submitFinal(User student, Long internshipId, String title, MultipartFile file)
            throws IOException {
        Internship internship = internshipRepo.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        String filename = saveFile(file);
        FinalReport report = FinalReport.builder()
                .internship(internship).student(student)
                .title(title).fileName(filename).filePath(uploadDir + "/" + filename)
                .status(FinalReport.Status.SUBMITTED)
                .build();
        finalRepo.save(report);
        logService.log(student, "SUBMIT_FINAL_REPORT", "Submitted final report: " + title,
                "FinalReport", report.getId());
        return report;
    }

    public ProgressReport reviewProgress(Long id, User reviewer, ReviewRequest req) {
        ProgressReport report = progressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(ProgressReport.Status.valueOf(req.getStatus()));
        report.setFeedback(req.getFeedback());
        report.setReviewedBy(reviewer);
        report.setReviewedAt(LocalDateTime.now());
        progressRepo.save(report);
        logService.log(reviewer, "REVIEW_PROGRESS_REPORT", req.getStatus() + " report id=" + id,
                "ProgressReport", id);
        return report;
    }

    public FinalReport reviewFinal(Long id, User reviewer, ReviewRequest req) {
        FinalReport report = finalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setStatus(FinalReport.Status.valueOf(req.getStatus()));
        report.setFeedback(req.getFeedback());
        report.setApprovedBy(reviewer);
        report.setApprovedAt(LocalDateTime.now());
        finalRepo.save(report);
        logService.log(reviewer, "REVIEW_FINAL_REPORT", req.getStatus() + " final report id=" + id,
                "FinalReport", id);
        return report;
    }

    public List<ProgressReport> getProgressByStudent(User student) {
        return progressRepo.findByStudent(student);
    }

    public List<ProgressReport> getProgressByInternship(Long internshipId) {
        return progressRepo.findByInternship_Id(internshipId);
    }

    public List<FinalReport> getFinalByStudent(User student) {
        return finalRepo.findByStudent(student);
    }

    public List<ProgressReport> getAllProgress() { return progressRepo.findAll(); }
    public List<FinalReport> getAllFinal() { return finalRepo.findAll(); }
}