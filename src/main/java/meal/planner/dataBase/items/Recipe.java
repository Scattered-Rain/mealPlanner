package meal.planner.dataBase.items;

import java.util.ArrayList;

public class Recipe extends Item<Ingredient>{
	
	private ArrayList<Ingredient> ings;
	
	public Recipe(int id){
		super(id);
		this.ings = new ArrayList<Ingredient>();
	}
	
	/** Returns whether this Recipe contains the given ingredient */
	public boolean contains(Ingredient ing){
		for(Ingredient i : ings){
			if(i.getId()==ing.getId()){
				return true;
			}
		}
		return false;
	}
	
	/** Returns the price of this Recipe */
	public float getPrice(){
		float price = 0;
		for(Ingredient i : ings){
			price += i.getPrice();
		}
		return price;
	}
	
}
