import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new MyGame());
			app.setDisplayMode(800, 600, false); // Mode fenêtré
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
