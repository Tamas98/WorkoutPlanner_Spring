package me.home.workoutplanner.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.repositories.ExerciseRepository;

@RestController
public class ExerciseController implements MealAndExerciseControllerInterface<Exercise>{

	@Autowired
	private ExerciseRepository repository;
	
	@Override
	@RequestMapping("/exercises")
	public Iterable<Exercise> getAllFromDatabase() {
		return repository.findAll();
	}

	@Override
	@RequestMapping(path = "/exercises",method = RequestMethod.POST )
	@ResponseBody
	public Exercise addNewInstanceToDatabase(@RequestParam("exercise") Exercise exercise) {
		List<Exercise> exercises = (List<Exercise>) repository.findAll();
		if(exercises.contains(exercise)) {
			return new Exercise();
		}
		repository.save(exercise);
		return exercise;
	}
}
