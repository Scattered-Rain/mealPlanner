package meal.planner.dataBase.items;

import java.util.ArrayList;

public class Meal extends Item<Recipe>{
	
	public Meal(int id){
		super(id);
	}
	
	/** Returns whether this meal contains the given Recipe */
	public boolean contains(Recipe rec){
		assert isOk();
		for(Recipe r : subItems){
			if(r.getId()==rec.getId()){
				return true;
			}
		}
		assert isOk();
		return false;
	}
	
	/** Returns the price of the entre meal */
	public float getPrice(){
		assert isOk();
		float price = 0;
		for(Recipe r : subItems){
			price += r.getPrice();
		}
		assert isOk();
		return price;
	}
	
}
