package granola.example.simple;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import granola.functional.Function;

public class GetFruitList implements Function<List<Fruit> >{
	
	public static List<Fruit> fruits = new LinkedList<Fruit> ();
	static {
		
		URL fruitsUrl = GetFruitList.class.getClassLoader().getResource("media/img");
		
		File f = new File(fruitsUrl.getFile());
		Collection<File> c = FileUtils.listFiles(f,new String[]{"jpg"},false);
		
		int idx = 0;
		for(File fx : c)
		{
			fruits.add(new Fruit(idx++,fx.getName()));
		}
			//fruits.add(new Fruit("cherries.jpg"));
			//fruits.add(new Fruit("lemon.jpg"));
			//fruits.add(new Fruit("banana.jpg"));
	}
	
	
	@Override
	public List<Fruit> call(Object o) throws Throwable {
		return fruits;
	}
}