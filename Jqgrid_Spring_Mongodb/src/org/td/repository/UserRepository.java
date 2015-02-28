package org.td.repository;

/*
 * This class makes the magic for mongodb crud management
 */

import org.td.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findById(String id);
	
}

