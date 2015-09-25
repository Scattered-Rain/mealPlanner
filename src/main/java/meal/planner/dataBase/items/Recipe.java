package meal.planner.dataBase.items;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe extends Item<Ingredient>{
	
	/** This Array Keeps track of the amount of an ingredient that has to be used here, the ingredients and amounts are coupled together via the index */
	private ArrayList<Double> ingQuant;
	
	private HashMap<Ingredient, Double> ings;
	
	protected boolean isOk(){
		if(!super.isOk()){
			return false;
		}
		if(ings==null || ingQuant==null){
			return false;
		}
		if(!(ings.size()==subItems.size() && subItems.size()==ings.size())){
			return false;
		}
		for(Double d : ingQuant){
			if(d==null){
				return false;
			}
		}
		return true;
	}
	
	public Recipe(int id){
		super(id);
		this.ingQuant = new ArrayList<Double>();
		this.ings = new HashMap<Ingredient, Double>();
		assert isOk();
	}
	
	/** Returns whether this Recipe contains the given ingredient */
	public boolean contains(Ingredient ing){
		assert isOk();
		for(Ingredient i : subItems){
			if(i.getId()==ing.getId()){
				return true;
			}
		}
		assert isOk();
		return false;
	}
	
	/** Returns the price of this Recipe */
	public float getPrice(){
		assert isOk();
		float price = 0;
		for(int c=0; c<subItems.size(); c++){
			price += subItems.get(c).getPrice()*ingQuant.get(c);
		}
		assert isOk();
		return price;
	}
	
	/** Returns the entire Hash Map of Ingredients */
	public HashMap<Ingredient, Double> getHashIngs(){
		assert isOk();
		return ings;
	}
	
	/** Adds Ingredient with default Quantity 1 */
	public void addSub(Ingredient subItem){
		assert isOk();
		this.subItems.add(subItem);
		this.ingQuant.add(1.0);
		this.ings.put(subItem, 1.0);
		assert isOk();
	}
	
	public void addIngredient(Ingredient subItem, double quantity){
		assert isOk();
		this.subItems.add(subItem);
		this.ingQuant.add(quantity);
		this.ings.put(subItem, quantity);
		assert isOk();
	}
	
	public void removeSub(Ingredient subItem){
		assert isOk();
		for(int c=0; c<subItems.size(); c++){
			if(subItems.get(c).equals(subItem)){
				subItems.remove(c);
				ingQuant.remove(c);
				this.ings.remove(subItem);
				return;
			}
		}
		assert isOk();
	}
	
	public void removeSub(int index){
		assert isOk();
		Ingredient i = subItems.get(index);
		ings.remove(i);
		subItems.remove(index);
		ingQuant.remove(index);
		assert isOk();
	}
	
}
