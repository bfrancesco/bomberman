package gioco.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import gioco.model.Explosion;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ExplosionView {

	public ExplosionView() {
		Resources.loadExplosionImages();
	}

	public synchronized Image get(int type, int dur, int dir) {
		switch (type) {
		case Explosion.CENTRAL:
			return Resources.center.get(((Resources.center.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		case Explosion.MIDDLE:
			if (dir == Settings.UP)
				return Resources.middle_up.get(((Resources.middle_up.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.RIGHT)
				return Resources.middle_right.get(((Resources.middle_right.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.DOWN)
				return Resources.middle_down.get(((Resources.middle_down.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.LEFT)
				return Resources.middle_left.get(((Resources.middle_left.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		case Explosion.LAST:
			if (dir == Settings.UP)
				return Resources.end_up.get(((Resources.end_up.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.RIGHT)
				return Resources.end_right.get(((Resources.end_right.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.DOWN)
				return Resources.end_down.get(((Resources.end_down.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.LEFT)
				return Resources.end_left.get(((Resources.end_left.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		}

		return null;
	}
}
