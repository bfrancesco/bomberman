package gioco.gui;

import java.awt.Image;

import gioco.model.Explosion;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class BrickView {
	public synchronized Image get(int explosionTime) {
		return Resources.bricks.get(((Resources.bricks.size()-1) * (Settings.BRICKEXPLOSIONTIME-explosionTime)) / (Settings.BRICKEXPLOSIONTIME));
	}
}
