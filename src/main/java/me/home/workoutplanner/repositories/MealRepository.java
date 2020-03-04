package me.home.workoutplanner.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.home.workoutplanner.model.Meal;

@RepositoryRestResource
public interface MealRepository extends CrudRepository<Meal, Long>{
	
	Meal findById(int id);
	
}
