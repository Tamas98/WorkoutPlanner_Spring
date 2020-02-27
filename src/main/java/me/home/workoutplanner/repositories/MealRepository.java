package me.home.workoutplanner.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.home.workoutplanner.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Long>{
	
	List<Meal> findAllByType(String type);
}
