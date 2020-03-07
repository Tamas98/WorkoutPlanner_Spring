package me.home.workoutplanner.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Day {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	private double inTake = 0;
	
	private double burn = 0;
	
	private double result;
	
	@ElementCollection()
	@JsonIgnore
	@CollectionTable(name="meals_eaten")
	@MapKeyColumn(name="day_id")
	private Map<Meal,Double> meals;
	
	@ElementCollection
	@JsonIgnore
	@CollectionTable(name="exercises_done")
	@MapKeyColumn(name="day_id")
	private Map<Exercise,Double> repsDone;


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

	@JsonProperty("intake")
	public double getInTake() {
		return this.inTake;
	}

	public void setInTake(double inTake) {
		this.inTake += inTake;
	}

	public double getBurn() {
		return this.burn;
	}

	public void setBurn(double burn) {
		this.burn += burn;
	}

	public double getResult() {
		return this.burn - this.inTake;
	}

	public void setResult(double result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Day [id=" + id + ", date=" + date + ", inTake=" + inTake + ", burn=" + burn + ", result=" + result
				+ "]";
	}

	public Day(LocalDate date, Map<Exercise,Double> exercises,Map<Meal,Double> meals) {
		System.out.println("Day Constructor called:");
		System.out.println(exercises.toString());
		System.out.println(meals.toString());
		this.date = date;
		this.repsDone = exercises;
		this.meals = meals;
		this.inTake = 0;//calculateInTake(meals);
		this.burn = 0; //calculateBurn(exercises);
		this.result = this.inTake-this.burn;
	}
	
	public Day() {
		System.out.println("Default constructor called");
	}
	
	private double calculateBurn(Map<Exercise,Double> exercises) {
		
		ArrayList<Double> burned = new ArrayList<>();
		
		exercises.forEach((exercise,done) -> burned.add(exercise.getBurn()*done));
		
		double sum = 0;
		
		for(double b:burned) {
			sum += b;
		}
		
		return sum;
	}

	/*public Day(LocalDate date, double inTake, double burn, List<Meal> meals,
			List<Exercise> exercises) {
		this.date = date;
		this.inTake = inTake;
		this.burn = burn;
		this.meals = meals;
		this.exercises = exercises;
	}*/
	
	private double calculateInTake(Map<Meal,Double> meals) {
		
		System.out.println("CalculateIntake called");
		System.out.println(this.meals.toString());
		
		ArrayList<Double> eat = new ArrayList<>();
		
		meals.forEach((meal,eaten) -> eat.add(meal.getCalories()*eaten));
		
		double sum = 0;
		
		for(double d:eat) {
			sum += d;
		}
		
		return sum;
	}

	public Map<Meal, Double> getMeals() {
		return this.meals;
	}

	public Map<Exercise, Double> getRepsDone() {
		return repsDone;
	}

	public void setRepsDone(Map<Exercise, Double> repsDone) {
		this.repsDone = repsDone;
	}
}
