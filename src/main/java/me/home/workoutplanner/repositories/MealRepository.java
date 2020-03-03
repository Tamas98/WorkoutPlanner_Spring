package me.home.workoutplanner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.home.workoutplanner.model.Meal;

@RepositoryRestResource
public interface MealRepository extends CrudRepository<Meal, Long>{
	
	List<Meal> findAllByType(String type);
	
	@Query("Select disctinct type(m.type) from Meals m order by type(m.type)")
	List<String> getTypes();
}
