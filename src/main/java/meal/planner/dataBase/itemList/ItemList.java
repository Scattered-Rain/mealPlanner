package meal.planner.dataBase.itemList;

import java.util.ArrayList;

import meal.planner.dataBase.items.Item;

/** This class is the abstract version of a wrapper class for easier handling of Meal, Recipe & Ingredient Lists */
public class ItemList<T extends Item, S> {
	
	/** Highest Id currently in use */
	private long highestAvailableId;
	/** The actual List Of Items */
	private ArrayList<T> items;
	
	/** Constructs new ItemList */
	public ItemList(){
		this.items = new ArrayList<T>();
	}
	
	/** Adds A new Item to the list */
	public void add(T item){
		items.add(item);
		item.setId(highestAvailableId);
		this.highestAvailableId++;
	}
	
	/** Removes item within this list of the given id */
	public void remove(int id){
		for(int c=0; c<items.size(); c++){
			T t = items.get(c);
			if(t.getId()==id){
				items.remove(c);
				return;
			}
		}
	}
	
	/** Returns a list containing all the items that as a substring have the given name */
	public ArrayList<T> findName(String name){
		return findName(name, items);
	}
	
	/** Returns a list containing all the items that as a substring have the given name */
	private ArrayList<T> findName(String name, ArrayList<T> items){
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
		return findId(id, items);
	}
	
	/** Returns the item with the given id */
	private T findId(int id, ArrayList<T> items){
		for(T t : items){
			if(t.getId()==id){
				return t;
			}
		}
		return null;
	}
	
	/** Returns a list containing all the items that as a sub component have the given component */
	public ArrayList<T> findPart(S part){
		return findPart(part, items);
	}
	
	/** Returns a list containing all the items that as a sub component have the given component */
	public ArrayList<T> findPart(S part, ArrayList<T> items){
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
		return findPrice(minPrice, maxPrice, items);
	}
	
	/** Returns a list containing all the items that have a price between min and max price (-1 will be evaluated as no cap in this direction)*/
	public ArrayList<T> findPrice(float minPrice, float maxPrice, ArrayList<T> items){
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
	public ArrayList<T> findAll(String name, float minPrice, float maxPrice, S ... parts){
		ArrayList<T> output = items;
		if(name!=null){
			output = findName(name, output);
		}
		if(parts!=null && parts.length>1 && parts[0]!=null){
			for(S s : parts){
				output = findPart(s, output);
			}
		}
		output = findPrice(minPrice, maxPrice, output);
		return output;
	}
	
}
