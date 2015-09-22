package meal.planner.dataBase;

import java.util.ArrayList;

import meal.planner.dataBase.itemList.ItemList;
import meal.planner.dataBase.items.Ingredient;
import meal.planner.dataBase.items.Meal;
import meal.planner.dataBase.items.Recipe;

/** This class serves as the central storage and access unit */
public class DataBase {
	
	private ItemList<Ingredient, IngAttribute> ings;
	private ItemList<Recipe, Ingredient> recs;
	private ItemList<Meal, Recipe> meals;
	
	/** Constructs new and empty Data Base */
	public DataBase(){
		this.ings = new ItemList<Ingredient, IngAttribute>();
		this.recs = new ItemList<Recipe, Ingredient>();
		this.meals = new ItemList<Meal, Recipe>();
	}
	
}
