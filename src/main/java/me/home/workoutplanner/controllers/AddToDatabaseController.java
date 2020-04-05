package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.GetExerciseData;
import me.home.workoutplanner.model.GetMealData;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.ExerciseRepository;
import me.home.workoutplanner.repositories.MealRepository;

public class AddToDatabaseController {

	@Autowired
	private DayRepository repository;

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@RequestMapping(value = "/days/mealAdd", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String addMealToDate(@RequestBody GetMealData day) {

		Optional<Day> findByDate = repository.findByDate(day.getDate());
		Meal mealToAdd = mealRepository.findById(day.getId()).get();
		
		if (checkExistance(findByDate)) {
			modifyMealsOnDate(mealToAdd,findByDate.get(),day.getEaten());
			return "Succesfully Modified";
		} else {
			addNewDayToDatabaseWithMeal(mealToAdd,day.getEaten(),day.getDate());
			return "Successfully created new Day";
		}
	}
	
	private void addNewDayToDatabaseWithMeal(Meal mealToAdd,double eatenAmount, LocalDate dateOfConsumption) {
		Map<Meal, Double> meals = new HashMap<>();
		meals.put(mealToAdd, eatenAmount);
		Day newDay = new Day(dateOfConsumption, new HashMap<Exercise, Double>(), meals);
		newDay.setInTake((mealToAdd.getCalories() / 100) * eatenAmount);
		repository.save(newDay);
	}
	
	private void modifyMealsOnDate(Meal mealToAdd,Day dateOfConsumption, double eatenAmount) {
		Day selectedDay = dateOfConsumption;
		
		if (selectedDay.getMeals().containsKey(mealToAdd)) {
			selectedDay.getMeals().put(mealToAdd, eatenAmount + selectedDay.getMeals().get(mealToAdd));
		} else {
			selectedDay.getMeals().put(mealToAdd, eatenAmount);
		}
		
		selectedDay.setInTake((mealToAdd.getCalories() / 100) * eatenAmount);
		repository.save(selectedDay);
	}

	@RequestMapping(value = "/days/exerciseAdd", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public String addExerciseToDate(@RequestBody GetExerciseData day) {

		Optional<Day> findByDate = repository.findByDate(day.getDate());
		Exercise exerciseDone = exerciseRepository.findById(day.getId()).get();

		if (checkExistance(findByDate)) {
			modifyExercisesOnDate(exerciseDone,findByDate.get(),day.getReps());
			return "Succesfully Modified";
		} else {
			addNewDayToDatabaseWithExercise(exerciseDone, day.getReps(), day.getDate());
			return "Successfully created new Day";
		}
	}

	private void addNewDayToDatabaseWithExercise(Exercise exerciseDone, double repNum, LocalDate dateOfTheExercise) {
		Map<Exercise, Double> exercises = new HashMap<>();
		exercises.put(exerciseDone, repNum);
		Day newDay = new Day(dateOfTheExercise, exercises, new HashMap<Meal, Double>());
		newDay.setBurn(exerciseDone.getBurn() * repNum);
		repository.save(newDay);
	}

	private void modifyExercisesOnDate(Exercise exerciseDone, Day dateOfExercise, double numberOfReps) {
		Day selectedDay = dateOfExercise;
		selectedDay.getRepsDone().put(exerciseDone, numberOfReps);
		selectedDay.setBurn(exerciseDone.getBurn() * numberOfReps);
		repository.save(selectedDay);
		
	}
	
	private boolean checkExistance(Optional<Day> day) {
		return day.isPresent();
	}

}
