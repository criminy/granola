package granola.example.simple;

public class Fruit {

	private String name;
	private String filename;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Fruit(int id,String filename) {
		this.id = id;
		this.filename = filename;
		this.name = filename.substring(0,filename.length()-4);
		this.name = this.name.substring(0,1).toUpperCase() + 
					this.name.substring(1,this.name.length());
	}
	
	
	
}
