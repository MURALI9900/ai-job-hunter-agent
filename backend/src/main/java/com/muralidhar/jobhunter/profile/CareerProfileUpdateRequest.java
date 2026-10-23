package com.muralidhar.jobhunter.profile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record CareerProfileUpdateRequest(
        @Size(max = 160) String displayName,
        @Size(max = 2000) String targetTitles,
        @Size(max = 4000) String skills,
        @Size(max = 1000) String locations,
        @Min(0) @Max(600) Integer experienceMonths,
        @Min(0) @Max(100) Integer minimumMatchScore
) {
}
