package com.muralidhar.jobhunter.job;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
public class JobMatchController {
    private final JobMatchService matchService;

    public JobMatchController(JobMatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/{id}/score")
    public JobPosting score(@PathVariable Long id) {
        return matchService.score(id);
    }
}
