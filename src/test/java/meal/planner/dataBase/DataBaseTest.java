package meal.planner.dataBase;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import lombok.NoArgsConstructor;
import meal.planner.dataBase.items.*;

@NoArgsConstructor
public class DataBaseTest {

	DataBase db;

	@Before
	public void setup() {
		db = new DataBase();
	}

	@Test
	public void constructorTest() {
		// db is already constructed in setup, just verifying setup is correct

		ArrayList<Meal> meals = db.getAllMeals();
		ArrayList<Ingredient> ingredients = db.getAllIngs();
		ArrayList<Recipe> recipes = db.getAllRecs();

		// Test if the Database lists length are indeed 0.
		assertEquals("Meals list was not 0", 0, meals.size());
		assertEquals("Ingredients list was not 0", 0, ingredients.size());
		assertEquals("Recipes list was not 0", 0, recipes.size());

		// Test if sorting doesn't break
		ingredients = db.getAllIngsSortedByName();
		meals = db.getAllMealsSortedByName();
		recipes = db.getAllRecsSortedByName();

		// Test if the Database lists length are still 0.
		assertEquals("Meals list was not 0", 0, meals.size());
		assertEquals("Ingredients list was not 0", 0, ingredients.size());
		assertEquals("Recipes list was not 0", 0, recipes.size());

		// test if getIngredient returns null
		Ingredient ing = db.getIngredient(500);
		Meal meal = db.getMeal(200);
		Recipe r = db.getRecipe(234);

		//Check if the results are null
		assertNull("ingredient should be null", ing);
		assertNull("meal should be null", meal);
		assertNull("recipe should be null", r);
		

	}

	@Test
	public void ingredientTest() {
		Ingredient ing1 = db.newIngredient();
		//Check if ingredient was made correctly
		assertNotNull("Newly created ingredient was null",ing1);
		
		//Check if we can retrieve the ingredient properly
		Ingredient result =  db.getIngredient(ing1.getId());
		assertNotNull("Searched existing ingredient by ID resulted in null",result);
	}

	@Test
	public void recipeTest() {
		fail("Not yet Implemented");
	}

	@Test
	public void mealTest() {
		fail("Not yet Implemented");
	}
	
	/*
	 * Subroutines for initializing some data into the Database
	 */
	public void fillDB(){
		
	}
}