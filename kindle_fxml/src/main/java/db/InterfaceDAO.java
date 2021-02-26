package db;

import java.util.ArrayList;

public interface InterfaceDAO<T> {
	
	public void add(T reference);
	
	public void remove(T reference);
	
	public ArrayList<T> list();
	
}
