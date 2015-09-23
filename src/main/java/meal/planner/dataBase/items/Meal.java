package meal.planner.dataBase.items;

import java.util.ArrayList;

public class Meal extends Item<Recipe>{
	
	private ArrayList<Recipe> recs;
	
	public Meal(int id){
		super(id);
		this.recs = new ArrayList<Recipe>();
	}
	
	/** Returns whether this meal contains the given Recipe */
	public boolean contains(Recipe rec){
		for(Recipe r : recs){
			if(r.getId()==rec.getId()){
				return true;
			}
		}
		return false;
	}
	
	/** Returns the price of the entre meal */
	public float getPrice(){
		float price = 0;
		for(Recipe r : recs){
			price += r.getPrice();
		}
		return price;
	}
	
}
