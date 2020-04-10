package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.GetExerciseData;
import me.home.workoutplanner.model.GetMealData;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.ExerciseRepository;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class AddToDatabaseController {
	
	private final Logger logger = LogManager.getLogger(AddToDatabaseController.class);

	@Autowired
	private DayRepository dayRepository;

	@Autowired
	private MealRepository mealRepository;

	@Autowired
	private ExerciseRepository exerciseRepository;

	@RequestMapping(value = "/days/mealAdd", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity addMealToDate(@RequestBody GetMealData day) {

		Optional<Day> findByDate = dayRepository.findByDate(day.getDate());
		Meal mealToAdd = mealRepository.findById(day.getId()).get();
		
		if (checkExistance(findByDate)) {
			modifyMealsOnDate(mealToAdd,findByDate.get(),day.getEaten());
			logger.info("Successfully modified day: " + day.getDate());
			return ResponseEntity.ok().body("Modified");
		} else {
			addNewDayToDatabaseWithMeal(mealToAdd,day.getEaten(),day.getDate());
			logger.info("Successfully created day: " + day.getDate() + " data with meal");
			return ResponseEntity.ok().body("New");
		}
	}
	
	private void addNewDayToDatabaseWithMeal(Meal mealToAdd,double eatenAmount, LocalDate dateOfConsumption) {
		Map<Meal, Double> meals = new HashMap<>();
		meals.put(mealToAdd, eatenAmount);
		Day newDay = new Day(dateOfConsumption, new HashMap<Exercise, Double>(), meals);
		newDay.setInTake((mealToAdd.getCalories() / 100) * eatenAmount);
		dayRepository.save(newDay);
	}
	
	private void modifyMealsOnDate(Meal mealToAdd,Day dateOfConsumption, double eatenAmount) {
		Day selectedDay = dateOfConsumption;
		
		if (selectedDay.getMeals().containsKey(mealToAdd)) {
			selectedDay.getMeals().put(mealToAdd, eatenAmount + selectedDay.getMeals().get(mealToAdd));
			logger.info("Meal modified: " + mealToAdd.toString());
		} else {
			selectedDay.getMeals().put(mealToAdd, eatenAmount);
			logger.info("new food added to daily meals: " + mealToAdd.toString());
		}
		
		selectedDay.setInTake((mealToAdd.getCalories() / 100) * eatenAmount);
		dayRepository.save(selectedDay);
	}

	@RequestMapping(value = "/days/exerciseAdd", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity addExerciseToDate(@RequestBody GetExerciseData day) {

		Optional<Day> findByDate = dayRepository.findByDate(day.getDate());
		Exercise exerciseDone = exerciseRepository.findById(day.getId()).get();

		if (checkExistance(findByDate)) {
			modifyExercisesOnDate(exerciseDone,findByDate.get(),day.getReps());
			logger.info("Successfully modified day: " + day.getDate());
			return ResponseEntity.ok().body("Modified");
		} else {
			addNewDayToDatabaseWithExercise(exerciseDone, day.getReps(), day.getDate());
			logger.info("Successfully created day: " + day.getDate() + " data with exercise");
			return ResponseEntity.ok().body("New");
		}
	}

	private void addNewDayToDatabaseWithExercise(Exercise exerciseDone, double repNum, LocalDate dateOfTheExercise) {
		Map<Exercise, Double> exercises = new HashMap<>();
		exercises.put(exerciseDone, repNum);
		Day newDay = new Day(dateOfTheExercise, exercises, new HashMap<Meal, Double>());
		newDay.setBurn(exerciseDone.getBurn() * repNum);
		dayRepository.save(newDay);
		logger.info("Successfully saved Day to database: " + newDay);
	}

	private void modifyExercisesOnDate(Exercise exerciseDone, Day dateOfExercise, double numberOfReps) {
		Day modifiedDay = dateOfExercise;
		
		if(modifiedDay.getRepsDone().containsKey(exerciseDone)) {
			double alreadyDoneReps = modifiedDay.getRepsDone().get(exerciseDone);
			modifiedDay.getRepsDone().put(exerciseDone, alreadyDoneReps+numberOfReps);
			logger.info("Exercise modified " + exerciseDone.toString());
		}else {
			modifiedDay.getRepsDone().put(exerciseDone, numberOfReps);
			logger.info("Exercise added " + exerciseDone.toString());
		}
		
		modifiedDay.setBurn(exerciseDone.getBurn()*numberOfReps);
		dayRepository.save(modifiedDay);
		
	}
	
	private boolean checkExistance(Optional<Day> day) {
		return day.isPresent();
	}

}
