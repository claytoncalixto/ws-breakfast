package com.wl.breakfast.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claytoncalixto.dscatalog.dto.ProductDTO;
import com.wl.breakfast.dto.BreakfastDTO;
import com.wl.breakfast.entities.User;
import com.wl.breakfast.services.BreakfastService;

@RestController
@RequestMapping(value = "/breakfasts")
public class BreakfastController {
	
	@Autowired
	private BreakfastService service;
	
	@GetMapping
	public ResponseEntity<Page<BreakfastDTO>> findAll(Pageable pageable){
		Page<BreakfastDTO> list = service.findAll(pageable);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(value = "/users/{id}")
	public ResponseEntity<BreakfastDTO> findByBreakfastId(@PathVariable Long id) throws Exception {
		BreakfastDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BreakfastDTO> findById(@PathVariable Long id) throws Exception {
		BreakfastDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
