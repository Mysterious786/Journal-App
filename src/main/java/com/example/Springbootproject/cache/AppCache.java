package com.example.Springbootproject.cache;


import com.example.Springbootproject.entity.ConfigJournalAppEntity;
import com.example.Springbootproject.repository.ConfigJournalApp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String, String> APP_CACHE; // in memory cache
    @Autowired
    private ConfigJournalApp configJournalApp;

    @PostConstruct
    public void init()
    {
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppEntity> configJournalAppEntities = configJournalApp.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : configJournalAppEntities)
        {
            APP_CACHE.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }

    }
}
