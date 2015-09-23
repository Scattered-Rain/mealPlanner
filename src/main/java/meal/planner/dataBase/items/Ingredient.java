package meal.planner.dataBase.items;

import java.util.ArrayList;

import meal.planner.dataBase.IngAttribute;

public class Ingredient extends Item<IngAttribute>{
	
	/** The price of this ingredient */
	private float price;
	
	public Ingredient(int id){
		super(id);
		this.price = 0;
	}
	
	/** Returns whether the Ingredient contains the given IngAttribute */
	public boolean contains(IngAttribute att){
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
		return false;
	}
	
	/** Returns the price of this ingredient */
	public float getPrice(){
		return price;
	}
	
	/** Sets the price of the current Ingredient */
	public void setPrice(float price){
		this.price = price;
	}

}
