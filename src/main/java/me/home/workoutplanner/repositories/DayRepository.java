package me.home.workoutplanner.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.home.workoutplanner.model.Day;

public interface DayRepository extends JpaRepository<Day, Long>{
	
	Optional<Day> findByDate(LocalDate date);

}
