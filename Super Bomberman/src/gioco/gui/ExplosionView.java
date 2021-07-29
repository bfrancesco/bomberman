package gioco.gui;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import gioco.model.Explosion;
import gioco.utilities.Settings;

public class ExplosionView {
	private Vector<Image> center;
	private Vector<Image> middle_up;
	private Vector<Image> middle_down;
	private Vector<Image> middle_right;
	private Vector<Image> middle_left;
	private Vector<Image> end_up;
	private Vector<Image> end_down;
	private Vector<Image> end_right;
	private Vector<Image> end_left;

	public ExplosionView() {
		center = new Vector<Image>();
		end_up = new Vector<Image>();
		middle_up = new Vector<Image>();
		end_right = new Vector<Image>();
		middle_right = new Vector<Image>();
		end_down = new Vector<Image>();
		middle_down = new Vector<Image>();
		end_left = new Vector<Image>();
		middle_left = new Vector<Image>();
		try {

			for (int i = 0; i < 4; ++i) {
				center.add(ImageIO
						.read(getClass().getResourceAsStream("/gioco/resources/explosion/center/center_" + i + ".png")));
				middle_up.add(ImageIO.read(
						getClass().getResourceAsStream("/gioco/resources/explosion/middle/middle_up_" + i + ".png")));
				end_up.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/end/end_up_" + i + ".png")));
				middle_right.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/middle/middle_right_" + i + ".png")));
				end_right.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/end/end_right_" + i + ".png")));
				middle_down.add(ImageIO.read(
						getClass().getResourceAsStream("/gioco/resources/explosion/middle/middle_down_" + i + ".png")));
				end_down.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/end/end_down_" + i + ".png")));
				middle_left.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/middle/middle_left_" + i + ".png")));
				end_left.add(ImageIO.read(getClass().getResourceAsStream("/gioco/resources/explosion/end/end_left_" + i + ".png")));
			}

		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("EXPLOSIONS RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}

	}

	public synchronized Image get(int type, int dur, int dir) {
		switch (type) {
		case Explosion.CENTRAL:
			return center.get(((center.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		case Explosion.MIDDLE:
			if (dir == Settings.UP)
				return middle_up.get(((middle_up.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.RIGHT)
				return middle_right.get(((middle_right.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.DOWN)
				return middle_down.get(((middle_down.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.LEFT)
				return middle_left.get(((middle_left.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		case Explosion.LAST:
			if (dir == Settings.UP)
				return end_up.get(((end_up.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.RIGHT)
				return end_right.get(((end_right.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.DOWN)
				return end_down.get(((end_down.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
			if (dir == Settings.LEFT)
				return end_left.get(((end_left.size()-1) * (Settings.EXPLOSIONTIME-dur)) / Settings.EXPLOSIONTIME);
		}

		return null;
	}
}
