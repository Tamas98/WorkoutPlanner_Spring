package me.home.workoutplanner.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.GetDayData;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;

@RestController
public class EvaluationController {
	
	Logger logger = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private DayRepository repository;
	
	@GetMapping("/days")
	public Iterable<Day> getDays(){
		logger.info("get from /days");
		return repository.findAll();
	}

	@RequestMapping(value="/days/getDayMeal",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Iterable<Meal> getMealsOnDay(@RequestBody GetDayData day){
		Day selectedDay = repository.findById(day.getId()).get();
		selectedDay.getMeals().forEach((meal,eaten) -> meal.setEaten(eaten));
		Iterable<Meal> meals = selectedDay.getMeals().keySet(); 
		return meals;
	}
	
	@RequestMapping(value="/days/getDayExercises",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Iterable<Exercise> getExercisesOnDay(@RequestBody GetDayData day){
		Day selectedDay = repository.findById(day.getId()).get();
		selectedDay.getRepsDone().forEach((exercise,done) -> exercise.setReps(done));
		Iterable<Exercise> exercises = selectedDay.getRepsDone().keySet(); 
		return exercises;
	}

}
