package me.home.workoutplanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class IndexController {

	@Autowired
	private MealRepository repository;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/meals")
	public Iterable<Meal> getMeals() {
		return repository.findAll();
	}
}
