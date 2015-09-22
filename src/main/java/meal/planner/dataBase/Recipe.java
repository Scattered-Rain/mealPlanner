package meal.planner.dataBase;

import java.util.ArrayList;

public class Recipe {
	
	private int id;
	private String name;
	private String description;
	private ArrayList<Ingredient> ings;
	
	public Recipe(int id){
		this.id = id;
		this.name = "";
		this.description = "";
		this.ings = new ArrayList<Ingredient>();
	}
	
	
	
}
