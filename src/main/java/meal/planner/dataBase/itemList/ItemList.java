package meal.planner.dataBase.itemList;

import java.util.ArrayList;

import meal.planner.dataBase.items.Item;

/** This class is the abstract version of a wrapper class for easier handling of Meal, Recipe & Ingredient Lists */
public abstract class ItemList<T extends Item, S> {
	
	/** Highest Id currently in use */
	private int highestId;
	/** A List storing all the Ids that aren't in use anymore */
	private ArrayList<Integer> unusedIds;
	/** The actual List Of Items */
	private ArrayList<T> items;
	
	/** Constructs new ItemList */
	public ItemList(){
		this.items = new ArrayList<T>();
	}
	
	/** Adds A new Item to the list */
	public void add(T item){
		items.add(item);
		if(unusedIds.size()>0){
			item.setId(unusedIds.get(0));
			unusedIds.remove(0);
		}
		else{
			item.setId(highestId+1);
			this.highestId++;
		}
	}
	
	/** Returns a list containing all the items that as a substring have the given name */
	public ArrayList<T> findName(String name){
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			if(t.getName().contains(name)){
				output.add(t);
			}
		}
		return output;
	}
	
	/** Returns the item with the given id */
	public T findId(int id){
		for(T t : items){
			if(t.getId()==id){
				return t;
			}
		}
		return null;
	}
	
	/** Returns a list containing all the items that as a sub component have the given component */
	public ArrayList<T> findPart(S part){
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			if(t.contains(part)){
				output.add(t);
			}
		}
		return output;
	}
	
	/** Returns a list containing all the items that have a price between min and max price (-1 will be evaluated as no cap in this direction)*/
	public ArrayList<T> findPrice(float minPrice, float maxPrice){
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			float price = t.getPrice();
			if((price>=minPrice || minPrice==-1) && (price<=maxPrice || maxPrice==-1)){
				output.add(t);
			}
		}
		return output;
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<T> findAll(String name, float minPrice, float maxPrice, S ... part){
		return null;
	}
	
}
