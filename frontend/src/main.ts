import { Component, OnInit, signal } from '@angular/core';
import { bootstrapApplication } from '@angular/platform-browser';
import { HttpClient, provideHttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Job {
  id: number;
  title: string;
  company: string;
  location?: string;
  sourceUrl: string;
  source?: string;
  matchScore: number;
  status: string;
  createdAt: string;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  template: `
    <main class="shell">
      <aside class="sidebar">
        <div class="brand"><span class="brand-mark">J</span><span>Job Hunter<small>PERSONAL AI AGENT</small></span></div>
        <div class="nav-label">WORKSPACE</div>
        <a class="nav-item active">▦ <span>Overview</span></a>
        <a class="nav-item">⌕ <span>Discover jobs</span></a>
        <a class="nav-item">✉ <span>Recruiter outreach</span></a>
        <a class="nav-item">▤ <span>Applications</span></a>
        <a class="nav-item">⚙ <span>Settings</span></a>
        <div class="sidebar-bottom"><div class="status-dot"></div><div><strong>Local workspace</strong><small>Private by default</small></div></div>
      </aside>

      <section class="content">
        <header class="topbar">
          <div><div class="eyebrow">YOUR CAREER WORKSPACE</div><h1>Job search overview</h1><p>Discover better opportunities. Keep every application organized.</p></div>
          <button class="primary" (click)="refresh()">↻ &nbsp; Refresh jobs</button>
        </header>

        <div class="notice"><span>✦</span><div><strong>Welcome to your job-search command center</strong><p>Connect your sources and profile in the next setup steps. Nothing is emailed or submitted automatically in this starter version.</p></div></div>

        <section class="metrics">
          <article class="metric"><div class="metric-label">Jobs tracked <span>↗</span></div><div class="metric-value">{{ jobs().length }}</div><div class="metric-foot">Saved in your workspace</div></article>
          <article class="metric"><div class="metric-label">Strong matches <span>◎</span></div><div class="metric-value">{{ strongMatches() }}</div><div class="metric-foot">Match score of 70% or higher</div></article>
          <article class="metric"><div class="metric-label">Outreach sent <span>✉</span></div><div class="metric-value">{{ outreachSent() }}</div><div class="metric-foot">Tracked email activity</div></article>
          <article class="metric"><div class="metric-label">Applications <span>▣</span></div><div class="metric-value">{{ applied() }}</div><div class="metric-foot">Marked as applied</div></article>
        </section>

        <section class="panel">
          <div class="panel-heading"><div><h2>Recent opportunities</h2><p>Jobs collected and tracked by your agent</p></div><span class="pill">{{ jobs().length }} jobs</span></div>
          <div *ngIf="error()" class="error">{{ error() }} — Start the backend and PostgreSQL to load saved jobs.</div>
          <div *ngIf="loading()" class="empty">Loading jobs…</div>
          <div *ngIf="!loading() && !error() && jobs().length === 0" class="empty-state">
            <div class="empty-icon">⌕</div><h3>Your opportunity list starts here</h3>
            <p>No jobs tracked yet. Once the API is running, you can add and evaluate job listings.</p>
            <button class="secondary" (click)="refresh()">Check connection</button>
          </div>
          <div class="job-list" *ngIf="jobs().length > 0">
            <article class="job-row" *ngFor="let job of jobs()">
              <div class="company-mark">{{ job.company.charAt(0).toUpperCase() }}</div>
              <div class="job-main"><strong>{{ job.title }}</strong><span>{{ job.company }} · {{ job.location || 'Location not specified' }}</span><small>{{ job.source || 'Manual entry' }} · {{ job.status.replace('_', ' ') }}</small></div>
              <div class="match"><strong>{{ job.matchScore || 0 }}%</strong><span>match</span></div>
              <a class="job-link" [href]="job.sourceUrl" target="_blank" rel="noopener noreferrer">View ↗</a>
            </article>
          </div>
        </section>

        <section class="bottom-grid">
          <article class="panel setup-panel"><div class="panel-heading"><div><h2>Setup checklist</h2><p>Build a safe, useful automation flow</p></div></div>
            <div class="check-row"><span class="check done">✓</span><div><strong>Project workspace created</strong><small>Spring Boot · Angular · PostgreSQL</small></div><span class="step-tag complete">READY</span></div>
            <div class="check-row"><span class="check">2</span><div><strong>Career profile and match rules</strong><small>Skills, experience, locations, salary</small></div><span class="step-tag">NEXT</span></div>
            <div class="check-row"><span class="check">3</span><div><strong>Job sources and alerts</strong><small>Permitted sources and duplicate checks</small></div><span class="step-tag">PLANNED</span></div>
            <div class="check-row"><span class="check">4</span><div><strong>Gmail OAuth connection</strong><small>Secure authorization, never share passwords</small></div><span class="step-tag">PLANNED</span></div>
          </article>
          <article class="panel profile-panel"><div class="panel-heading"><div><h2>Target profile</h2><p>Initial suggested preferences</p></div></div>
            <div class="profile-tags"><span>Full Stack Developer</span><span>Java</span><span>Spring Boot</span><span>Microservices</span><span>Angular</span><span>SQL</span></div>
            <div class="profile-line"><span>Experience</span><strong>4.5+ years</strong></div>
            <div class="profile-line"><span>Locations</span><strong>Bengaluru · Hyderabad · Remote</strong></div>
            <div class="profile-line"><span>Automation mode</span><strong class="approval">Approval required</strong></div>
          </article>
        </section>
        <footer>AI Job Hunter Agent <span>·</span> Local-first starter <span>·</span> Built for safer job-search automation</footer>
      </section>
    </main>
  `,
})
class AppComponent implements OnInit {
  readonly jobs = signal<Job[]>([]);
  readonly loading = signal(false);
  readonly error = signal('');
  readonly strongMatches = () => this.jobs().filter(job => (job.matchScore || 0) >= 70).length;
  readonly outreachSent = () => this.jobs().filter(job => job.status === 'OUTREACH_SENT').length;
  readonly applied = () => this.jobs().filter(job => job.status === 'APPLIED').length;

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void { this.refresh(); }

  refresh(): void {
    this.loading.set(true);
    this.error.set('');
    this.http.get<Job[]>('http://localhost:8080/api/jobs').subscribe({
      next: data => { this.jobs.set(data); this.loading.set(false); },
      error: () => { this.error.set('API unavailable'); this.loading.set(false); }
    });
  }
}

bootstrapApplication(AppComponent, { providers: [provideHttpClient()] })
  .catch(error => console.error('Angular bootstrap failed', error));
