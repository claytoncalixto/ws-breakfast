package com.wl.breakfast.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wl.breakfast.dto.UserDTO;
import com.wl.breakfast.entities.Seller;
import com.wl.breakfast.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public List<UserDTO> findAll(){
		List<Seller> result = repository.findAll();
		return result.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	}
}
