package com.example.Springbootproject.repository;

import com.example.Springbootproject.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository; // <-- add this

import java.util.List;

@Repository
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("email").exists(true),
                        Criteria.where("sentimentAnalysis").is(true)
                )
        );
        return mongoTemplate.find(query, User.class);
    }
}
