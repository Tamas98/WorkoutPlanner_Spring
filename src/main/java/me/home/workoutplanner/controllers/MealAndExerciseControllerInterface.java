package me.home.workoutplanner.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;

public interface MealAndExerciseControllerInterface<T> {
	
	static Logger logger = LogManager.getLogger(MealAndExerciseControllerInterface.class);
	
	ResponseEntity getAllFromDatabase();
	
	ResponseEntity addNewInstanceToDatabase(T instanceToAdd);
	
}
