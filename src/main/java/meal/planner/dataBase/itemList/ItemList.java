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
		assert isOk();
	}
	
	/** Adds A new Item to the list */
	public void add(T item){
		assert isOk();
		items.add(item);
		item.setId(highestAvailableId);
		this.highestAvailableId++;
		assert isOk();
	}
	
	/** Removes item within this list of the given id */
	public void remove(int id){
		assert isOk();
		for(int c=0; c<items.size(); c++){
			T t = items.get(c);
			if(t.getId()==id){
				items.remove(c);
				assert isOk();
				return;
			}
		}
		assert isOk();
	}
	
	/** Returns a list containing all the items that as a substring have the given name */
	public ArrayList<T> findName(String name){
		assert isOk();
		return findName(name, items);
	}
	
	/** Returns a list containing all the items that as a substring have the given name */
	private ArrayList<T> findName(String name, ArrayList<T> items){
		assert isOk();
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			if(t.getName().equals(name)){
				output.add(t);
			}
		}
		assert isOk();
		return output;
	}
	
	/** Returns the item with the given id */
	public T findId(long id) {
		assert isOk();
		return findId(id, items);
	}
	
	/** Returns the item with the given id */
	private T findId(long id, ArrayList<T> items) {
		assert isOk();
		for(T t : items){
			if(t.getId()==id){
				return t;
			}
		}
		assert isOk();
		return null;
	}
	
	/** Returns a list containing all the items that as a sub component have the given component */
	public ArrayList<T> findPart(S part){
		assert isOk();
		return findPart(part, items);
	}
	
	/** Returns a list containing all the items that as a sub component have the given component */
	public ArrayList<T> findPart(S part, ArrayList<T> items){
		assert isOk();
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			if(t.contains(part)){
				output.add(t);
			}
		}
		assert isOk();
		return output;
	}
	
	/** Returns a list containing all the items that have a price between min and max price (-1 will be evaluated as no cap in this direction)*/
	public ArrayList<T> findPrice(float minPrice, float maxPrice){
		assert isOk();
		return findPrice(minPrice, maxPrice, items);
	}
	
	/** Returns a list containing all the items that have a price between min and max price (-1 will be evaluated as no cap in this direction)*/
	public ArrayList<T> findPrice(float minPrice, float maxPrice, ArrayList<T> items){
		assert isOk();
		ArrayList<T> output = new ArrayList<T>();
		for(T t : items){
			double price = t.getPrice();
			if((price>=minPrice || minPrice==-1) && (price<=maxPrice || maxPrice==-1)){
				output.add(t);
			}
		}
		assert isOk();
		return output;
	}
	
	/** Finds All items that meet all the given requirements, given null or -1 will be evaluated to not matter for the search */
	public ArrayList<T> findAll(String name, float minPrice, float maxPrice, S ... parts){
		assert isOk();
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
		assert isOk();
		return output;
	}
	
	/** Returns a list of all items sorted according to id */
	public ArrayList<T> getAll(){
		assert isOk();
		return (ArrayList<T>)items.clone();
	}
	
	/** Returns a list of all items sorted according to name */
	public ArrayList<T> getAllSortedByName(){
		assert isOk();
		ArrayList<T> output = (ArrayList<T>)items.clone();
		for(int c=0; c<output.size(); c++){
			for(int c2=c+1; c2<output.size(); c2++){
				if(output.get(c2).getName().compareTo(output.get(c).getName())<0){
					T help = output.get(c);
					output.set(c, output.get(c2));
					output.set(c2, help);
				}
			}
		}
		assert isOk();
		return output;
	}
	
	/** Assertion Method */
	protected boolean isOk(){
		if(items==null){
			return false;
		}
		for(Item i : items){
			if(i==null || i.getId()>=highestAvailableId){
				return false;
			}
		}
		return true;
	}
	
}
