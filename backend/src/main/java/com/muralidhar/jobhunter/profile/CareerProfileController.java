package com.muralidhar.jobhunter.profile;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class CareerProfileController {
    private static final long DEFAULT_PROFILE_ID = 1L;
    private final CareerProfileRepository repository;

    public CareerProfileController(CareerProfileRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public CareerProfile get() {
        return repository.findById(DEFAULT_PROFILE_ID)
                .orElseGet(() -> repository.save(CareerProfile.defaults()));
    }

    @PutMapping
    public CareerProfile update(@Valid @RequestBody CareerProfileUpdateRequest request) {
        CareerProfile profile = repository.findById(DEFAULT_PROFILE_ID)
                .orElseGet(CareerProfile::defaults);

        // Treat omitted/null fields as "leave unchanged" so partial updates are safe.
        if (request.displayName() != null) profile.setDisplayName(request.displayName());
        if (request.targetTitles() != null) profile.setTargetTitles(request.targetTitles());
        if (request.skills() != null) profile.setSkills(request.skills());
        if (request.locations() != null) profile.setLocations(request.locations());
        if (request.experienceMonths() != null) profile.setExperienceMonths(request.experienceMonths());
        if (request.minimumMatchScore() != null) profile.setMinimumMatchScore(request.minimumMatchScore());

        return repository.save(profile);
    }
}