package meal.planner.dataBase.items;

public class Meal extends Item<Recipe>{
	
	public Meal(long id) {
		super(id);
	}
	
	/** Returns whether this meal contains the given Recipe */
	@Override
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
	public double getPrice(){
		assert isOk();
		float price = 0;
		for(Recipe r : subItems){
			price += r.getPrice();
		}
		assert isOk();
		return price;
	}
	
}
