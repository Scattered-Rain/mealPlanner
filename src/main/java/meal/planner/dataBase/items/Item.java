package meal.planner.dataBase.items;

import java.util.ArrayList;

import lombok.Getter;

/** Abstract class that is the base for Ingredients, Meals and Recipes 
 * S here represents a Sub-Part, for example S=Ingredients for Recipes */
public abstract class Item<S> {
	
	/** The Id of this Item */
	private long id;
	/** The Name of this item */
	private String name;
	/** The Description of this item */
	private String description;
	/** The list of sub items this item contains */
	@Getter
	protected ArrayList<S> subItems;
	
	/** Construct new Item */
	public Item(long id) {
		this.id = id;
		this.name = "";
		this.description = "";
		this.subItems = new ArrayList<S>();
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	/** Returns whether this Item matches the given Item, done based solely on name and type */
	public boolean matches(Item item){
		return name.equals(item);
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	/** Returns whether this Item contains the given SubItem */
	public abstract boolean contains(S s);
	
	/** Returns the price of this item, as scaled to one person */
	public abstract double getPrice();

}
