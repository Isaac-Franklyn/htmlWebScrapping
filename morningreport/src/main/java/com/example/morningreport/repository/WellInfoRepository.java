package com.example.morningreport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.morningreport.model.WellInfo;

public interface WellInfoRepository extends MongoRepository<WellInfo, String> {
}