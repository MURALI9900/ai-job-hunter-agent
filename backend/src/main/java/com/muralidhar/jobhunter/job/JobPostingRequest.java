package com.muralidhar.jobhunter.job;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobPostingRequest(
        @NotBlank @Size(max = 240) String title,
        @NotBlank @Size(max = 240) String company,
        @Size(max = 120) String location,
        @NotBlank @Size(max = 1000) String sourceUrl,
        @Size(max = 80) String source,
        @Size(max = 10000) String description,
        @Min(0) @Max(100) Integer matchScore
) {
}
