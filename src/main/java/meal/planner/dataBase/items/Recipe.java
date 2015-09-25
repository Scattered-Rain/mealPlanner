package meal.planner.dataBase.items;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe extends Item<Ingredient>{
	
	/** This Array Keeps track of the amount of an ingredient that has to be used here, the ingredients and amounts are coupled together via the index */
	private ArrayList<Double> ingQuant;
	
	private HashMap<Ingredient, Double> ings;
	
	public Recipe(int id){
		super(id);
		this.ingQuant = new ArrayList<Double>();
		this.ings = new HashMap<Ingredient, Double>();
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
	
	/** Returns the entire Hash Map of Ingredients */
	public HashMap<Ingredient, Double> getHashIngs(){
		return ings;
	}
	
	/** Adds Ingredient with default Quantity 1 */
	public void addSub(Ingredient subItem){
		this.subItems.add(subItem);
		this.ingQuant.add(1.0);
		this.ings.put(subItem, 1.0);
	}
	
	public void addIngredient(Ingredient subItem, double quantity){
		this.subItems.add(subItem);
		this.ingQuant.add(quantity);
		this.ings.put(subItem, quantity);
	}
	
	public void removeSub(Ingredient subItem){
		for(int c=0; c<subItems.size(); c++){
			if(subItems.get(c).equals(subItem)){
				subItems.remove(c);
				ingQuant.remove(c);
				this.ings.remove(subItem);
				return;
			}
		}
	}
	
	public void removeSub(int index){
		Ingredient i = subItems.get(index);
		ings.remove(i);
		subItems.remove(index);
		ingQuant.remove(index);
	}
	
}
