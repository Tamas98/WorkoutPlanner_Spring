package me.home.workoutplanner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.home.workoutplanner.model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> { 

	List<Exercise> findAllByType(String type);
	
}
