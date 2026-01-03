package com.example.ai_resume_scanner.controller;

import com.example.ai_resume_scanner.model.MatchResult;
import com.example.ai_resume_scanner.service.AiSuggestionService;
import com.example.ai_resume_scanner.service.KeywordMatchService;
import com.example.ai_resume_scanner.service.ResumeParserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ResumeController {
    
    private final KeywordMatchService keywordMatchService;
    private final ResumeParserService resumeParserService;
    private final AiSuggestionService aiSuggestionService;

    public ResumeController(KeywordMatchService keywordMatchService, ResumeParserService resumeParserService,AiSuggestionService aiSuggestionService)
    {
        this.keywordMatchService = keywordMatchService;
        this.resumeParserService = resumeParserService;
        this.aiSuggestionService = aiSuggestionService;
    }

    @PostMapping("/match")
    public MatchResult matchResume(@RequestBody Map<String,String> payload)
    {
        String resumeText = payload.get("resumeText");
        String jobDescription = payload.get("jobDescription");

        Map<String,Object> analysis = keywordMatchService.analyzeResume(resumeText, jobDescription);

        double matchPercentage = (double) analysis.get("matchPercentage");
        List<String> missingSkills = (List<String>) analysis.get("missingSkills");
        List<String> aiSuggestions = aiSuggestionService.generateSuggestions(missingSkills);

        return new MatchResult(matchPercentage,missingSkills, aiSuggestions);
    }
    
    @PostMapping("/match/pdf")
    public MatchResult matchResumepdf(
        @RequestParam("resume") MultipartFile resumeFile,
        @RequestParam("jobDescription") String jobDescription) throws Exception
    {
        String resumeText = resumeParserService.extractTextFromPdf(resumeFile);

        Map<String,Object> analysis = keywordMatchService.analyzeResume(resumeText, jobDescription);

        double matchPercentage = (double) analysis.get("matchPercentage");
        List<String> missingSkills = (List<String>) analysis.get("missingSkills");
        List<String> aiSuggestions = aiSuggestionService.generateSuggestions(missingSkills);

        return new MatchResult(matchPercentage,missingSkills, aiSuggestions);
    }
    
}
