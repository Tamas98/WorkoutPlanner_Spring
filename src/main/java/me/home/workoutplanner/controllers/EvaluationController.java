package me.home.workoutplanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.repositories.DayRepository;

@RestController
public class EvaluationController {
	
	@Autowired
	private DayRepository repository;
	
	@GetMapping("/eval")
	public Iterable<Day> getDays(){
		return repository.findAll();
	}

}
