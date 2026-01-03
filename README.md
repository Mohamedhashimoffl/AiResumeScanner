# AiResumeScanner

A full-stack AI-powered resume scanner that analyzes resume PDFs against job descriptions,
calculates match percentage, identifies missing skills, and provides AI-based improvement suggestions.

## Features
- PDF resume upload
- Resume–Job Description matching
- Skill gap analysis
- AI-powered resume suggestions
- Spring Boot REST APIs
- Simple HTML + JavaScript frontend

## Tech Stack
- Java 17
- Spring Boot
- Apache PDFBox
- OpenRouter (LLM integration)
- HTML, CSS, JavaScript

## How It Works
1. Upload resume PDF
2. Paste job description
3. System extracts resume text
4. Rule-based NLP calculates match
5. AI generates improvement suggestions

## Run Locally
```bash
mvn spring-boot:run
