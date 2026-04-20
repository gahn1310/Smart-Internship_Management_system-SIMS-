package com.sims.repository;
import com.sims.entity.FinalReport;
import com.sims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface FinalReportRepository extends JpaRepository<FinalReport, Long> {
    List<FinalReport> findByStudent(User student);
    java.util.Optional<FinalReport> findByInternship_Id(Long internshipId);
}