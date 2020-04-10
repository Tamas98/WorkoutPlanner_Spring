package me.home.workoutplanner.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity getAllFromDatabase() {
		Iterable<Exercise> exercisesInDatabase = repository.findAll();
		logger.info("All exercise queried");
		return ResponseEntity.ok(exercisesInDatabase);
	}

	@Override
	@RequestMapping(path = "/exercises",method = RequestMethod.POST )
	@ResponseBody
	public ResponseEntity addNewInstanceToDatabase(@RequestBody Exercise exercise) {
		List<Exercise> exercises = (List<Exercise>) repository.findAll();
		
		if(exercises.contains(exercise)) {
			logger.info("User tried to add already  existing meal: " + exercise.toString());
			return ResponseEntity.status(409).build();
		}
		
		repository.save(exercise);
		logger.info("New exercise: " + exercise.toString() + " successfully added to databese");
		return ResponseEntity.ok().build();
	}
}
