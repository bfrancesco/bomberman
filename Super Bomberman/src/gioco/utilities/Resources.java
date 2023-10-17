package gioco.utilities;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import gioco.model.PowerUp;

/*
 * Contiene tutte le risorse utilizzate nell'applicazione , è necessario che tutte possano essere lette in maniera corretta per il corretto sviiuppo del gioco
 * I metodi di lettura permettono di accedere alle immagini desiderate 
 * */
public class Resources {
	
	/***GRAPHICFILES***/
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
	
	
	public static Vector<Image> leftOrangeBomberman;
	public static Vector<Image> rightOrangeBomberman;
	public static Vector<Image> upOrangeBomberman;
	public static Vector<Image> downOrangeBomberman;
	public static Vector<Image> victoryOrangeBomberman;
	public static Vector<Image> dyingExplosionOrangeBomberman;
	public static Vector<Image> dyingByEnemyOrangeBomberman;
	
	public static Vector<Image> leftBlueBomberman;
	public static Vector<Image> rightBlueBomberman;
	public static Vector<Image> upBlueBomberman;
	public static Vector<Image> downBlueBomberman;
	public static Vector<Image> victoryBlueBomberman;
	public static Vector<Image> dyingExplosionBlueBomberman;
	public static Vector<Image> dyingByEnemyBlueBomberman;
	
	public static Vector<Image> leftGreenBomberman;
	public static Vector<Image> rightGreenBomberman;
	public static Vector<Image> upGreenBomberman;
	public static Vector<Image> downGreenBomberman;
	public static Vector<Image> victoryGreenBomberman;
	public static Vector<Image> dyingExplosionGreenBomberman;
	public static Vector<Image> dyingByEnemyGreenBomberman;
	
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
	
	public static Vector<Image> maps;
	public static Vector<Image> bricks;
	
	public static Vector<Image> victory;
	
	public static Image createGameMenu;	
	public static Image singlePlayer ;
	public static Image multiPlayer ;
	public static Image battleRoyale ;
	
	public static Image annulla;
	public static Image conferma;
	public static Image indietro ;
	public static Image indietroSelected ;
	public static Image gioca ;
	public static Image giocaSelected ;
	public static Image riprendi ;
	public static Image ricomincia ;
	public static Image abbandona ;
	public static Image esci;
	public static Image giocaAncora;
	public static Image prossimo;
	public static Image rivincita;
	public static Image indietroButton;
	
	public static Image firePowerUp ;
	public static Image bombPowerUp ;
	public static Image speedPowerUp ;
	//Player1 - Player2 - Player3 ...
	
	//l'ordine dei colori deve essere rispettato per avere corrispondenza fra il colore scelto e quello visualizzato
	public static Vector<Image> bombermanIcons;
	public static Image iconEnemy;
	public static Image iconClock;
	
	public static Image info;
	public static Image iconWindow;
	public static Image loading;
	public static Image iconMyDialog;
	
	public static Image enemiesImage;
	public static Image keys;
	
	public static Image muteEffects;
	public static Image notMuteEffects;
	
	public static Image wallpaper;
	public static Image pauseWallpaper;
	public static Image  mydialogWallpaper;
	public static Image wallpaperConnecting;
	public static Image logo;
	public static Image key;
	
	public static Image loss;
	public static Image draw;
	public static Font  myFont;
	
	/***AUDIOFILES***/
	public static Clip  gameSoundtrack ;	
	public static Clip  menuSoundtrack ;
	public static Clip  steps ;	
	public static Clip  pause;
	public static Clip  explosion;
	public static Clip  clicked;	
	public static Clip  placeBomb;	
	public static Clip  getPowerUp;
	public static Clip  dyingEnemySound;
	public static Clip  enteredMenuButton;
	public static Clip  enteredStandardButton;
	public static Clip  bombSelection;
	public static Clip  playerDeathSound;
	public static Clip  lossSounds;
	public static Clip  victorySound;
	public static Clip  drawSound;
	
	/***TEXTFILES***/
	public static BufferedReader controls;
	public static BufferedReader enemies;
	public static BufferedReader rules;
	public static BufferedReader powerup;
	public static BufferedReader mode;
	public static BufferedReader credit;
	
	

	public static void loadInfoFiles() {
		try {
		InputStream input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/controls.txt");
		controls =   new BufferedReader(new InputStreamReader(input));
		
		input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/enemies.txt");
		enemies =   new BufferedReader(new InputStreamReader(input));
		
		input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/rules.txt");
		rules =   new BufferedReader(new InputStreamReader(input));
		
		input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/powerup.txt");
		powerup =   new BufferedReader(new InputStreamReader(input));
		
		input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/mode.txt");
		mode =   new BufferedReader(new InputStreamReader(input));
	
		input = Resources.class.getResourceAsStream("/gioco/resources/infoFiles/credit.txt");
		credit =   new BufferedReader(new InputStreamReader(input));
		}catch (Exception e) {
			System.out.println("INFO FILES NOT LOADED");
		}
	}
	
	public static void loadInfoImages() throws IOException{
		enemiesImage = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/infoFiles/enemies.png"));
		keys =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/infoFiles/keys.png"));
	}
	
	public static void loadWindowIcon()throws IOException{
		
			iconWindow = ImageIO
					.read(Resources.class.getResourceAsStream("/gioco/resources/bombs/bomb_0.png"));
			loading = (new ImageIcon(Resources.class.getClassLoader().getResource("gioco/resources/other/ajax-loader.gif"))).getImage();
			
			key = ImageIO
					.read(Resources.class.getClassLoader().getResource("gioco/resources/other/key.png"));
			

	}
	
	public static void loadResult() throws IOException {
		victory = new Vector<Image>();
		draw = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/result/pareggio.png"));
		loss = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/result/loss.png"));
		Image white =  ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/result/victoryWhite.png"));
		Image black =ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/result/victoryBlack.png")) ;
		Image orange = ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/result/victoryOrange.png"));
		Image blue =ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/result/victoryBlue.png"));
		Image green  = ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/result/victoryGreen.png"));
		
		victory.add(white);
		victory.add(black);
		victory.add(orange);
		victory.add(blue);
		victory.add(green);
	}
	
	public static Image getPowerUp(int type) {
		switch (type) {
		case PowerUp.DOUBLEBOMB:
			return bombPowerUp;
		case PowerUp.DOUBLEFIRE:
			return firePowerUp;
		case PowerUp.SPEEDUP:
			return speedPowerUp; 
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
	
	public static void loadWallpapers() throws IOException {
		wallpaper = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/other/sfondo.jpg"));
		wallpaperConnecting = ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/other/wallpaperConnecting.png"));
		pauseWallpaper = ImageIO
				.read(Resources.class.getClassLoader().getResource("gioco/resources/other/pauseWallpaper.jpg"));
		logo = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/other/logo.png"));
		mydialogWallpaper = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/other/mydialog.jpg"));
		createGameMenu = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/other/createGameMenu.png"));
	}
	
	public static void loadFont()  {
		try {
			myFont =  Font.createFont( Font.TRUETYPE_FONT , Resources.class.getResourceAsStream("/gioco/resources/other/magicDream.ttf"));
			myFont = myFont.deriveFont(15f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    //register the font
		    ge.registerFont(myFont);
		} catch (Exception e) {
			System.out.println("sans");
			myFont = Font.getFont(Font.SANS_SERIF);
		}
	}
	
	public static BufferedReader loadMap(int i) throws FileNotFoundException {
		InputStream input = Resources.class.getResourceAsStream("/gioco/resources/maps/Map"+i+".txt");
		return  new BufferedReader(new InputStreamReader(input));
	}
	
	public static void loadResources() {		
		
		try {
		loadBricks();
		loadPowerUp();
		loadWhiteBombermanImages();
		loadBlackBombermanImages();
		loadOrangeBombermanImages();
		loadBombImages();
		loadEnemyImages();
		loadExplosionImages();
		loadIcons();
		loadInfoFiles();
		loadSounds();
		loadMaps();
		loadWallpapers();
		loadInfoImages();
		loadFont();
		loadButtons();
		loadResult();
		loadWindowIcon();
		}
		catch (IOException e) {
			System.out.println("RESOURCES ARE UNAVAILABLE");
			System.exit(0);
			
		}
	}
	
	private static void loadBricks() throws IOException {
		bricks = new Vector<Image>();
		for(int i = 0 ; i<6; i++)
				bricks.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/bricks/brick_"+i+".png")));

	}
	
	private static void loadPowerUp() throws IOException {
		bombPowerUp = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/powerUps/bombPowerUp.png"));
		firePowerUp = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/powerUps/firePowerUp.png"));
		speedPowerUp = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/powerUps/speedPowerUp.png"));
		
	}
	
	private static void loadButtons() throws IOException{
		annulla = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/annulla.png"));
		conferma = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/conferma.png"));
		singlePlayer = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/singleplayer.png"));
		multiPlayer = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/multiplayer.png"));
		battleRoyale = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/battleRoyale.png"));
		indietro = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/indietro.png"));
		indietroSelected = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/indietroSelected.png"));
		gioca = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/gioca.png"));
		giocaSelected = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/giocaSelected.png"));
		riprendi = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/riprendi.png"));
		ricomincia = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/ricomincia.png"));
		abbandona = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/abbandona.png"));
		indietroButton = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/indietroButton.png"));
		esci = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/esci.png"));
		giocaAncora =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/giocaAncora.png"));
		rivincita =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/rivincita.png"));
		prossimo =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/buttons/prossimo.png"));
	}
	
	private static void loadSounds() {
		try {
			clicked = AudioSystem.getClip();
			gameSoundtrack = AudioSystem.getClip();
			menuSoundtrack = AudioSystem.getClip();
			getPowerUp = AudioSystem.getClip();
			steps= AudioSystem.getClip();
			playerDeathSound = AudioSystem.getClip();
			explosion  = AudioSystem.getClip();
			enteredMenuButton =AudioSystem.getClip();
			enteredStandardButton =AudioSystem.getClip();
			pause = AudioSystem.getClip();
			drawSound = AudioSystem.getClip();
			victorySound = AudioSystem.getClip();
			lossSounds = AudioSystem.getClip();
			drawSound = AudioSystem.getClip();
			bombSelection = AudioSystem.getClip();
			placeBomb= AudioSystem.getClip();
			dyingEnemySound = AudioSystem.getClip();
			gameSoundtrack.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/BombermanTheme.wav"))));					
			menuSoundtrack.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/menuTheme.wav"))));				
			steps.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/Walking.wav"))));
			explosion.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/Explosion.wav"))));
			pause.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/Pause.wav"))));
			clicked.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/buttonPress.wav"))));
			placeBomb.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/placeBomb.wav"))));
			getPowerUp.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/getPowerUp.wav"))));
			dyingEnemySound.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/dyingEnemy.wav"))));
			enteredMenuButton.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/enteredButton.wav"))));
			enteredStandardButton.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/standardSelection.wav"))));
			bombSelection.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/bombButton.wav"))));
			playerDeathSound.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/playerDeath.wav"))));
			victorySound.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/win.wav"))));
			lossSounds.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/loss.wav"))));
			drawSound.open(AudioSystem.getAudioInputStream(new BufferedInputStream(Resources.class.getResourceAsStream("/gioco/resources/sounds/draw.wav"))));
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			gameSoundtrack = null;
			menuSoundtrack= null;
			e.printStackTrace();
		}
	}
	
	private static void loadIcons() throws IOException {
		bombermanIcons = new Vector<Image>();
		iconEnemy =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/enemiesIcon.png")).getScaledInstance(30 , 30, 0);
		iconClock =  ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/clock.png")).getScaledInstance(30 , 30, 0);
		bombermanIcons.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/whiteBombermanIcon.png")).getScaledInstance(30 , 30, 0));
		bombermanIcons.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/blackBombermanIcon.png")).getScaledInstance(30 , 30, 0));
		bombermanIcons.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/orangeBombermanIcon.png")).getScaledInstance(30 , 30, 0));
		bombermanIcons.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/blueBombermanIcon.png")).getScaledInstance(30 , 30, 0));
		bombermanIcons.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/greenBombermanIcon.png")).getScaledInstance(30 , 30, 0));
		muteEffects=ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/muteEffects.png"));
		notMuteEffects=ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/notMuteEffects.png"));
		info = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/info.png"));
		iconMyDialog = ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/icons/iconMyDialog.png"));
	}
	
	private static void loadEnemyImages() {
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
	
	private static void loadBombImages() {
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
	
	private static void loadMaps() {
		maps = new Vector<Image>();
		try {
			for(int i = 1;i<=Settings.MAPSNUMBER;++i) {
				maps.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/maps/Map" + i + ".png")));
			}
		} catch (Exception e) {
			System.out.println("IMPOSSIBILE CARICARE I TERRENI DI GIOCO");
		}
	}
	
	private static void loadExplosionImages() {
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
	
	private static void loadWhiteBombermanImages() {
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
			dyingExplosionWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/dying_" + 5 + ".png")));
			for (int i = 0; i < 2; ++i) {
				victoryWhiteBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/whiteBomberman/victory_" + i + ".png")));
			}
						
			
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("WHITE BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	
	private static void loadBlackBombermanImages() {
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
			dyingExplosionBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/dying_" + 5 + ".png")));
			for (int i = 0; i < 2; ++i) {
				victoryBlackBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blackBomberman/winning_" + i + ".png")));
			}
						
			
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("BLACK BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	
	private static void loadOrangeBombermanImages() {
		leftOrangeBomberman = new Vector<Image>();
		rightOrangeBomberman = new Vector<Image>();
		upOrangeBomberman = new Vector<Image>();
		downOrangeBomberman = new Vector<Image>();
		victoryOrangeBomberman = new Vector<Image>();
		dyingExplosionOrangeBomberman = new Vector<Image>();
		dyingByEnemyOrangeBomberman = new Vector<Image>();
		
		leftBlueBomberman = new Vector<Image>();
		rightBlueBomberman = new Vector<Image>();
		upBlueBomberman = new Vector<Image>();
		downBlueBomberman = new Vector<Image>();
		victoryBlueBomberman = new Vector<Image>();
		dyingExplosionBlueBomberman = new Vector<Image>();
		dyingByEnemyBlueBomberman = new Vector<Image>();
		
		leftGreenBomberman = new Vector<Image>();
		rightGreenBomberman = new Vector<Image>();
		upGreenBomberman = new Vector<Image>();
		downGreenBomberman = new Vector<Image>();
		victoryGreenBomberman = new Vector<Image>();
		dyingExplosionGreenBomberman = new Vector<Image>();
		dyingByEnemyGreenBomberman = new Vector<Image>();
		try {
			for (int i = 0; i < 4; ++i) {
				leftOrangeBomberman.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/left_" + i + ".png")));
				rightOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/right_" + i + ".png")));
				upOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/up_" + i + ".png")));
				downOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/down_" + i + ".png")));
				leftBlueBomberman.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/left_" + i + ".png")));
				rightBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/right_" + i + ".png")));
				upBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/up_" + i + ".png")));
				downBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/down_" + i + ".png")));
				leftGreenBomberman.add( ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/left_" + i + ".png")));
				rightGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/right_" + i + ".png")));
				upGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/up_" + i + ".png")));
				downGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/down_" + i + ".png")));
			
			}
			for (int i = 0; i < 5; ++i) {
				dyingByEnemyOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/dyingEnemy_" + i + ".png")));
				dyingExplosionOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/dying_" + i + ".png")));
				dyingByEnemyBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/dyingEnemy_" + i + ".png")));
				dyingExplosionBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/dying_" + i + ".png")));
				dyingByEnemyGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/dyingEnemy_" + i + ".png")));
				dyingExplosionGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/dying_" + i + ".png")));
			
			}
			dyingExplosionOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/dying_" + 5 + ".png")));
			dyingExplosionBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/dying_" + 5 + ".png")));
			dyingExplosionGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/dying_" + 5 + ".png")));
			for (int i = 0; i < 2; ++i) {
				victoryOrangeBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/orangeBomberman/Orangewinning_" + i + ".png")));
				victoryBlueBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/blueBomberman/Bluewinning_" + i + ".png")));
				victoryGreenBomberman.add(ImageIO.read(Resources.class.getResourceAsStream("/gioco/resources/greenBomberman/Greenwinning_" + i + ".png")));
			}
						
			
		} catch (IOException e) {
			/* Aggiungere finestra */
			System.out.println("ORANGE BOMBERMAN RESOURCES ARE UNAVAILABLE");
			e.printStackTrace();
		}
	}
	
	public static Vector<Image> downBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return downWhiteBomberman;
		case Settings.BLACK:
			return downBlackBomberman;
		case Settings.ORANGE:
			return downOrangeBomberman;
		case Settings.BLUE:
			return downBlueBomberman;
		case Settings.GREEN:
			return downGreenBomberman;
		default:
			return  downBlackBomberman;
		}
	}
	
	public static Vector<Image> upBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return upWhiteBomberman;
		case Settings.BLACK:
			return upBlackBomberman;
		case Settings.ORANGE:
			return upOrangeBomberman;
		case Settings.BLUE:
			return upBlueBomberman;
		case Settings.GREEN:
			return upGreenBomberman;
		default:
			return  upBlackBomberman;
		}
	}
	
	public static Vector<Image> rightBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return rightWhiteBomberman;
		case Settings.BLACK:
			return rightBlackBomberman;
		case Settings.ORANGE:
			return rightOrangeBomberman;
		case Settings.BLUE:
			return rightBlueBomberman;
		case Settings.GREEN:
			return rightGreenBomberman;
		default:
			return  rightBlackBomberman;
		}
	}
	
	public static Vector<Image> leftBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return leftWhiteBomberman;
		case Settings.BLACK:
			return leftBlackBomberman;
		case Settings.ORANGE:
			return leftOrangeBomberman;
		case Settings.BLUE:
			return leftBlueBomberman;
		case Settings.GREEN:
			return leftGreenBomberman;
		default:
			return  leftBlackBomberman;
		}
	}
	
	public static Vector<Image> dyingByEnemyBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return dyingByEnemyWhiteBomberman;
		case Settings.BLACK:
			return dyingByEnemyBlackBomberman;
		case Settings.ORANGE:
			return dyingByEnemyOrangeBomberman;
		case Settings.BLUE:
			return dyingByEnemyBlueBomberman;
		case Settings.GREEN:
			return dyingByEnemyGreenBomberman;
		default:
			return  dyingByEnemyBlackBomberman;
		}
	}
	
	public static Vector<Image> dyingExplosionBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return  dyingExplosionWhiteBomberman;
		case Settings.BLACK:
			return  dyingExplosionBlackBomberman;
		case Settings.ORANGE:
			return  dyingExplosionOrangeBomberman;
		case Settings.BLUE:
			return dyingExplosionBlueBomberman;
		case Settings.GREEN:
			return dyingExplosionGreenBomberman;
		default:
			return   dyingExplosionBlackBomberman;
		}
	}
	
	public static Vector<Image> victoryBomberman(int player){
		switch(player) {
		case Settings.WHITE:
			return victoryWhiteBomberman;
		case Settings.BLACK:
			return victoryBlackBomberman;
		case Settings.ORANGE:
			return victoryOrangeBomberman;
		case Settings.BLUE:
			return victoryBlueBomberman;
		case Settings.GREEN:
			return victoryGreenBomberman;
		default:
			return  victoryBlackBomberman;
		}
	}
	
	
	public static Image victory(int color) {
		int pos = color - Settings.WHITE;
		if(pos >= victory.size())
			pos = 0;
		return victory.get(pos);
		
	}
	
}
