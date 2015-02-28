package org.td.service;

/*
 * Business need programmed in this class for now only basic required field check
 */

import java.util.UUID;

import org.td.domain.User;
import org.td.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;	
	
	public Page<User> readAll(Pageable page) {
		return userRepository.findAll(page);
	}
	
	private Boolean validateUser(User user)
	{
		Boolean validationResult = true;
		
		if (user.getFirstName() == null || user.getFirstName().isEmpty())
			validationResult=false;
		
		if (user.getLastName() == null || user.getLastName().isEmpty())
			validationResult=false;
		
		//TODO:Regex control
		if (user.getTelephone() != null && user.getTelephone().isEmpty())
			validationResult=false;
		
		return validationResult;
	}
	
	public User create(User user) {
		
		if (!validateUser(user)) {
			return null;
		}
		
		user.setId(UUID.randomUUID().toString());
		
		return userRepository.save(user);
	}	

	public User update(User user) {
		User existingUser = userRepository.findById(user.getId());
		
		if (existingUser == null) {
			return null;
		}
		
		if (!validateUser(existingUser)) {
			return null;
		}
		
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setTelephone(user.getTelephone());
		
		return userRepository.save(existingUser);
	}
	
	public Boolean delete(String userId) {
		User existingUser = userRepository.findById(userId);
		
		if (existingUser == null) {
			return false;
		}
		
		userRepository.delete(existingUser);
		return true;
	}
}

