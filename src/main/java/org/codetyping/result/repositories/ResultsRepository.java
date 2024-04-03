package org.codetyping.result.repositories;

import org.codetyping.result.models.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultsRepository extends MongoRepository<Result, String> {
    List<Result> findResultsByUserIDOrderByEndTimeDesc(String userID);
}