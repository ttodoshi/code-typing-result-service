package org.speedtyping.result.repositories;

import org.speedtyping.result.models.Result;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResultsRepository extends MongoRepository<Result, String> {
    List<Result> findResultsByUserIDOrderByEndTime(String userID);
}