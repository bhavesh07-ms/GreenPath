package com.bhavesh.route_orchestrator.route.repository;


import com.bhavesh.route_orchestrator.route.entity.CachedPathDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface CachedPathRepository extends MongoRepository<CachedPathDoc, String> {

    Optional<CachedPathDoc> findByCacheKey(String cacheKey);

}

