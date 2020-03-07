package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.GetDayData;
import me.home.workoutplanner.model.GetExerciseData;
import me.home.workoutplanner.model.GetMealData;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.ExerciseRepository;
import me.home.workoutplanner.repositories.MealRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
public class EvaluationController {
	
	Logger logger = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private DayRepository repository;
	
	@Autowired
	private MealRepository mealRepository;
	
	@Autowired
	private ExerciseRepository exerciseRepository;
	
	@GetMapping("/days")
	public Iterable<Day> getDays(){
		logger.info("get from /days");
		return repository.findAll();
	}
	
	@RequestMapping(value="/days/mealAdd",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String putMealToDate(@RequestBody GetMealData day) {
		
		logger.info("Posted to /days");

		Optional<Day> findByDate = repository.findByDate(day.getDate());
		
		if(checkExistance(findByDate)) {
			Meal mealToAdd = mealRepository.findById(day.getId()).get();
			Day selectedDay = findByDate.get();
			if(selectedDay.getMeals().containsKey(mealToAdd)) {
				selectedDay.getMeals().put(mealToAdd,day.getEaten()+selectedDay.getMeals().get(mealToAdd));
			}else {
				selectedDay.getMeals().put(mealToAdd,day.getEaten());
			}
			selectedDay.setInTake((mealToAdd.getCalories()/100)*day.getEaten());
			repository.save(selectedDay);
			return "Succesfully Modified";
		}else {
			Meal mealToAdd = mealRepository.findById(day.getId()).get();
			Map<Meal,Double> meals = new HashMap<>();
			meals.put(mealToAdd, day.getEaten());
			Day newDay = new Day(day.getDate(),new HashMap<Exercise,Double>(),meals);
			newDay.setInTake((mealToAdd.getCalories()/100)*day.getEaten());
			repository.save(newDay);
			return "Successfully created new Day";
		}
	}
	
	@RequestMapping(value="/days/exerciseAdd",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String putExerciseToDate(@RequestBody GetExerciseData day) {
		
		Optional<Day> findByDate = repository.findByDate(day.getDate());
		Exercise exerciseDone = exerciseRepository.findById(day.getId()).get();
		
		if(checkExistance(findByDate)) {
			
			Day selectedDay = findByDate.get();
			selectedDay.getRepsDone().put(exerciseDone, day.getReps());
			selectedDay.setBurn(exerciseDone.getBurn()*day.getReps());
			repository.save(selectedDay);
			return "Succesfully Modified";
		}else {
			Map<Exercise,Double> exercises = new HashMap<>();
			exercises.put(exerciseDone, day.getReps());
			Day newDay = new Day(day.getDate(),exercises,new HashMap<Meal,Double>());
			newDay.setInTake(exerciseDone.getBurn()*day.getReps());
			repository.save(newDay);
			return "Successfully created new Day";
		}
	}

	private boolean checkExistance(Optional<Day> day) {
		logger.info("Posted to /days");
		return day.isPresent();
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
