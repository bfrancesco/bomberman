package gioco.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gioco.controller.PlayerController;
import gioco.model.Block;
import gioco.model.Bomb;
import gioco.model.Enemy;
import gioco.model.Enemy1;
import gioco.model.Enemy2;
import gioco.model.Enemy3;
import gioco.model.Explosion;
import gioco.model.Player;
import gioco.utilities.Settings;

public class ChangeablePanel extends JPanel {

	private static final long serialVersionUID = 971861536905109107L;
	private PlayerController controller;
	private Image brick;
	private ExplosionView explosions;
	private BombView bombs;
	private BombermanView player1;

	public ChangeablePanel(PlayerController controller) {
		this.controller = controller;
		this.setOpaque(false);
		try {
			brick = ImageIO.read(getClass().getResourceAsStream("/gioco/resources/blocks/brick.jpg"));
		} catch (IOException e) {
			System.out.println("CANNOT FIND BRICK RESOURCE");
		}
		player1 = new BombermanView();
		explosions = new ExplosionView();
		bombs = new BombView();
	}

	public void update() {
		player1.update(controller.getGioco().getPlayer1().getState());
		repaint();
	}

	@Override
	protected synchronized void paintComponent(Graphics g) {

		for (int i = 0; i < controller.getGioco().getHeight(); i++)
			for (int j = 0; j < controller.getGioco().getWidth(); j++) {
				switch (controller.getGioco().getElement(i, j).getType()) {
				case Block.BRICK:
					g.drawImage(brick.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_FAST),
							i * Settings.BLOCKSIZEX, j * Settings.BLOCKSIZEY, Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							null);
					break;
				}
			}
		g.setColor(Color.MAGENTA);
		for (Enemy b : controller.getGioco().getEnemies()) {
			if (b instanceof Enemy1) {
				g.setColor(Color.MAGENTA);
				g.fillRect(b.getX() , b.getY() , b.getWidth(),
						b.getHeight());
			} else if (b instanceof Enemy2) {
				g.setColor(Color.PINK);
				g.fillRect(b.getX(), b.getY(), b.getWidth(),
						b.getHeight());
			} else if (b instanceof Enemy3) {
				if (((Enemy3) b).isVisible()) {
					g.setColor(Color.ORANGE);
					g.fillRect(b.getX(), b.getY() , b.getWidth(),
							b.getHeight());
				} else {
					g.setColor(Color.ORANGE);
					if (((Enemy3) b).getUnseenTime() < 80 && ((Enemy3) b).getUnseenTime() % 20 >= 0
							&& ((Enemy3) b).getUnseenTime() % 20 < 5)
						g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight());
				}
			}
		}
		
		Vector<Bomb> tmpB= new Vector<Bomb>(controller.getGioco().getBombs());
		for (Bomb b : tmpB) {
			g.drawImage( bombs.get(b.getTimer()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
					b.getX() * Settings.BLOCKSIZEX, b.getY() * Settings.BLOCKSIZEY, null);
		}
		
		
		
		Vector<Explosion> tmpE= new Vector<Explosion>(controller.getGioco().getExplosions());
		for (Explosion b : tmpE) {
			g.drawImage( explosions.get(b.getType(),b.getDurata() , b.getDirection()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
					b.getX() * Settings.BLOCKSIZEX, b.getY() * Settings.BLOCKSIZEY, null);
		}
		
		Player p1 = controller.getGioco().getPlayer1();
		g.drawImage(player1.getCurrentImage().getScaledInstance(p1.getWidth(), p1.getHeight(), Image.SCALE_FAST),
				p1.getX(), p1.getY(), p1.getWidth(), Settings.BLOCKSIZEY-2, null);	
	}

}
