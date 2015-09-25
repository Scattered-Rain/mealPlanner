package meal.planner.dataBase.items;

public class Meal extends Item<Recipe>{
	
	public Meal(long id) {
		super(id);
	}
	
	/** Returns whether this meal contains the given Recipe */
	@Override
	public boolean contains(Recipe rec){
		for(Recipe r : subItems){
			if(r.getId()==rec.getId()){
				return true;
			}
		}
		return false;
	}
	
	/** Returns the price of the entre meal */
	@Override
	public double getPrice() {
		float price = 0;
		for(Recipe r : subItems){
			price += r.getPrice();
		}
		return price;
	}
	
}
