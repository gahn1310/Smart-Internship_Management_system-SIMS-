package com.sims.service;
import com.sims.dto.ReviewRequest;
import com.sims.entity.*;
import com.sims.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternshipService {
    private final InternshipRepository internshipRepo;
    private final ActivityLogService logService;

    public Internship register(User student, com.sims.dto.InternshipRequest req) {
        Internship internship = Internship.builder()
            .student(student)
            .companyName(req.getCompanyName())
            .role(req.getRole())
            .location(req.getLocation())
            .startDate(req.getStartDate())
            .endDate(req.getEndDate())
            .status(Internship.Status.PENDING)
            .build();
        internshipRepo.save(internship);
        logService.log(student, "REGISTER_INTERNSHIP",
            "Registered internship at " + req.getCompanyName(), "Internship", internship.getId());
        return internship;
    }

    public Internship review(Long id, User reviewer, ReviewRequest req) {
        Internship internship = internshipRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Internship not found"));
        internship.setStatus(Internship.Status.valueOf(req.getStatus()));
        internship.setApprovedBy(reviewer);
        internship.setRejectionReason(req.getFeedback());
        internship.setUpdatedAt(LocalDateTime.now());
        internshipRepo.save(internship);
        logService.log(reviewer, req.getStatus() + "_INTERNSHIP",
            req.getStatus() + " internship id=" + id, "Internship", id);
        return internship;
    }

    public List<Internship> getByStudent(User student) {
        return internshipRepo.findByStudentIdQuery(student.getId());
    }

    public List<Internship> getAll() { return internshipRepo.findAll(); }

    public Internship getById(Long id) {
        return internshipRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Not found"));
    }
}