package org.td.controller;

/*
 * Spring MVC controller for jqgrid ajax operations,returns JSON data to js
 */

import static java.util.Collections.singletonList;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.net.URI;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.td.domain.User;
import org.td.dto.ViewPage;
import org.td.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value="/records",method = GET)
	public @ResponseBody ViewPage<User> getUsers(Pageable page) {		
		return new ViewPage<>(service.readAll(page));
	}
	
	@RequestMapping(method= POST)
	public ResponseEntity<String> createUser(HttpServletRequest request, @RequestBody User user) {
		User createdUser = service.create(user);

		URI uri = new UriTemplate("{requestUrl}/{id}").expand(request.getRequestURL().toString(), createdUser.getId());
		final HttpHeaders headers = new HttpHeaders();
		headers.put("Location", singletonList(uri.toASCIIString()));
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable("id") String id, @RequestBody User user) {
		service.update(user);
	}
	
	@RequestMapping(value="/delete/{id}", method=DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") String id) {		
		service.delete(id);
	}
	
	@RequestMapping(value="/captcha",method = GET)
	@ResponseBody
	//Captcha generator for security but must be something like image not complete!
	public String getCaptcha(HttpServletRequest request)
	{
		String captchaVal = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
		request.getSession().setAttribute("captcha",captchaVal);
		return captchaVal;
	}
}