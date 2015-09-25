package meal.planner.dataBase.items;

import java.util.HashMap;

public class Recipe extends Item<Ingredient>{
	
	public Recipe(int id){
		super(id);
	}
	
	/** Returns whether this Recipe contains the given ingredient */
	@Override
	public boolean contains(Ingredient ing){
		for(Ingredient i : subItems){
			if(i.getId()==ing.getId()){
				return true;
			}
		}
		return false;
	}
	
	/** Returns the price of this Recipe */
	@Override
	public double getPrice() {
		float price = 0;
		for(Ingredient i : subItems){
			price += i.getPrice();
		}
		return price;
	}

	public void addIngredient(Ingredient ing, double amount) {
		// TODO: FLO.... IMPLEMENT THIS!
	}

	public HashMap<Ingredient, Double> getIngredients() {
		// TODO: FLO... IMPLEMENT THIS!
		return null;

	}
}
