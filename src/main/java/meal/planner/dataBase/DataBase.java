package meal.planner.dataBase;

import java.util.ArrayList;

import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

/** This class serves as the central storage and access unit */
public class DataBase {
	
	private ArrayList<Ingredient> ings;
	private ArrayList<Recipe> recs;
	private ArrayList<Meal> meals;
	
	/** Constructs new and empty Data Base */
	public DataBase(){
		this.ings = new ArrayList<Ingredient>();
		this.recs = new ArrayList<Recipe>();
		this.meals = new ArrayList<Meal>();
	}
	
	public void add(Ingredient ing){
		this.ings.add(ing);
	}
	
	public void add(Recipe rec){
		this.recs.add(rec);
	}
	
	public void add(Meal meal){
		this.meals.add(meal);
	}
	
	
	
}
