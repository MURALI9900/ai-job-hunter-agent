package com.muralidhar.jobhunter.job;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "job_postings", uniqueConstraints = {
        @UniqueConstraint(name = "uk_job_postings_source_url", columnNames = "source_url")
})
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 240)
    @Column(nullable = false, length = 240)
    private String title;

    @NotBlank
    @Size(max = 240)
    @Column(nullable = false, length = 240)
    private String company;

    @Size(max = 120)
    @Column(length = 120)
    private String location;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "source_url", nullable = false, length = 1000)
    private String sourceUrl;

    @Size(max = 80)
    @Column(length = 80)
    private String source;

    @Size(max = 10000)
    @Column(length = 10000)
    private String description;

    @Min(0)
    @Max(100)
    private Integer matchScore = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private JobStatus status = JobStatus.DISCOVERED;

    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    protected JobPosting() {
    }

    public JobPosting(String title, String company, String location, String sourceUrl, String source, String description) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.sourceUrl = sourceUrl;
        this.source = source;
        this.description = description;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getSourceUrl() { return sourceUrl; }
    public String getSource() { return source; }
    public String getDescription() { return description; }
    public Integer getMatchScore() { return matchScore; }
    public JobStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) { this.title = title; }
    public void setCompany(String company) { this.company = company; }
    public void setLocation(String location) { this.location = location; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }
    public void setSource(String source) { this.source = source; }
    public void setDescription(String description) { this.description = description; }
    public void setMatchScore(Integer matchScore) { this.matchScore = matchScore; }
    public void setStatus(JobStatus status) { this.status = status; }
}
