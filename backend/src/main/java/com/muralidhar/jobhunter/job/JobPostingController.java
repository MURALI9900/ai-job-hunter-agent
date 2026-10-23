package com.muralidhar.jobhunter.job;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobPostingController {
    private final JobPostingRepository repository;

    public JobPostingController(JobPostingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<JobPosting> list(@RequestParam(required = false) JobStatus status) {
        return status == null
                ? repository.findAllByOrderByCreatedAtDesc()
                : repository.findByStatusOrderByCreatedAtDesc(status);
    }

    @GetMapping("/{id}")
    public JobPosting get(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobPosting create(@Valid @RequestBody JobPostingRequest request) {
        repository.findBySourceUrl(request.sourceUrl()).ifPresent(existing -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This job URL is already tracked");
        });

        JobPosting job = new JobPosting(
                request.title(), request.company(), request.location(),
                request.sourceUrl(), request.source(), request.description()
        );
        if (request.matchScore() != null) {
            job.setMatchScore(request.matchScore());
        }
        return repository.save(job);
    }

    @PatchMapping("/{id}/status")
    public JobPosting updateStatus(@PathVariable Long id, @RequestParam JobStatus status) {
        JobPosting job = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        job.setStatus(status);
        return repository.save(job);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found");
        }
        repository.deleteById(id);
    }
}
