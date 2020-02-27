package me.home.workoutplanner.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class Day {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private LocalDate date;
	
	private double inTake;
	
	private double burn;
	
	private double result;
	
	@ElementCollection
	@CollectionTable(name="meals_eaten")
	@MapKeyColumn(name="day_id")
	private Map<Meal,Double> meals;
	
	@ElementCollection
	@CollectionTable(name="exercises_done")
	@MapKeyColumn(name="day_id")
	private Map<Exercise,Integer> exercises;

	public Map<Exercise, Integer> getExercises() {
		return exercises;
	}

	public void setExercises(Map<Exercise, Integer> exercises) {
		this.exercises = exercises;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getInTake() {
		return calculateInTake(this.meals);
	}

	public void setInTake(double inTake) {
		this.inTake = inTake;
	}

	public double getBurn() {
		return calculateBurn(this.exercises);
	}

	public void setBurn(double burn) {
		this.burn = burn;
	}

	public double getResult() {
		return calculateBurn(this.exercises) - calculateInTake(this.meals);
	}

	public void setResult(double result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Day [id=" + id + ", date=" + date + ", inTake=" + inTake + ", burn=" + burn + ", result=" + result
				+ "]";
	}

	public Day(LocalDate date, Map<Exercise,Integer> exercises, Map<Meal,Double> meals) {
		this.date = date;
		this.exercises = exercises;
		this.meals = meals;
		this.inTake = 0;//calculateInTake(meals);
		this.burn = 0; //calculateBurn(exercises);
		this.result = this.inTake-this.burn;
	}
	
	public Day() {}
	
	private double calculateBurn(Map<Exercise,Integer> exercises) {
		
		ArrayList<Double> burned = new ArrayList<>();
		
		exercises.forEach((exercise,reps) -> burned.add(exercise.getBurn()*reps));
		
		double sum = 0;
		
		for(double b:burned) {
			sum += b;
		}
		
		return sum;
	}

	public Map<Meal, Double> getMeals() {
		return meals;
	}

	public void setMeals(Map<Meal, Double> meals) {
		this.meals = meals;
	}
	
	private double calculateInTake(Map<Meal,Double> meals) {
		
		ArrayList<Double> eat = new ArrayList<>();
		
		meals.forEach((meal,eaten) -> eat.add(meal.getCalories()*eaten));
		
		double sum = 0;
		
		for(double d:eat) {
			sum += d;
		}
		
		return sum;
	}
}
