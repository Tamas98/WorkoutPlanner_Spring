package me.home.workoutplanner.controllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class MealController implements MealAndExerciseControllerInterface<Meal>{
	
	Logger logger = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private MealRepository repository;
	
	@Override
	@GetMapping("/meals")
	@ResponseBody
	public Iterable<Meal> getAllFromDatabase() {
		logger.info(repository.findAll().toString());
		return repository.findAll();
	}

	@Override
	@PostMapping("/meals")
	@ResponseBody
	public Meal addNewInstanceToDatabase(@RequestParam("meal") Meal meal) {
		List<Meal> meals = (List<Meal>) repository.findAll();
		if(meals.contains(meal)) {
			return meal;
		}
		repository.save(meal);
		return meal;
	}
	
}
