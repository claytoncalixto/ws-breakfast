package com.wl.breakfast.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.claytoncalixto.dscatalog.dto.BreakfastDTO;
import com.claytoncalixto.dscatalog.entities.Breakfast;
import com.claytoncalixto.dscatalog.services.exceptions.DatabaseException;
import com.claytoncalixto.dscatalog.services.exceptions.ResourceNotFoundException;
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
	
	@Transactional(readOnly = true)
	public BreakfastDTO findById(Long id) throws Exception {
		Optional<Breakfast> obj = repository.findById(id);
		Breakfast entity = obj.orElseThrow(() -> new Exception("Entity not found"));
		return new BreakfastDTO(entity);
	}

	@Transactional
	public BreakfastDTO insert(BreakfastDTO dto) {
		Breakfast entity = new Breakfast();
		entity.setOptions(dto.getOptions());
		entity = repository.save(entity);
		return new BreakfastDTO(entity);
	}

	@Transactional
	public BreakfastDTO update(Long id, BreakfastDTO dto) throws Exception {
		try {
		Breakfast entity =  repository.getOne(id);
		entity.setOptions(dto.getOptions());
		entity = repository.save(entity);
		return new BreakfastDTO(entity);
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
}
