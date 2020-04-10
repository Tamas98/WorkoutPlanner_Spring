package me.home.workoutplanner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;
	
	private String type;
	
	private double calories;
	
	private double fat;
	
	private double salt;

	private double protein;

	private double sugar;

	private double carbos;

	private double eaten;
	
	public Meal() {
	}

	public Meal(String name, String type, double calories, double fat, double salt, double protein, double sugar,
			double carbos) {
		this.name = name;
		this.type = type;
		this.calories = calories;
		this.fat = fat;
		this.salt = salt;
		this.protein = protein;
		this.sugar = sugar;
		this.carbos = carbos;
	}
	
	public Meal(String name, String type, double calories, double fat, double salt, double protein, double sugar,
			double carbos, double eaten) {
		this.name = name;
		this.type = type;
		this.calories = calories;
		this.fat = fat;
		this.salt = salt;
		this.protein = protein;
		this.sugar = sugar;
		this.carbos = carbos;
		this.eaten = eaten;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) {
		this.fat = fat;
	}

	public double getSalt() {
		return salt;
	}

	public void setSalt(double salt) {
		this.salt = salt;
	}

	public double getProtein() {
		return protein;
	}

	public void setProtein(double protein) {
		this.protein = protein;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public double getCarbos() {
		return carbos;
	}

	public void setCarbos(double carbos) {
		this.carbos = carbos;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getEaten() {
		return eaten;
	}

	public void setEaten(double eaten) {
		this.eaten = eaten;
	}

	@Override
	public String toString() {
		return "Meal [id=" + id + ", name=" + name + ", type=" + type + ", calories=" + calories + ", fat=" + fat
				+ ", salt=" + salt + ", protein=" + protein + ", sugar=" + sugar + ", carbos=" + carbos + ", eaten="
				+ eaten + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Meal meal = (Meal) obj;
		if(this.name.equals(meal.getName()) && this.calories == meal.getCalories())
			return true;
		else
			return false;
	}
}
