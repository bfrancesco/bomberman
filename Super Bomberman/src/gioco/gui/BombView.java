package gioco.gui;

import java.awt.Image;
import gioco.utilities.Resources;


public class BombView {
	
	public BombView() {
		Resources.loadBombImages();
	}

	public Image get( int dur) {
		return Resources.bombSteps.get(dur/6 % 3);
	}
}
