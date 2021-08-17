package gioco.utilities;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resources {
	
	public static Vector<Image> leftWhiteBomberman;
	public static Vector<Image> rightWhiteBomberman;
	public static Vector<Image> upWhiteBomberman;
	public static Vector<Image> downWhiteBomberman;
	public static Vector<Image> victoryWhiteBomberman;
	public static Vector<Image> dyingExplosionWhiteBomberman;
	public static Vector<Image> dyingByEnemyWhiteBomberman;
	
	public static Vector<Image> leftBlackBomberman;
	public static Vector<Image> rightBlackBomberman;
	public static Vector<Image> upBlackBomberman;
	public static Vector<Image> downBlackBomberman;
	public static Vector<Image> victoryBlackBomberman;
	public static Vector<Image> dyingExplosionBlackBomberman;
	public static Vector<Image> dyingByEnemyBlackBomberman;
	
	
	
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
	
	public static Image brick;
	
	public static Image iconWhite;
	public static Image iconBlack;
	public static Image iconEnemy;
	public static Image iconClock;
	
	
	public static Image iconWindow;
	
	public static void loadWindowIcon()throws IOException{
		
			iconWindow = ImageIO
					.read(Resources.class.getResourceAsStream("/gioco/resources/bombs/bomb_0.png"));

	}
	
	public static void loadResources() {
		try {
			brick = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blocks/brick.jpg"));
		} catch (IOException e) {
			System.out.println("BRICK RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
		loadWhiteBombermanImages();
		loadBlackBombermanImages();
		loadBombImages();
		loadEnemyImages();
		loadExplosionImages();
		loadIcons();
	}
	
	public static void loadIcons() {
		try {
			iconEnemy =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/enemiesIcon.png")).getScaledInstance(30 , 30, 0);
			iconWhite = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/whiteBombermanIcon.png")).getScaledInstance(30 , 30, 0);
			iconClock =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/clock.png")).getScaledInstance(30 , 30, 0);
			iconBlack =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/blackBombermanIcon.png")).getScaledInstance(30 , 30, 0);
		} catch (IOException e) {
			System.out.println("ICONS RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
		
	}
	
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
	
	public static void loadWhiteBombermanImages() {
		leftWhiteBomberman = new Vector<Image>();
		rightWhiteBomberman = new Vector<Image>();
		upWhiteBomberman = new Vector<Image>();
		downWhiteBomberman = new Vector<Image>();
		victoryWhiteBomberman = new Vector<Image>();
		dyingExplosionWhiteBomberman = new Vector<Image>();
		dyingByEnemyWhiteBomberman = new Vector<Image>();
		try {
			for (int i = 0; i < 4; ++i) {
				leftWhiteBomberman.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/left_" + i + ".png")));
				rightWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/right_" + i + ".png")));
				upWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/up_" + i + ".png")));
				downWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/down_" + i + ".png")));
			}
			for (int i = 0; i < 5; ++i) {
				dyingByEnemyWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/dyingEnemy_" + i + ".png")));
				dyingExplosionWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/dying_" + i + ".png")));
			}
			for (int i = 0; i < 2; ++i) {
				victoryWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/victory_" + i + ".png")));
			}
						
			
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("WHITE BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	
	public static void loadBlackBombermanImages() {
		leftBlackBomberman = new Vector<Image>();
		rightBlackBomberman = new Vector<Image>();
		upBlackBomberman = new Vector<Image>();
		downBlackBomberman = new Vector<Image>();
		victoryBlackBomberman = new Vector<Image>();
		dyingExplosionBlackBomberman = new Vector<Image>();
		dyingByEnemyBlackBomberman = new Vector<Image>();
		try {
			for (int i = 0; i < 4; ++i) {
				leftBlackBomberman.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/left_" + i + ".png")));
				rightBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/right_" + i + ".png")));
				upBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/up_" + i + ".png")));
				downBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/down_" + i + ".png")));
			}
			for (int i = 0; i < 5; ++i) {
				dyingByEnemyBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/dyingEnemy_" + i + ".png")));
				dyingExplosionBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/dying_" + i + ".png")));
			}
			for (int i = 0; i < 2; ++i) {
				victoryBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/winning_" + i + ".png")));
			}
						
			
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("BLACK BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	public static Vector<Image> downBomberman(int player){
		if(player == Settings.PLAYER1)
			return downWhiteBomberman;
		else
			return downBlackBomberman;
	}
	
	public static Vector<Image> upBomberman(int player){
		if(player == Settings.PLAYER1)
			return upWhiteBomberman;
		else
			return upBlackBomberman;
	}
	
	public static Vector<Image> rightBomberman(int player){
		if(player == Settings.PLAYER1)
			return rightWhiteBomberman;
		else
			return rightBlackBomberman;
	}
	
	public static Vector<Image> leftBomberman(int player){
		if(player == Settings.PLAYER1)
			return leftWhiteBomberman;
		else
			return leftBlackBomberman;
	}
	
	public static Vector<Image> dyingByEnemyBomberman(int player){
		if(player == Settings.PLAYER1)
			return dyingByEnemyWhiteBomberman;
		else
			return dyingByEnemyBlackBomberman;
	}
	
	public static Vector<Image> dyingExplosionBomberman(int player){
		if(player == Settings.PLAYER1)
			return dyingExplosionWhiteBomberman;
		else
			return dyingExplosionWhiteBomberman;
	}
	
	public static Vector<Image> victoryBomberman(int player){
		if(player == Settings.PLAYER1)
			return victoryWhiteBomberman;
		else
			return victoryBlackBomberman;
	}
	
	
}
