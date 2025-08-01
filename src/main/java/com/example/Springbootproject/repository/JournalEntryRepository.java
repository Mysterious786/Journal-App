package com.example.Springbootproject.repository;

//controller --> services --> repository

import com.example.Springbootproject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {
}
