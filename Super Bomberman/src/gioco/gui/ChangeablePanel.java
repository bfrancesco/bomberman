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
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ChangeablePanel extends JPanel {

	private static final long serialVersionUID = 971861536905109107L;
	private PlayerController controller;
	private Image brick;
	private ExplosionView explosions;
	private BombView bombs;
	private Vector<EnemyView> enemyview;
	private BombermanView player1;
	private BombermanView player2;

	public ChangeablePanel(PlayerController controller) {
		this.controller = controller;
		this.setOpaque(false);
	
		brick = Resources.brick;

		player1 = new BombermanView(Settings.PLAYER1);
		if(controller.isMultiplayer())
			player2 = new BombermanView(Settings.PLAYER2);
		explosions = new ExplosionView();
		bombs = new BombView();
		enemyview= new Vector<EnemyView>();
		for(int i = 0; i < controller.getGioco().getEnemies().size();++i) {
			int id = controller.getGioco().getEnemies().get(i).getID();
			if(controller.getGioco().getEnemies().get(i) instanceof Enemy1)
				enemyview.add(new EnemyView(1 , id));
			else if(controller.getGioco().getEnemies().get(i) instanceof Enemy3)
				enemyview.add(new EnemyView(3,id));
			else 	enemyview.add(new EnemyView(2,id));
		}
		
	}

	public void update() {
		player1.update(controller.getGioco().getPlayer1().getState());
		if(controller.isMultiplayer())
			player2.update(controller.getGioco().getPlayer2().getState());
		updateEnemies();
		repaint();
	}
		
	private void updateEnemies() {
		Vector<EnemyView> enemyViewToBeRemoved = new Vector<EnemyView>();
		for(int i = 0; i < controller.getGioco().getEnemies().size();++i) {
			enemyview.get(i).update(controller.getGioco().getEnemies().get(i).getState());
			boolean trovato = false;
			for(Enemy e : controller.getGioco().getEnemies()) {
				if(e.getID() == enemyview.get(i).getId() )
					trovato = true;
			}
			if(!trovato)
				enemyViewToBeRemoved.add(enemyview.get(i));
		}
		enemyview.removeAll(enemyViewToBeRemoved);
	}
	
	@Override
	protected synchronized void paintComponent(Graphics g) {

		for (int i = 0; i < controller.getGioco().getHeight(); i++)
			for (int j = 0; j < controller.getGioco().getWidth(); j++) {
				switch (controller.getGioco().getElement(i, j).getType()) {
				case Block.BRICK:
					g.drawImage(brick.getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_FAST),
							j * Settings.BLOCKSIZEX, i * Settings.BLOCKSIZEY, Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							null);
					break;
				}
			}
		
		
		Vector<Bomb> tmpB= new Vector<Bomb>(controller.getGioco().getBombs());
		for (Bomb b : tmpB) {
			g.drawImage( bombs.get(b.getTimer()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
					b.getXCell() * Settings.BLOCKSIZEX, b.getYCell() * Settings.BLOCKSIZEY, null);
		}
		
		
		
		Vector<Explosion> tmpE= new Vector<Explosion>(controller.getGioco().getExplosions());
		for (Explosion b : tmpE) {
			g.drawImage( explosions.get(b.getType(),b.getDurata() , b.getDirection()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
					b.getXCell() * Settings.BLOCKSIZEX, b.getYCell() * Settings.BLOCKSIZEY, null);
		}
		
		for (int i = 0;i<controller.getGioco().getEnemies().size();++i) {
			Enemy b = controller.getGioco().getEnemies().get(i);
			if (b instanceof Enemy1) {
				g.drawImage(enemyview.get(i).getCurrentImage().getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_FAST),
						b.getX(), b.getY() , null);	
			} else if (b instanceof Enemy2) {
				g.drawImage(enemyview.get(i).getCurrentImage().getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_FAST),
						b.getX(), b.getY() , null);
			} else if (b instanceof Enemy3) {
				if (((Enemy3) b).isVisible()) {
					g.drawImage(enemyview.get(i).getCurrentImage().getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_FAST),
							b.getX(), b.getY() , null);
				} else {
					if (((Enemy3) b).getUnseenTime() < 80 && ((Enemy3) b).getUnseenTime() % 20 >= 0
							&& ((Enemy3) b).getUnseenTime() % 20 < 5)
						g.drawImage(enemyview.get(i).getCurrentImage().getScaledInstance(b.getWidth(), b.getHeight(), Image.SCALE_FAST),
								b.getX(), b.getY() , null);
				}
			}
		}
		
		Player p1 = controller.getGioco().getPlayer1();
		g.drawImage(player1.getCurrentImage().getScaledInstance(p1.getWidth(), p1.getHeight(), Image.SCALE_FAST),
				p1.getX(), p1.getY(), p1.getWidth(),  p1.getHeight(), null);	
		if(controller.getGioco().isMultiplayer()) {
			Player p2 = controller.getGioco().getPlayer2();
			g.drawImage(player2.getCurrentImage().getScaledInstance(p2.getWidth(), p2.getHeight(), Image.SCALE_FAST),
					p2.getX(), p2.getY(), p2.getWidth(),  p2.getHeight(), null);	
		}
	}

}
