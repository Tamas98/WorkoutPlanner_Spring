package me.home.workoutplanner.controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.MealRepository;

@RestController
public class MealController {

	@Autowired
	private MealRepository repository;
	
	@Autowired
	private DayRepository dayRepo;
	
	@GetMapping("/meals")
	public Iterable<Meal> getTypes() {
		return repository.findAll();
	}
	
	@PutMapping("/meals")
	public Iterable<Meal> putMealToDate(@RequestParam("id") long id,
			@RequestParam("eaten") double eaten,
			@RequestParam("date") @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
		Optional<Meal> selectedMeal = repository.findById(id);
		Optional<Day> selectedDate = dayRepo.findByDate(date);
		
		if(selectedDate.isPresent()) {
	
			selectedDate.get().getMeals().put(selectedMeal.get(),eaten);
			dayRepo.save(selectedDate.get());
	
		}else {
			
			selectedMeal.get().setEaten(eaten);
			Day newDay = new Day(date,new HashMap<Exercise,Integer>(),new HashMap<Meal,Double>());
			newDay.getMeals().put(selectedMeal.get(), eaten);
			
			dayRepo.save(newDay);
		}
		
		return repository.findAll();
		
	}
	
}
