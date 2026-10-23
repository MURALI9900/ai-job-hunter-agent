package com.muralidhar.jobhunter.job;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    Optional<JobPosting> findBySourceUrl(String sourceUrl);
    List<JobPosting> findAllByOrderByCreatedAtDesc();
    List<JobPosting> findByStatusOrderByCreatedAtDesc(JobStatus status);
}
