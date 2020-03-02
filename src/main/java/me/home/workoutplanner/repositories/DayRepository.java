package me.home.workoutplanner.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import me.home.workoutplanner.model.Day;

@RepositoryRestResource
public interface DayRepository extends CrudRepository<Day, Long>{
	
	Optional<Day> findByDate(LocalDate date);

}
