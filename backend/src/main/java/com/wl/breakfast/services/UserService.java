package com.wl.breakfast.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.wl.breakfast.dto.UserDTO;
import com.wl.breakfast.dto.UserInsertDTO;
import com.wl.breakfast.dto.UserUpdateDTO;
import com.wl.breakfast.entities.User;
import com.wl.breakfast.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class); 

	
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;
	
	
	public List<UserDTO> findAll(){
		List<User> result = repository.findAll();
		return result.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) throws Exception {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new Exception("Entity not found"));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert( @RequestBody UserInsertDTO dto) {
		User entity = new User();
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) throws Exception {
		try {
		User entity =  repository.getOne(id);
		entity = repository.save(entity);
		return new UserDTO(entity);
		} catch (EntityNotFoundException e){
			throw new Exception("Id not found " + id);
		}
	}

	public void  delete(Long id) throws Exception {
		try {
		repository.deleteById(id);
		} catch (EmptyResultDataAccessException e ) {
			throw new Exception("Id not found "  + id);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Integraty Violation");
		}
	}
	
	public UserDetails loadUserByUsername(String username) {
		
		User user = repository.findByEmail(username);
		if(user == null) {
			logger.error("User not found " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info("User found " + username);
		return (UserDetails) user;
	}
}
