package me.home.workoutplanner.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity getAllFromDatabase() {
		logger.info(repository.findAll().toString());
		return ResponseEntity.ok(repository.findAll());
	}

	@Override
	@PostMapping("/meals")
	@ResponseBody
	public ResponseEntity addNewInstanceToDatabase(@RequestBody Meal meal) {
		List<Meal> availableMeals = convertIntoList(repository.findAll());
		
		if(availableMeals.contains(meal)) {
			logger.info("User tried to add already  existing meal: " + meal.toString());
			return ResponseEntity.status(409).build();
		}
		
		repository.save(meal);
		logger.info("New meal: " + meal.toString() + " successfully added to databese");
		return ResponseEntity.ok().build();
	}
	
	
	private List<Meal> convertIntoList(Iterable<Meal> mealsFromDatabase){
		List<Meal> availableMeals = new ArrayList<>();
		mealsFromDatabase.forEach(meal -> availableMeals.add(meal));
		return availableMeals;
	}
}
