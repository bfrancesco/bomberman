package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.JPanel;


import gioco.controller.PlayerController;
import gioco.model.Bomb;
import gioco.model.Brick;
import gioco.model.Enemy;
import gioco.model.Enemy1;
import gioco.model.Enemy2;
import gioco.model.Enemy3;
import gioco.model.Explosion;
import gioco.model.Player;
import gioco.model.PowerUp;
import gioco.net.Client;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

//pannello che permette di visualizzare le entità e gli oggetti modificabili in generale del gioco
//la mappa statica non è gestita dal panel
//contiene 5 bombermanview , uno per ogni player 
//il gioco non supporta infatti più di 5 player 
// Un vector di view di nemici e tutti gli oggetti che permettono di eseguire l'animazione degli elementi del gioco
//il gioco si sviluppa su una LogicSize fittizia , l'entities panel permette di scalare in proporzione alla dimensione della finestra 
// i metodi update e paintComponent permettono di aggiornare con i nuovi stati ed elementi per poi disegnarli
public class EntitiesPanel extends JPanel {

	private static final long serialVersionUID = 971861536905109107L;
	private PlayerController controller;
	private ExplosionView explosions;
	private BrickView bricks;
	private BombView bombs;
	private Vector<EnemyView> enemyview;
	private BombermanView player1;
	private BombermanView player2;
	private BombermanView player3;
	private BombermanView player4;
	private BombermanView player5;

	public EntitiesPanel(PlayerController controller) {
		this.controller = controller;
		this.setOpaque(false);
		player1 = new BombermanView(Settings.selectedbomberman);
		this.setLayout(new FlowLayout());
		if (controller.isMultiplayer()) {
			player1.setColor(Settings.WHITE);
			player2 = new BombermanView(Settings.BLACK);
			if (controller.getGioco().isBattleRoyale()) {
				player3 = new BombermanView(Settings.ORANGE);
				player4 = new BombermanView(Settings.BLUE);
				player5 = new BombermanView(Settings.GREEN);
			}
		}
		bricks = new BrickView();
		explosions = new ExplosionView();
		bombs = new BombView();
		enemyview = new Vector<EnemyView>();
		for (int i = 0; i < controller.getGioco().getEnemies().size(); ++i) {
			int id = controller.getGioco().getEnemies().get(i).getID();
			if (controller.getGioco().getEnemies().get(i) instanceof Enemy1)
				enemyview.add(new EnemyView(1, id));
			else if (controller.getGioco().getEnemies().get(i) instanceof Enemy3)
				enemyview.add(new EnemyView(3, id));
			else
				enemyview.add(new EnemyView(2, id));
		}

	}
	//scala la x dalla dimensione fittizia della logicSize alla dimensione del blocco reale
	private int scaleByX(int a) {
		return a * Settings.BLOCKSIZEX / Settings.LOGICBLOCKSIZEX;
	}
	//scala la y  dalla dimensione fittizia della logicSize alla dimensione del blocco reale
	private int scaleByY(int a) {
		return a * Settings.BLOCKSIZEY / Settings.LOGICBLOCKSIZEY;
	}
	// aggiorna gli stati delle corrispettive view dell'entità (player , nemici)
	//Es: aggiorna lo stato e l'immagine corrente dell'enemyView che corrisponde al nemico x
	// disegna gli aggiornamenti
	public void update() {
		//System.out.println("Player "+Settings.PLAYER1+" "+controller.getGioco().getPlayer(Settings.PLAYER1) );
		player1.update(controller.getGioco().getPlayer(Settings.PLAYER1).getState());
		if (controller.isMultiplayer())
			player2.update(controller.getGioco().getPlayer(Settings.PLAYER2).getState());
		if (controller.getGioco().isBattleRoyale()) {
			player3.update(controller.getGioco().getPlayer(Settings.PLAYER3).getState());
			player4.update(controller.getGioco().getPlayer(Settings.PLAYER4).getState());
			player5.update(controller.getGioco().getPlayer(Settings.PLAYER5).getState());
		}
		updateEnemies();
		repaint();
	}
	
	//se un nemico è morto ed è stato eliminato , allora viene trovata la corrispettiva view per essre eliminata a sua volta
	private void updateEnemies() {
		Vector<EnemyView> enemyViewToBeRemoved = new Vector<EnemyView>();
		for (int i = 0; i < controller.getGioco().getEnemies().size(); ++i) {
			enemyview.get(i).update(controller.getGioco().getEnemies().get(i).getState());
			boolean trovato = false;
			for (Enemy e : controller.getGioco().getEnemies()) {
				if (e.getID() == enemyview.get(i).getId())
					trovato = true;
			}
			if (!trovato)
				enemyViewToBeRemoved.add(enemyview.get(i));
		}
		enemyview.removeAll(enemyViewToBeRemoved);
	}
	
	//tutti i modificabili del gioco vengono disegnati 
	//i bomberman sono spostati ed ingranditi di un fattore3d calcolato dal playerController
	//permette di rendere l'immagine tridimensionale, tuttavia le immagini usate per i bomberman permettono di non avere alcuna distorsione o allungamento considerate  la loro dimensione e forma
	//è nercessario usare un insieme di immagini adatte se usate con il factor3d
	@Override
	protected synchronized void paintComponent(Graphics g) {
		Vector<PowerUp> tmpPowerUp = new Vector<PowerUp>(controller.getGioco().getPowerups());
		for (int i = 0;i<tmpPowerUp.size();++i) {
			PowerUp powerUp = tmpPowerUp.get(i);
			g.drawImage( 
					Resources.getPowerUp(powerUp.getType()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							Image.SCALE_FAST),
					powerUp.getCell().getxCell() * Settings.BLOCKSIZEX, powerUp.getCell().getyCell() * Settings.BLOCKSIZEY, null);
		}
		
		Player selected = controller.getGioco().getPlayer(Settings.PLAYER1);
		if(controller.isMultiplayer()) {
			selected = controller.getGioco().getPlayer(Client.getClient().getOrderConnection());
		}		
	
		Vector<Brick> tmpbrick = new Vector<Brick>(controller.getGioco().getBricks());
		for (Brick brick : tmpbrick) {
			g.drawImage(
					bricks.get(brick.getExplosionTime()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							Image.SCALE_FAST),
					brick.getCell().getxCell() * Settings.BLOCKSIZEX, brick.getCell().getyCell() * Settings.BLOCKSIZEY, null);
		}
		Vector<Bomb> tmpB = new Vector<Bomb>(controller.getGioco().getBombs());
		for (Bomb b : tmpB) {
			g.drawImage(
					bombs.get(b.getTimer()).getScaledInstance(Settings.BLOCKSIZEX, Settings.BLOCKSIZEY,
							Image.SCALE_SMOOTH),
					b.getXCell() * Settings.BLOCKSIZEX, b.getYCell() * Settings.BLOCKSIZEY, null);
		}

		
		
		Vector<Explosion> tmpE = new Vector<Explosion>(controller.getGioco().getExplosions());
		for (Explosion b : tmpE) {
			g.drawImage(
					explosions.get(b.getType(), b.getDurata(), b.getDirection()).getScaledInstance(Settings.BLOCKSIZEX,
							Settings.BLOCKSIZEY, Image.SCALE_SMOOTH),
					b.getXCell() * Settings.BLOCKSIZEX, b.getYCell() * Settings.BLOCKSIZEY , null);
		}
		
		for (int i = 0; i < controller.getGioco().getEnemies().size(); ++i) {
			Enemy b = controller.getGioco().getEnemies().get(i);
			if (b instanceof Enemy1 || b instanceof Enemy2) {
				g.drawImage(
						enemyview.get(i).getCurrentImage().getScaledInstance(scaleByX(b.getWidth()),
								scaleByY(b.getHeight()) , Image.SCALE_FAST),
						scaleByX(b.getX()), scaleByY(b.getY()), null);
			} else if (b instanceof Enemy3) {
				if (((Enemy3) b).isVisible()) {
					g.drawImage(
							enemyview.get(i).getCurrentImage().getScaledInstance(scaleByX(b.getWidth()),
									scaleByY(b.getHeight()), Image.SCALE_FAST),
							scaleByX(b.getX()), scaleByY(b.getY()), null);
				} else {
					if (((Enemy3) b).getUnseenTime() < 80 && ((Enemy3) b).getUnseenTime() % 20 >= 0
							&& ((Enemy3) b).getUnseenTime() % 20 < 5)
						g.drawImage(
								enemyview.get(i).getCurrentImage().getScaledInstance(scaleByX(b.getWidth()),
										scaleByY(b.getHeight()), Image.SCALE_FAST),
								scaleByX(b.getX()), scaleByY(b.getY()), null);
				}
			}
		}
		Player p1 = controller.getGioco().getPlayer(Settings.PLAYER1);
		g.drawImage(player1.getCurrentImage().getScaledInstance(scaleByX(p1.getWidth()), scaleByY(p1.getHeight())+Settings.factor3d,
				Image.SCALE_FAST), scaleByX(p1.getX()), scaleByY(p1.getY())-Settings.factor3d, null);
		if (controller.isMultiplayer()) {
			Player p2 = controller.getGioco().getPlayer(Settings.PLAYER2);
			g.drawImage(player2.getCurrentImage().getScaledInstance(scaleByX(p2.getWidth()), scaleByY(p2.getHeight())+Settings.factor3d,
					Image.SCALE_FAST), scaleByX(p2.getX()), scaleByY(p2.getY())-Settings.factor3d, null);
			if (controller.getGioco().isBattleRoyale()) {
				Player p3 = controller.getGioco().getPlayer(Settings.PLAYER3);
				g.drawImage(player3.getCurrentImage().getScaledInstance(scaleByX(p1.getWidth()),
						scaleByY(p1.getHeight())+Settings.factor3d, Image.SCALE_FAST), scaleByX(p3.getX()), scaleByY(p3.getY())-Settings.factor3d, null);
				Player p4 = controller.getGioco().getPlayer(Settings.PLAYER4);
				g.drawImage(player4.getCurrentImage().getScaledInstance(scaleByX(p4.getWidth()),
						scaleByY(p4.getHeight())+Settings.factor3d, Image.SCALE_FAST), scaleByX(p4.getX()), scaleByY(p4.getY())-Settings.factor3d, null);
				Player p5 = controller.getGioco().getPlayer(Settings.PLAYER5);
				g.drawImage(player5.getCurrentImage().getScaledInstance(scaleByX(p5.getWidth()),
						scaleByY(p5.getHeight())+Settings.factor3d, Image.SCALE_FAST), scaleByX(p5.getX()), scaleByY(p5.getY())-Settings.factor3d
								, null);
			}
		}
		

	}

}
