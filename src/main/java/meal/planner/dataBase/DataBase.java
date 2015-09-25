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
	
	public Ingredient newIngredient(){
		Ingredient i = new Ingredient(-1);
		ings.add(i);
		return i;
	}
	
	public Recipe newRecipe(){
		Recipe i = new Recipe(-1);
		recs.add(i);
		return i;
	}
	
	public Meal newMeal(){
		Meal i = new Meal(-1);
		meals.add(i);
		return i;
	}
	
	public ArrayList<Ingredient> getAllIngs(){
		return ings.getAll();
	}
	
	public ArrayList<Recipe> getAllRecs(){
		return recs.getAll();
	}
	
	public ArrayList<Meal> getAllMeals(){
		return meals.getAll();
	}
	
	public ArrayList<Ingredient> getAllIngsSortedByName(){
		return ings.getAllSortedByName();
	}
	
	public ArrayList<Recipe> getAllRecsSortedByName(){
		return recs.getAllSortedByName();
	}
	
	public ArrayList<Meal> getAllMealsSortedByName(){
		return meals.getAllSortedByName();
	}
	
}
