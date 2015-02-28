package org.td.service;

import java.util.UUID;
import org.td.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Service for initializing MongoDB with sample data using {@link MongoTemplate}
 */
public class InitMongoService {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	//Init mongo db for sample data
	public void init() {
		// Drop existing collections
		mongoTemplate.dropCollection("user");

		// Create new records
		User tmp =null;
		for (int i=1;i<=10;++i){
			tmp  = new User();
			tmp.setId(UUID.randomUUID().toString());
			tmp.setFirstName("Ad_" + i);
			tmp.setLastName("Soyad_" + i);
			
			// Insert to db
			mongoTemplate.insert(tmp, "user");
		}
	}
}
