package meal.planner.dataBase;

import java.io.File;
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
	
	public boolean isOk(){
		if(ings==null || recs==null || meals==null){
			return false;
		}
		for(Ingredient i : ings.getAll()){
			if(i==null){
				return false;
			}
		}
		for(Recipe i : recs.getAll()){
			if(i==null){
				return false;
			}
		}
		for(Meal i : meals.getAll()){
			if(i==null){
				return false;
			}
		}
		return true;
	}
	
	/** Constructs new and empty Data Base */
	public DataBase(){
		this.ings = new ItemList<Ingredient, IngAttribute>();
		this.recs = new ItemList<Recipe, Ingredient>();
		this.meals = new ItemList<Meal, Recipe>();
		assert isOk();
	}
	
	/** Returns the Ingredient with the specified Id */
	public Ingredient getIngredient(int id){
		assert isOk();
		return ings.findId(id);
	}
	
	/** Returns the Recipe with the specified Id */
	public Recipe getRecipe(long id){
		assert isOk();
		return recs.findId(id);
	}
	
	/** Returns the Meal with the specified Id */
	public Meal getMeal(long id){
		assert isOk();
		return meals.findId(id);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Ingredient> findIngredients(String name, float minPrice, float maxPrice, IngAttribute ... parts){
		assert isOk();
		return ings.findAll(name, minPrice, maxPrice, parts);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Recipe> findRecipes(String name, float minPrice, float maxPrice, Ingredient ... parts){
		assert isOk();
		return recs.findAll(name, minPrice, maxPrice, parts);
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<Meal> findMeals(String name, float minPrice, float maxPrice, Recipe ... parts){
		assert isOk();
		return meals.findAll(name, minPrice, maxPrice, parts);
	}
	
	public Ingredient newIngredient(){
		assert isOk();
		Ingredient i = new Ingredient(-1);
		ings.add(i);
		assert isOk();
		return i;
	}
	
	public Recipe newRecipe(){
		assert isOk();
		Recipe i = new Recipe(-1);
		recs.add(i);
		assert isOk();
		return i;
	}
	
	public Meal newMeal(){
		assert isOk();
		Meal i = new Meal(-1);
		meals.add(i);
		assert isOk();
		return i;
	}
	
	public ArrayList<Ingredient> getAllIngs(){
		assert isOk();
		return ings.getAll();
	}
	
	public ArrayList<Recipe> getAllRecs(){
		assert isOk();
		return recs.getAll();
	}
	
	public ArrayList<Meal> getAllMeals(){
		assert isOk();
		return meals.getAll();
	}
	
	public ArrayList<Ingredient> getAllIngsSortedByName(){
		assert isOk();
		return ings.getAllSortedByName();
	}
	
	public ArrayList<Recipe> getAllRecsSortedByName(){
		assert isOk();
		return recs.getAllSortedByName();
	}
	
	public ArrayList<Meal> getAllMealsSortedByName(){
		assert isOk();
		return meals.getAllSortedByName();
	}
	
	public void makePdf(Meal meal, File file, int attendees){
		assert isOk();
		try{
			PdfDataWriter.writePdf(meal, file, attendees);
		}catch(Exception ex){
			System.out.println("Couldn't wrte file");
		}
		assert isOk();
	}
	
}
