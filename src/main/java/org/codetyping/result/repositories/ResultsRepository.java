package org.codetyping.result.repositories;

import org.codetyping.result.models.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultsRepository extends MongoRepository<Result, String> {
    Page<Result> findAllByUserID(String userID, Pageable pageable);
}