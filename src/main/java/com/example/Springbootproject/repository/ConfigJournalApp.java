package com.example.Springbootproject.repository;

//controller --> services --> repository

import com.example.Springbootproject.entity.ConfigJournalAppEntity;
import com.example.Springbootproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// we write here collection pojo
public interface ConfigJournalApp extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
