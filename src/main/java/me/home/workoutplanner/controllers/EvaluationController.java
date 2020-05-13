package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.GetDayData;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;

/**
 * Handles requests from front-end about evalutaions.
 * @author tamas
 *
 */
@RestController
public class EvaluationController {
	
	private final Logger logger = LogManager.getLogger(EvaluationController.class);
	
	@Autowired
	private DayRepository repository;
	
	
	/**
	 * Queries all saved Day from the database.
	 * @return Iterable data structure containing all available Days data 
	 */
	@GetMapping("/days")
	@ResponseBody
	public ResponseEntity getAllDays(){
		logger.info("All Days Queried");
		return ResponseEntity.ok(repository.findAll());
	}

	/**
	 * Queries eaten meals on the given day.
	 * @param day the day user want to inspect
	 * @return All meal on the given day in an Iterable data structure.
	 */
	@RequestMapping(value="/days/getDayMeal",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity getMealsOnDay(@RequestBody GetDayData day){
		Day selectedDay = repository.findById(day.getId()).get();
		selectedDay.getMeals().forEach((meal,eaten) -> meal.setEaten(eaten));
		Iterable<Meal> meals = selectedDay.getMeals().keySet(); 
		logger.info("All meals on day "+ selectedDay.getDate().toString() + " queried");
		return ResponseEntity.ok(meals);
	}
	
	/**
	 * Queries done exercises on the given day.
	 * @param day the day user want to inspect
	 * @return All exercise on the given day in an Iterable data structure.
	 */
	@RequestMapping(value="/days/getDayExercises",method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public ResponseEntity getExercisesOnDay(@RequestBody GetDayData day){
		Day selectedDay = repository.findById(day.getId()).get();
		selectedDay.getRepsDone().forEach((exercise,done) -> exercise.setReps(done));
		Iterable<Exercise> exercises = selectedDay.getRepsDone().keySet(); 
		logger.info("All meals on day "+selectedDay.getDate().toString() + " queried");
		return ResponseEntity.ok(exercises);
	}

	@RequestMapping(value="/days/deleteExercise", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity deleteExerciseFromDay(@RequestParam("id") long exerciseId,@RequestParam("dateId") long dateId) {
		Optional<Day> selectedDay = repository.findById(dateId);
		if(selectedDay.isPresent()) {
			deleteExerciseByIdFromDay(selectedDay.get(),exerciseId);
			return ResponseEntity.ok(repository.findAll());
		}else {
			return ResponseEntity.status(-1).build();
		}
	}

	private void deleteExerciseByIdFromDay(Day day, long exerciseId) {
		Optional<Exercise> selectedExercise = day.getRepsDone().keySet().stream().filter(exercise -> exercise.getId() == exerciseId).findFirst();
		if(selectedExercise.isPresent()) {
			Exercise exerciseToDelete = selectedExercise.get();
			
			Map<Exercise,Double> exercisesOnDay = day.getRepsDone();
			double doneReps = exercisesOnDay.get(exerciseToDelete);
			
			exercisesOnDay.remove(exerciseToDelete);
			day.setRepsDone(exercisesOnDay);
			day.setBurn(-(doneReps*exerciseToDelete.getBurn()));
			repository.save(day);
		}
	}
	
	@RequestMapping(value="/days/deleteMeal", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity deleteMealFromDay(@RequestParam("id") long mealId,@RequestParam("dateId") long dateId) {
		Optional<Day> selectedDay = repository.findById(dateId);
		if(selectedDay.isPresent()) {
			deleteMealByIdFromDay(selectedDay.get(),mealId);
			return ResponseEntity.ok(repository.findAll());
		}else {
			return ResponseEntity.status(-1).build();
		}
	}
	
	private void deleteMealByIdFromDay(Day day, long mealId) {
		Optional<Meal> selectedMeal = day.getMeals().keySet().stream().filter(meal -> meal.getId() == mealId).findFirst();
		if(selectedMeal.isPresent()) {
			Meal mealToDelete = selectedMeal.get();
			
			Map<Meal,Double> mealsOnSelectedDay = day.getMeals();
			double eatenAmount = mealsOnSelectedDay.get(mealToDelete);
			
			mealsOnSelectedDay.remove(mealToDelete);
			day.setMeals(mealsOnSelectedDay);
			day.setInTake(-(eatenAmount*(mealToDelete.getCalories()/100)));
			repository.save(day);
		}
	}
	
	@RequestMapping(value="/days/getMonthData",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity getMonthDataForLineChart(@RequestParam("month") int monthAsInt) {
		List<Day> allDay = convertFromIterableToList(repository.findAllDayFromMonth(monthAsInt));
		logger.info("Queried all day from month: " + monthAsInt);
		
		allDay.sort((day1,day2) -> compareDays(day1.getDate(),day2.getDate()));	
		List<Day> dataForAllDayInMonth = createDataForAllDayInMonth(allDay,monthAsInt);
		
		return ResponseEntity.ok(dataForAllDayInMonth);
	}
	
	private List<Day> createDataForAllDayInMonth(List<Day> allDay,int monthAsInt) {

		Map<LocalDate,Day> extractedDateFromDays = extractDateFromDays(allDay);
		
		List<Day> dataOnAllDayOfTheMonth = new ArrayList<>();
		
		LocalDate firstDayOfCurrentlyInscpectedMonth = getFirstDayOfTheMonth(monthAsInt);
		
		long daysInTheMonth = ChronoUnit.DAYS.between(firstDayOfCurrentlyInscpectedMonth,getFirstDayOfTheMonth(monthAsInt+1));
		
		for(int i = 0; i < daysInTheMonth; i++) {
			dataOnAllDayOfTheMonth.add(inspectAndReturnNeededValue(extractedDateFromDays,firstDayOfCurrentlyInscpectedMonth.plusDays(i)));
		}
		
		return dataOnAllDayOfTheMonth;
	}

	private LocalDate getFirstDayOfTheMonth(int monthAsInt) {
		LocalDate now = LocalDate.now();
		return now.withMonth(monthAsInt).withDayOfMonth(1);
	}

	private Day inspectAndReturnNeededValue(Map<LocalDate, Day> extractedDateFromDays,
			LocalDate currentlyInspectedDayOfMonth) {

		if(extractedDateFromDays.keySet().contains(currentlyInspectedDayOfMonth)) {
			return extractedDateFromDays.get(currentlyInspectedDayOfMonth);
		}else {
			return new Day(currentlyInspectedDayOfMonth,0);
		}
		
	}

	private Map<LocalDate, Day> extractDateFromDays(List<Day> allDay) {
		Map<LocalDate,Day> extractedDateFromDays = new HashMap<>();
		
		allDay.forEach(day -> extractedDateFromDays.put(day.getDate(),day));
		
		return extractedDateFromDays;
	}

	private List<Day> convertFromIterableToList(Iterable<Day> days) {
		List<Day> allDay = new ArrayList<>();
		repository.findAll().forEach(day -> allDay.add(day));
		
		return allDay;
	}

	private int compareDays(LocalDate date1,LocalDate date2) {
		if(date2.isBefore(date1)) {
			return 1;
		}else {
			return -1;
		}
	}
}
