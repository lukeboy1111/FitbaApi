package com.lukeyboy1111.fitba.apis.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lukeyboy1111.fitba.apis.model.FitbaTeam;

/**
 * 
 */
public interface TeamRepository extends MongoRepository<FitbaTeam, String>
{
     
}
