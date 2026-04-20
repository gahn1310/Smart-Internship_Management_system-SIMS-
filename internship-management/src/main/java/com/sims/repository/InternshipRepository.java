package com.sims.repository;
import com.sims.entity.Internship;
import com.sims.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    List<Internship> findByStudent(User student);
    List<Internship> findByStudentId(Long studentId);
    List<Internship> findByStatus(Internship.Status status);

    @Query("SELECT i FROM Internship i WHERE i.student.id = :studentId")
    List<Internship> findByStudentIdQuery(@Param("studentId") Long studentId);
}