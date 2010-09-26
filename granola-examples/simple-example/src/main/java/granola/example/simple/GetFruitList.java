package granola.example.simple;

import java.util.LinkedList;
import java.util.List;

import granola.functional.Function;

public class GetFruitList implements Function<List<Fruit> >{
	
	public static List<Fruit> fruits = new LinkedList<Fruit> ();
	static {
		fruits.add(new Fruit("apple.jpg"));
		fruits.add(new Fruit("lemon.jpg"));
		fruits.add(new Fruit("banana.jpg"));
	}
	@Override
	public List<Fruit> call(Object o) throws Throwable {
		return fruits;
	}
}