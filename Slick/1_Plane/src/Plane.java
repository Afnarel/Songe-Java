import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Plane {
	
	private String path;
	private float x;
	private float y;
	private float scale;
	
	private Image image;
	
	public Plane() {
		path = "img/plane.png";
		x = 400;
		y = 300;
		scale = 1.0f;
		try {
			this.image = new Image(path);
		} catch (SlickException e) {
			System.err.println("Couldn't load plane image");
		}
	}
	
	public void draw() {
		image.draw(x, y, scale);
	}
	
	public void rotate(int delta) {
		image.rotate(0.2f * delta);
	}
	
	public void moveForward(int delta) {
		float hip = 0.4f * delta;

		float rotation = image.getRotation();

		x += hip * Math.sin(Math.toRadians(rotation));
		y -= hip * Math.cos(Math.toRadians(rotation));
	}
	
	public void reduce() {
		scale -= (scale <= 1.0f) ? 0 : 0.1f;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}
	
	public void enlarge() {
		scale += (scale >= 5.0f) ? 0 : 0.1f;
		image.setCenterOfRotation(image.getWidth() / 2.0f * scale,
				image.getHeight() / 2.0f * scale);
	}

}
