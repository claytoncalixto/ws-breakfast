package com.wl.breakfast.dto;

import java.time.LocalDate;

import com.wl.breakfast.entities.Breakfast;

public class BreakfastDTO {
	
	private Long id;
	private String options;
	private LocalDate date;

	private UserDTO userDTO;
	
	public BreakfastDTO (){
	}

	public BreakfastDTO(Long id, String options, LocalDate date, UserDTO userDTO) {
		this.id = id;
		this.options = options;
		this.date = date;
		this.userDTO = userDTO;
	}
	
	public BreakfastDTO(Breakfast entity) {
		id = entity.getId();
		options = entity.getOptions();
		date = entity.getDate();
		userDTO = new UserDTO(entity.getUser());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}	
}
