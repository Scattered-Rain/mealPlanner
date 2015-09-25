package meal.planner.dataBase.items;

import java.util.ArrayList;

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
	protected ArrayList<S> subItems;
	
	
	protected boolean isOk(){
		if(name==null){
			return false;
		}
		if(description==null){
			return false;
		}
		if(subItems==null){
			return false;
		}
		for(S s : subItems){
			if(s==null){
				return false;
			}
		}
		return true;
	}
	
	
	/** Construct new Item */
	public Item(int id){
		this.id = id;
		this.name = "";
		this.description = "";
		this.subItems = new ArrayList<S>();
		assert isOk();
	}
	
	public long getId(){
		assert isOk();
		return id;
	}
	
	public void setId(long id){
		assert isOk();
		this.id = id;
	}
	
	public String getName(){
		assert isOk();
		return name;
	}
	
	/** Returns whether this Item matches the given Item, done based solely on name and type */
	public boolean matches(Item item){
		assert isOk();
		return name.equals(item);
	}
	
	public String getDescription(){
		assert isOk();
		return description;
	}
	
	public void setName(String name){
		assert isOk();
		this.name = name;
	}
	
	public void setDescription(String description){
		assert isOk();
		this.description = description;
	}
	
	public void addSub(S subItem){
		assert isOk();
		this.subItems.add(subItem);
	}
	
	public void removeSub(S subItem){
		assert isOk();
		for(int c=0; c<subItems.size(); c++){
			if(subItems.get(c).equals(subItem)){
				subItems.remove(c);
				return;
			}
		}
		assert isOk();
	}
	
	public void removeSub(int index){
		assert isOk();
		subItems.remove(index);
		assert isOk();
	}
	
	public ArrayList<S> getSubs(){
		assert isOk();
		return (ArrayList<S>)subItems.clone();
	}
	
	/** Returns whether this Item contains the given SubItem */
	public abstract boolean contains(S s);
	
	/** Returns the price of this item, as scaled to one person */
	public abstract float getPrice();
	
	public boolean equals(Object item){
		assert isOk();
		if(item instanceof Item){
			if(this.getId()==((Item)item).getId()){
				return true;
			}
		}
		assert isOk();
		return false;
	}

}
