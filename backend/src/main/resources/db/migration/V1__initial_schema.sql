CREATE TABLE IF NOT EXISTS job_postings (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(240) NOT NULL,
    company VARCHAR(240) NOT NULL,
    location VARCHAR(120),
    source_url VARCHAR(1000) NOT NULL UNIQUE,
    source VARCHAR(80),
    description VARCHAR(10000),
    match_score INTEGER NOT NULL DEFAULT 0 CHECK (match_score BETWEEN 0 AND 100),
    status VARCHAR(32) NOT NULL DEFAULT 'DISCOVERED',
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_job_postings_created_at ON job_postings (created_at DESC);
CREATE INDEX IF NOT EXISTS idx_job_postings_status ON job_postings (status);
CREATE INDEX IF NOT EXISTS idx_job_postings_match_score ON job_postings (match_score DESC);
