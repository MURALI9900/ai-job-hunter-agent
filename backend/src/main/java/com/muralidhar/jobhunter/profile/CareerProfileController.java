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
        profile.setDisplayName(request.displayName());
        profile.setTargetTitles(request.targetTitles());
        profile.setSkills(request.skills());
        profile.setLocations(request.locations());
        profile.setExperienceMonths(request.experienceMonths());
        profile.setMinimumMatchScore(request.minimumMatchScore());
        return repository.save(profile);
    }
}
