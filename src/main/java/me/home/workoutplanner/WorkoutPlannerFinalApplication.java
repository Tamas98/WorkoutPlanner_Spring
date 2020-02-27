package me.home.workoutplanner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.ExerciseRepository;
import me.home.workoutplanner.repositories.MealRepository;

@ComponentScan
@SpringBootApplication
public class WorkoutPlannerFinalApplication extends SpringBootServletInitializer{
	
	@Autowired
	private MealRepository mealRepo;

	@Autowired
	private ExerciseRepository exerciseRepo;
	
	@Autowired
	private DayRepository dayRepo;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WorkoutPlannerFinalApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WorkoutPlannerFinalApplication.class, args);
	}
	
	 @Bean
	 CommandLineRunner runner(){
		 return args -> {
			 Meal meal = new Meal("test","chicken",0,0,0,0,0,0,100);
			 mealRepo.save(meal);
			 Exercise exec = new Exercise("test2","medium",20,10,"bicessz");
			 exerciseRepo.save(exec);
			 Map<Exercise,Integer> exercises = new HashMap<>();
			 exercises.put(exec, 20);
			 Map<Meal,Double> meals= new HashMap<>();
			 meals.put(meal,20.2);
			 Day day = new Day(LocalDate.now(),exercises,meals);
			 dayRepo.save(day);
		 };
	 }

}
