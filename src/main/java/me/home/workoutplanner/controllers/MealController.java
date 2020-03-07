package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class MealController {
	
	Logger logger = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private MealRepository repository;
	
	@GetMapping("/meals")
	@ResponseBody
	public Iterable<Meal> getTypes() {
		logger.info(repository.findAll().toString());
		return repository.findAll();
	}
	
	@PostMapping("/meals")
	@ResponseBody
	public Meal addNewMeal(@RequestParam("meal") Meal meal) {
		List<Meal> meals = (List<Meal>) repository.findAll();
		if(meals.contains(meal)) {
			return meal;
		}
		repository.save(meal);
		return meal;
	}
	
}
