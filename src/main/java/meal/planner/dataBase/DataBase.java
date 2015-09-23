package meal.planner.dataBase;

import java.util.ArrayList;

import meal.planner.dataBase.itemList.ItemList;
import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

/** This class serves as the central storage and access unit */
public class DataBase {
	
	/** An Item List containing all Ingredients */
	private ItemList<Ingredient, IngAttribute> ings;
	/** An Item List containing all Recipes */
	private ItemList<Recipe, Ingredient> recs;
	/** An Item List containing all Meals */
	private ItemList<Meal, Recipe> meals;
	
	//TODO: Add a constructor that can create the entire DataBase based upon an external file
	
	/** Constructs new and empty Data Base */
	public DataBase(){
		this.ings = new ItemList<Ingredient, IngAttribute>();
		this.recs = new ItemList<Recipe, Ingredient>();
		this.meals = new ItemList<Meal, Recipe>();
	}
	
	/** Returns the Ingredient with the specified Id */
	public Ingredient getIngredient(int id){
		return ings.findId(id);
	}
	
	/** Returns the Recipe with the specified Id */
	public Recipe getRecipe(int id){
		return recs.findId(id);
	}
	
	/** Returns the Meal with the specified Id */
	public Meal getMeal(int id){
		return meals.findId(id);
	}
	
	
	
}
