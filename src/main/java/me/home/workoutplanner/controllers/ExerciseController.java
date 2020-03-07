package me.home.workoutplanner.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@PostMapping("/exercises")
	@ResponseBody
	public Exercise addNewMeal(@RequestParam("exercise") Exercise exercise) {
		List<Exercise> exercises = (List<Exercise>) repository.findAll();
		if(exercises.contains(exercise)) {
			return exercise;
		}
		repository.save(exercise);
		return exercise;
	}
	
}
