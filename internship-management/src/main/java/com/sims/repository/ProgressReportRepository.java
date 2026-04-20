package com.sims.repository;
import com.sims.entity.ProgressReport;
import com.sims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {
    List<ProgressReport> findByStudent(User student);
    List<ProgressReport> findByInternship_Id(Long internshipId);
    List<ProgressReport> findAll();
}