package me.home.workoutplanner.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.repositories.ExerciseRepository;

@RestController
public class ExerciseController {

	@Autowired
	private ExerciseRepository repository;
	
	@GetMapping("/exercises")
	public Iterable<Exercise> getTypes() {
		return repository.findAll();
	}
	
}
