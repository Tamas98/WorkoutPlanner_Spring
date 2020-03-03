package me.home.workoutplanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class MealController {

	@Autowired
	private MealRepository repository;
	
	@GetMapping("/meals")
	public Iterable<Meal> getTypes() {
		return repository.findAll();
	}
	
}
