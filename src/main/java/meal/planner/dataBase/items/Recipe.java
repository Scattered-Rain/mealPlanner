package meal.planner.dataBase.items;

import java.util.HashMap;

public class Recipe extends Item<Ingredient>{
	
	/** This Array Keeps track of the amount of an ingredient that has to be used here, the ingredients and amounts are coupled together via the index */
	private ArrayList<Double> ingQuant;
	
	public Recipe(int id){
		super(id);
		this.ingQuant = new ArrayList<Double>();
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
		for(int c=0; c<subItems.size(); c++){
			price += subItems.get(c).getPrice()*ingQuant.get(c);
		}
		return price;
	}
	
	/** Adds Ingredient with default Quantity 1 */
	public void addSub(Ingredient subItem){
		this.subItems.add(subItem);
		this.ingQuant.add(1.0);
	}
	
	public void addIngredient(Ingredient subItem, double quantity){
		this.subItems.add(subItem);
		this.ingQuant.add(quantity);
	}
	
	public void removeSub(Ingredient subItem){
		for(int c=0; c<subItems.size(); c++){
			if(subItems.get(c).equals(subItem)){
				subItems.remove(c);
				ingQuant.remove(c);
				return;
			}
		}
	}
	
	public void removeSub(int index){
		subItems.remove(index);
		ingQuant.remove(index);
	}
	
}
