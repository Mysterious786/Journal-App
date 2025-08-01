package com.example.Springbootproject.scheduler;

import com.example.Springbootproject.cache.AppCache;
import com.example.Springbootproject.entity.JournalEntry;
import com.example.Springbootproject.enums.Sentiment;
import com.example.Springbootproject.entity.User;
import com.example.Springbootproject.repository.UserRepositoryImpl;
import com.example.Springbootproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUserForSA();
        for (User user : users) {
            List<JournalEntry> recentEntries = user.getJournalEntries().stream()
                    .filter(entry -> entry.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (JournalEntry entry : recentEntries) {
                Sentiment sentiment = entry.getSentiment();
                if (sentiment != null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                String subject = "Sentiment for Previous Week";
                String body = "Sentiment for last 7 days: " + mostFrequentSentiment.name();
                emailService.sendEmail(user.getEmail(), subject, body);
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
