package com.example.morningreport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.morningreport.model.Operations;

@Repository
public interface OperationsRepository extends MongoRepository<Operations, String>{
	
}
