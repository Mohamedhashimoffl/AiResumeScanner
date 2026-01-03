package com.example.ai_resume_scanner.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class AiSuggestionService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<String> generateSuggestions(List<String> missingSkills) 
    {
        if (missingSkills == null || missingSkills.isEmpty()) {
            return List.of("Skills match looks good!");
        }

        Map<String, Object> body = new HashMap<>();
        // SWITCH TO THIS MODEL: It is officially supported for the /v1/chat endpoint
        body.put("model", "meta-llama/Llama-3.2-1B-Instruct"); 
        
        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "system", "content", "You are a professional career coach."));
        messages.add(Map.of("role", "user", "content", "The candidate is missing these skills: " + String.join(", ", missingSkills) + ". Provide 3 very brief resume improvement tips as a list."));
        
        body.put("messages", messages);
        body.put("max_tokens", 150);
        body.put("stream", false); // Ensure streaming is off for RestTemplate

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            if (response.getBody() != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                String text = message.get("content").toString();
                
                return Arrays.stream(text.split("\\n"))
                            .map(String::trim)
                            .filter(s -> s.length() > 5)
                            .limit(3)
                            .toList();
            }
        } catch (Exception e) {
            return List.of("AI currently busy. Please try again in a few seconds.");
        }
        return List.of("No suggestions found.");
    }
}