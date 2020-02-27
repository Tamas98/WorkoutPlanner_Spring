package me.home.workoutplanner.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.home.workoutplanner.model.Exercise;

@RepositoryRestResource
public interface ExerciseRepository extends CrudRepository<Exercise, Long> { 

	List<Exercise> findAllByType(String type);
	
}
