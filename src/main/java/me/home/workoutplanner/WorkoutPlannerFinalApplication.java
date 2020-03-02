package me.home.workoutplanner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.home.workoutplanner.model.Day;
import me.home.workoutplanner.model.Exercise;
import me.home.workoutplanner.model.Meal;
import me.home.workoutplanner.repositories.DayRepository;
import me.home.workoutplanner.repositories.ExerciseRepository;
import me.home.workoutplanner.repositories.MealRepository;

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
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    // Don't do this in production, use a proper list  of allowed origins
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
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
