package com.greenpath.emmision_service.repository;

import com.greenpath.emmision_service.model.EmissionRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmissionRepository extends MongoRepository<EmissionRecord, String> {
}
