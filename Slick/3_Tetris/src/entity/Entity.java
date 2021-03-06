package entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Entity {
	
	protected String path;
	protected Image image;
	
	protected float x;
	protected float y;
	
	protected float scale;
	
	/* ************ *
	 * Constructors *
	 * ************ */
	
	/**
	 * Initialise les attributs avec des valeurs par defaut
	 */
	protected void initAttributes() {
		this.x = 0;
		this.y = 0;
		
		this.scale = 1.0f;
	}
	
	public Entity(Image image) {
		this.image = image;
		this.initAttributes();
	}
	
	public Entity(String path) {
		
		this.path = path;
		
		try {
			this.image = new Image(path);
		} catch (SlickException e) {
			System.err.println("Couldn't load image");
		}
		
		initAttributes();
	}
	
	public Entity(String path, float x, float y) {
		this(path);
		this.x = x;
		this.y = y;
	}
	
	public Entity(String path, float x, float y, float scale) {
		this(path,x,y);
		this.scale = scale;
	}
	
	/* **************** *
	 * Public Functions *
	 * **************** */
	public void draw() {
		image.draw(x, y, scale);
	}
	
	/**
	 * @return True si les coordonnees (x,y) sont a l'interieur de l'entite. False sinon.
	 */
	public boolean isInside(float coordX, float coordY) {
		return ( coordX >= x && coordX <= x + image.getWidth()) &&
			   ( coordY >= y && coordY <= y + image.getHeight());
	}
	
	public void sub(int x, int y, int width, int height) {
		this.image = this.image.getSubImage(x, y, width, height);
	}
	
	/*
	public Entity subEntity(int x, int y, int width, int height) {
		Entity entity = this.clone();
		entity.image = entity.image.getSubImage(x, y, width, height);
		return entity;
	}
	
	public Entity clone() {
		Entity entity = new Entity(this.path,this.x,this.y, this.scale);
		entity.image = this.image.copy();
		return entity;
	}
	*/
	
	/* ******* *
	 * Setters *
	 * ******* */
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
	/* ******* *
	 * Getters *
	 * ******* */
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
	
	public float getScale() {
		return this.scale;
	}
	
	/* ********* *
	 * To String *
	 * ********* */
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("===== Entity =====").append("\n");
		sb.append("Path : ").append(path).append("\n");
		sb.append("X : ").append(x).append("\n");
		sb.append("Y : ").append(y).append("\n");
		sb.append("Width : ").append(image.getWidth()).append("\n");
		sb.append("Height : ").append(image.getHeight()).append("\n");
		sb.append("Scale : ").append(scale);
		return sb.toString();
	}

}
