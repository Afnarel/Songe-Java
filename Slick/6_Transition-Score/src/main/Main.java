package main;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	public static void main(String[] args){
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Hoorah());
			app.setDisplayMode(1024, 768, false); // Mode fenêtré
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}