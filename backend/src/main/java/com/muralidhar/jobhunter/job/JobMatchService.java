package com.muralidhar.jobhunter.job;

import com.muralidhar.jobhunter.profile.CareerProfile;
import com.muralidhar.jobhunter.profile.CareerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class JobMatchService {
    private final JobPostingRepository jobs;
    private final CareerProfileRepository profiles;

    public JobMatchService(JobPostingRepository jobs, CareerProfileRepository profiles) {
        this.jobs = jobs;
        this.profiles = profiles;
    }

    @Transactional
    public JobPosting score(Long jobId) {
        JobPosting job = jobs.findById(jobId)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Job not found"));
        CareerProfile profile = profiles.findById(1L)
                .orElseGet(() -> profiles.save(CareerProfile.defaults()));

        String title = normalize(job.getTitle());
        String searchable = normalize(job.getTitle() + " " + safe(job.getDescription()));
        String location = normalize(safe(job.getLocation()));
        List<String> titles = terms(profile.getTargetTitles());
        List<String> skills = terms(profile.getSkills());
        List<String> locations = terms(profile.getLocations());

        int titleScore = containsAny(title, titles) ? 35 : 0;
        int matchedSkills = (int) skills.stream().filter(skill -> searchable.contains(normalize(skill))).count();
        int skillScore = skills.isEmpty() ? 0 : Math.min(50, Math.round(50f * matchedSkills / Math.min(skills.size(), 5)));
        int locationScore = locations.isEmpty() || containsAny(location, locations) ? 15 : 0;
        int score = Math.min(100, titleScore + skillScore + locationScore);
        job.setMatchScore(score);
        return jobs.save(job);
    }

    private List<String> terms(String raw) {
        if (raw == null || raw.isBlank()) return List.of();
        return Arrays.stream(raw.split("[,;\\n]"))
                .map(String::trim).filter(term -> !term.isBlank()).toList();
    }

    private boolean containsAny(String value, List<String> terms) {
        return terms.stream().map(this::normalize).filter(term -> !term.isBlank()).anyMatch(value::contains);
    }

    private String normalize(String value) {
        return safe(value).toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9+#. ]", " ").replaceAll("\\s+", " ").trim();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
