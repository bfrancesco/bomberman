package gioco.utilities;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Resources {
	
	public static Vector<Image> left;
	public static Vector<Image> right;
	public static Vector<Image> up;
	public static Vector<Image> down;
	public static Vector<Image> victory;
	public static Vector<Image> dyingExplosion;
	public static Vector<Image> dyingByEnemy;
	
	
	
	public static Vector<Image> center;
	public static Vector<Image> middle_up;
	public static Vector<Image> middle_down;
	public static Vector<Image> middle_right;
	public static Vector<Image> middle_left;
	public static Vector<Image> end_up;
	public static Vector<Image> end_down;
	public static Vector<Image> end_right;
	public static Vector<Image> end_left;
	
	public static  Vector<Image> bombSteps;
	
	
	public static Vector<Image> rightEnemy1;
	public static Vector<Image> downEnemy1;
	public static Vector<Image> leftEnemy1;
	public static Vector<Image> upEnemy1;
	public static Vector<Image> dyingExplosionEnemy1 ;
	
	public static Vector<Image> rightEnemy2;
	public static Vector<Image> downEnemy2;
	public static Vector<Image> upEnemy2;
	public static Vector<Image> leftEnemy2;
	public static Vector<Image> dyingExplosionEnemy2 ;
	
	public static Vector<Image> rightEnemy3;
	public static Vector<Image> downEnemy3;
	public static Vector<Image> upEnemy3;
	public static Vector<Image> leftEnemy3;
	public static Vector<Image> dyingExplosionEnemy3 ;
	
	
	public static void loadEnemyImages() {
		rightEnemy1 = new Vector<Image>();
		downEnemy1 = new Vector<Image>();
		leftEnemy1 = new Vector<Image>();
		upEnemy1 = new Vector<Image>();
		dyingExplosionEnemy1 = new Vector<Image>();
		
		rightEnemy2 = new Vector<Image>();
		downEnemy2= new Vector<Image>();
		dyingExplosionEnemy2 = new Vector<Image>();
		leftEnemy2 = new Vector<Image>();
		upEnemy2 = new Vector<Image>();
		
		rightEnemy3 = new Vector<Image>();
		downEnemy3 = new Vector<Image>();
		dyingExplosionEnemy3 = new Vector<Image>();
		leftEnemy3 = new Vector<Image>();
		upEnemy3 = new Vector<Image>();
		
		
		try {

			for (int i = 0; i < 4; ++i) {
				rightEnemy2.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy2/mummy_right_" + i + ".png")));
				leftEnemy2.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy2/mummy_left_" + i + ".png")));
			}
			
			for(int i = 0; i<3 ; i++) {
				leftEnemy1.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy1/virus_left_" + i + ".png")));
				downEnemy1.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy1/virus_down_" + i + ".png")));
				upEnemy1.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy1/virus_up_" + i + ".png")));
				rightEnemy1.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy1/virus_right_" + i + ".png")));
				dyingExplosionEnemy1.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy1/virus_dying_" + i + ".png")));
				
				downEnemy2.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy2/mummy_down_" + i + ".png")));
				dyingExplosionEnemy2.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy2/mummy_dying_" + i + ".png")));		
				upEnemy2.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy2/mummy_up_" + i + ".png")));
				rightEnemy3.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy3/ghost_right_" + i + ".png")));
				downEnemy3.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy3/ghost_down_" + i + ".png")));
				dyingExplosionEnemy3.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy3/ghost_dying_" + i + ".png")));
				leftEnemy3.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy3/ghost_left_" + i + ".png")));
				upEnemy3.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/enemy3/ghost_up_" + i + ".png")));
			}
						
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("ENEMY1 RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	public static void loadBombImages() {
		bombSteps = new Vector<Image>();
		try {

			for (int i = 0; i < 3; ++i) {
				Image img = ImageIO
						.read(Resources.class.getResourceAsStream("/gioco/resources/bombs/bomb_" + i + ".png"));
				bombSteps.add(img);
			}
	}
		catch (IOException e) {
			System.out.println("BOMB RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	
	public static void loadExplosionImages() {
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
						.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/center/center_" + i + ".png")));
				middle_up.add(ImageIO.read(
						Resources.class.getResourceAsStream("/gioco/resources/explosion/middle/middle_up_" + i + ".png")));
				end_up.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/end/end_up_" + i + ".png")));
				middle_right.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/middle/middle_right_" + i + ".png")));
				end_right.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/end/end_right_" + i + ".png")));
				middle_down.add(ImageIO.read(
						Resources.class.getResourceAsStream("/gioco/resources/explosion/middle/middle_down_" + i + ".png")));
				end_down.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/end/end_down_" + i + ".png")));
				middle_left.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/middle/middle_left_" + i + ".png")));
				end_left.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/explosion/end/end_left_" + i + ".png")));
			}

		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("EXPLOSIONS RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	public static void loadBombermanImages() {
		left = new Vector<Image>();
		right = new Vector<Image>();
		up = new Vector<Image>();
		down = new Vector<Image>();
		victory = new Vector<Image>();
		dyingExplosion = new Vector<Image>();
		dyingByEnemy = new Vector<Image>();
		
		try {
			for (int i = 0; i < 3; ++i) {
				left.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/Walking/left/left_" + i + ".png")));
				right.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/Walking/right/right_" + i + ".png")));
				up.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/Walking/up/up_" + i + ".png")));
				down.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/Walking/down/down_" + i + ".png")));
			}
			for (int i = 0; i < 5; ++i) {
				dyingByEnemy.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/dyingByEnemy/dying_" + i + ".png")));
				dyingExplosion.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/dyingExplosion/dying_" + i + ".png")));
			}
			for (int i = 0; i < 2; ++i) {
				victory.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/winning/victory_" + i + ".png")));
			}
						
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("WHITE BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	
	
	
	
}
