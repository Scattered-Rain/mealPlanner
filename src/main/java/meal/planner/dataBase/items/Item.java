package meal.planner.dataBase.items;

/** Abstract class that is the base for Ingrediants, Meals and Recipes */
public abstract class Item<S> {
	
	private int id;
	private String name;
	private String description;
	
	public Item(int id){
		this.id = id;
		this.name = "";
		this.description = "";
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getName(){
		return name;
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
	
	public abstract boolean contains(S s);
	
	public abstract float getPrice();

}
