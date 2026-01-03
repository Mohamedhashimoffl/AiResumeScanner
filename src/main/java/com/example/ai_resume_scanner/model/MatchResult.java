package com.example.ai_resume_scanner.model;

import java.util.List;
public class MatchResult {
    
    private double matchPercentage;
    private List<String> missingSkills;
    private List<String> aiSuggestions;

    public MatchResult(double matchPercentage,List<String> missingSkills,List<String> aiSuggestions)
    {
        this.matchPercentage = matchPercentage;
        this.missingSkills = missingSkills;
        this.aiSuggestions = aiSuggestions;
    }
    public List<String> getAiSuggestions()
    {
        return aiSuggestions;
    }

    public void setAiSuggestions(List<String> aiSuggestions)
    {
        this.aiSuggestions = aiSuggestions;
    }

    public double getMatchPercentage()
    {
        return matchPercentage;
    }

    public void setMatchPercentage(double matchPercentage)
    {
        this.matchPercentage=matchPercentage;
    }

    public List<String> getMissingSkills()
    {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills)
    {
        this.missingSkills = missingSkills;
    }
}
