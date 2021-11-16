package com.wl.breakfast.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wl.breakfast.dto.BreakfastDTO;
import com.wl.breakfast.entities.Breakfast;
import com.wl.breakfast.repositories.BreakfastRepository;
import com.wl.breakfast.repositories.UserRepository;

@Service
public class BreakfastService {

	@Autowired
	private BreakfastRepository repository;
	
	@Autowired
	private UserRepository userRepository;;
	
	@Transactional(readOnly = true)
	public Page<BreakfastDTO> findAll(Pageable pageable){
		userRepository.findAll();
		Page<Breakfast> result = repository.findAll(pageable);
		return result.map(x -> new BreakfastDTO(x));
	}
}
