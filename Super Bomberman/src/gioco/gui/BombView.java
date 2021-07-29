package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;


public class BombView {
	private Vector<Image> steps;
	
	public BombView() {
		steps = new Vector<Image>();
		try {

			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO
						.read(getClass().getResourceAsStream("/gioco/resources/bombs/bomb_" + i + ".png"));
				steps.add(img);
			}
	}
		catch (IOException e) {
			System.out.println("BOMB RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}

	public Image get( int dur) {
		return steps.get(dur/6 % 3);
	}
}
