package com.example.ai_resume_scanner.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeywordMatchService {

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
            "and", "or", "the", "a", "an", "with", "for", "to", "in",
            "on", "of", "is", "are", "as", "by", "from", "that",
            "this", "will", "be", "looking", "experience", "using"
    ));

    private String normalize(String word) {
        word = word.toLowerCase().replaceAll("[^a-z0-9]", "");

        if (word.endsWith("s") && word.length() > 3) {
            word = word.substring(0, word.length() - 1);
        }

        return word;
    }

    public Map<String, Object> analyzeResume(String resumeText, String jobDescription) 
    {

        Set<String> resumeWords = new HashSet<>();
        Set<String> jobWords = new HashSet<>();

        for (String word : resumeText.split("\\s+")) {
            String normalized = normalize(word);
            if (!STOPWORDS.contains(normalized) && !normalized.isBlank()) {
                resumeWords.add(normalized);
            }
        }

        for (String word : jobDescription.split("\\s+")) {
            String normalized = normalize(word);
            if (!STOPWORDS.contains(normalized) && !normalized.isBlank()) {
                jobWords.add(normalized);
            }
        }

        List<String> missingSkills = new ArrayList<>();
        int matched = 0;

        for (String jobWord : jobWords) {
            if (resumeWords.contains(jobWord)) {
                matched++;
            } else {
                missingSkills.add(jobWord);
            }
        }

        double matchPercentage =
                jobWords.isEmpty() ? 0 : (matched * 100.0) / jobWords.size();

        matchPercentage = Math.round(matchPercentage * 100.0) / 100.0;

        Map<String, Object> result = new HashMap<>();
        result.put("matchPercentage", matchPercentage);
        result.put("missingSkills", missingSkills);

        return result;
    }

    // public Map<String,Object> analyzeResume(String resumeText, String jobDescription)
    // {
    //     Set<String> resumeWords = new HashSet<>(Arrays.asList(resumeText.toLowerCase().split("\\s+")));
    //     Set<String> jobWords = new HashSet<>(Arrays.asList(jobDescription.toLowerCase().split("\\s+")));

    //     List<String> missingSkills = new ArrayList<>();
    //     int matchedWords = 0;

    //     for(String word : jobWords)
    //     {
    //         if(resumeWords.contains(word))
    //         {
    //             matchedWords++;
    //         }
    //         else{
    //             missingSkills.add(word);
    //         }
    //     }

    //     double matchPercentage = jobWords.isEmpty() ? 0 : (matchedWords*100)/jobWords.size();

    //     Map<String,Object> result = new HashMap<>();
    //     result.put("matchPercentage", matchPercentage);
    //     result.put("missingSkills", missingSkills);

    //     return result;
    // }
}
