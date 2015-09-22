package meal.planner.dataBase;

public enum IngAttribute {
	MEAT(),
	PORK(MEAT),
	BEEF(MEAT),
	CHICKEN(MEAT),
	PEANUT();
	
	private IngAttribute parent;
	private IngAttribute(){
		this.parent = null;
	}
	private IngAttribute(IngAttribute parent){
		this.parent = parent;
	}
	public boolean hasParent(){
		return parent!=null;
	}
	public IngAttribute getParent(){
		return parent;
	}
	
}
