package org.td.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.td.domain.User;
import org.td.service.UserService;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebContent/WEB-INF/applicationContext.xml")
@Transactional
public class UserServiceTest {

	@Autowired
    private UserService userService;
    
	//User creating and validating test
    @Test
    public void createUser() {
    	User user = new User();
    	user.setFirstName("Ali");
    	user.setLastName("Veli");
    	User createdUser = userService.create(user);
    	Assert.assertEquals(user.getFirstName(), createdUser.getFirstName());
    }

}
