package com.example.morningreport.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.morningreport.model.MudData;

public interface MudDataRepository extends MongoRepository<MudData, String>{

}
