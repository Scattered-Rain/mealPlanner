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
	public Meal getMeal(long id) {
		return meals.findId(id);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Ingredient> findIngredients(String name, float minPrice, float maxPrice, IngAttribute ... parts){
		return ings.findAll(name, minPrice, maxPrice, parts);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Recipe> findRecipes(String name, float minPrice, float maxPrice, Ingredient ... parts){
		return recs.findAll(name, minPrice, maxPrice, parts);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Meal> findMeals(String name, float minPrice, float maxPrice, Recipe ... parts){
		return meals.findAll(name, minPrice, maxPrice, parts);
	}
	
	
}
