package meal.planner.dataBase.items;

import meal.planner.dataBase.IngAttribute;

public class Ingredient extends Item<IngAttribute>{
	
	/** The price of this ingredient */
	private double price;
	
	public Ingredient(int id){
		super(id);
		this.price = 0;
	}
	
	/** Returns whether the Ingredient contains the given IngAttribute */
	@Override
	public boolean contains(IngAttribute att){
		assert isOk();
		for(IngAttribute a : subItems){
			if(a==att){
				return true;
			}
			else if(a.hasParent()){
				while(a.hasParent()){
					a = a.getParent();
					if(a==att){
						return true;
					}
				}
			}
		}
		assert isOk();
		return false;
	}
	
	/** Returns the price of this ingredient */
	public double getPrice(){
		assert isOk();
		return price;
	}
	
	/** Sets the price of the current Ingredient */
	public void setPrice(double price){
		assert isOk();
		this.price = price;
	}

}
