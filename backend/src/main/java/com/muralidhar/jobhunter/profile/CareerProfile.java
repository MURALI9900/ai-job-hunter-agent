package com.muralidhar.jobhunter.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.Instant;

@Entity
@Table(name = "career_profile")
public class CareerProfile {
    @Id
    private Long id = 1L;

    @Size(max = 160)
    @Column(length = 160)
    private String displayName = "";

    @Size(max = 2000)
    @Column(length = 2000)
    private String targetTitles = "Full Stack Developer, Senior Full Stack Developer, Java Developer";

    @Size(max = 4000)
    @Column(length = 4000)
    private String skills = "Java, Spring Boot, Microservices, REST API, Angular, TypeScript, SQL, Oracle, PostgreSQL, Hibernate";

    @Size(max = 1000)
    @Column(length = 1000)
    private String locations = "Bengaluru, Hyderabad, Remote";

    @Min(0)
    @Max(600)
    private Integer experienceMonths = 54;

    @Min(0)
    @Max(1000)
    private Integer minimumMatchScore = 60;

    @Column(nullable = false)
    private Instant updatedAt = Instant.now();

    protected CareerProfile() {
    }

    public static CareerProfile defaults() {
        return new CareerProfile();
    }

    @jakarta.persistence.PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    public Long getId() { return id; }
    public String getDisplayName() { return displayName; }
    public String getTargetTitles() { return targetTitles; }
    public String getSkills() { return skills; }
    public String getLocations() { return locations; }
    public Integer getExperienceMonths() { return experienceMonths; }
    public Integer getMinimumMatchScore() { return minimumMatchScore; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setTargetTitles(String targetTitles) { this.targetTitles = targetTitles; }
    public void setSkills(String skills) { this.skills = skills; }
    public void setLocations(String locations) { this.locations = locations; }
    public void setExperienceMonths(Integer experienceMonths) { this.experienceMonths = experienceMonths; }
    public void setMinimumMatchScore(Integer minimumMatchScore) { this.minimumMatchScore = minimumMatchScore; }
}
