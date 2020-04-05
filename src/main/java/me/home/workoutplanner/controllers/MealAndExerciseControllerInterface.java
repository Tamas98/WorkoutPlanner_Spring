package me.home.workoutplanner.controllers;

public interface MealAndExerciseControllerInterface<T> {
	
	Iterable<T> getAllFromDatabase();
	
	T addNewInstanceToDatabase(T instanceToAdd);
	
}
