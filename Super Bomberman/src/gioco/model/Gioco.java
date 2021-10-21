package gioco.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import gioco.utilities.Resources;
import gioco.utilities.Settings;
/*
 * Gioco gestisce lo sviluppo logico di una partita 
 * Possiede una matrice di blocchi che identifica la mappa 
 * Dei vettori per i vari elementi : Brick, PowerUp ,Enemy, Bomb, Explosion , Player
 * ha inoltre dei campi utili a conservare la modalità , le impostazioni , il risultato ,i cambiamenti avvenuti rispetto allo stato precedente
 * gestisce le collisioni totali , parziali  per migliorare la giocabilità e le interazioni fra i vari elementi posseduti
 * */
public class Gioco {
	public static final int VICTORY = 0;
	public static final int DRAW = 1;
	public static final int LOSS = 2;

	public static final int TOTALCOLLISION = 4;
	public static final int RIGHTCOLLISION = 5;
	public static final int LEFTCOLLISION = 6;
	public static final int DOWNCOLLISION = 7;
	public static final int UPCOLLISION = 8;
	public static final int NOCOLLISION = 9;
	
	private static final int POINTSENEMY1= 1000;
	private static final int POINTSENEMY2 = 2500;
	private static final int POINTSENEMY3 = 3000;
	private static final int POINTSPLAYER = 5000;
	private int time = 150;
	private Block[][] matrix;
	private Vector<Brick> bricks;
	private Vector<PowerUp> powerups;
	private int height = Settings.LOGICHEIGHT;
	private int width = Settings.LOGICWIDTH;
	private Vector<Player> players;
	private Vector<Enemy> enemies;
	private Vector<Bomb> bombs;
	private Vector<Explosion> explosions;
	private boolean multiplayer;
	private boolean gameOver;
	private boolean started;
	private boolean battleRoyale;
	private int playersAlive;
	private int map;

	private boolean collidingPowerUp;
	private boolean bombIncreased;
	private boolean bombExploded;
	private boolean collidingEnemyPlayer;
	private boolean collidingExplosionPlayer;
	private boolean collisionExplosionEnemy;
	
	private int result ;
	//inizializza il gioco , legge la mappa , genera i powerup
	public Gioco(boolean multiplayer, boolean battleRoyale, int map) {
		this.multiplayer = multiplayer;
		enemies = new Vector<Enemy>();
		bombs = new Vector<Bomb>();
		explosions = new Vector<Explosion>();
		matrix = new Block[height][width];
		powerups = new Vector<PowerUp>();
		gameOver = false;
		result = LOSS;
		started = false;
		players = new Vector<Player>();
		bricks = new Vector<Brick>();
		collidingEnemyPlayer = false;
		collisionExplosionEnemy = false;
		bombExploded = false;
		bombIncreased = false;
		collidingPowerUp = false;
		collidingExplosionPlayer = false;
		if (multiplayer)
			this.battleRoyale = battleRoyale;
		else
			this.battleRoyale = false;
		try {
			this.map = map;
			loadMap(map);

		} catch (IOException e) {
			e.printStackTrace();
		}
		randomPowerUps();
		playersAlive = players.size();
	}

	public void inizia() {
		started = true;
		gameOver = false;
	}

	public void setTime(int t) {
		time = t;
	}

	public int getTime() {
		return time;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	//creazione casuale dei powerup , si possono generare solo sotto i brick e se non è presente un altro powerUp 
	private void randomPowerUps() {
		Random r = new Random(System.currentTimeMillis());
		for (int index = 0; index < PowerUp.TOTALPOWERUPS; ++index) {
			if (bricks.size() > 0) {
				int i = r.nextInt(bricks.size());
				Block b = matrix[bricks.get(i).getCell().getyCell()][bricks.get(i).getCell().getxCell()];
				if (!b.hasPowerUp()) {
					b.setPowerUp(PowerUp.SPEEDUP + index);
					powerups.add(new PowerUp(bricks.get(i).getCell().getxCell(), bricks.get(i).getCell().getyCell(), b.getPowerUp()));
				}
			}
		}
	}


	//lettura della mappa , dei nemici e della posizione dei players.
	//in base alla modalità di gioco solo i player utili verranno considerati
	//Dopo aver letto END segue la lettura della mappa 
	public void loadMap(int map) throws IOException {
		BufferedReader reader = null;
		try {
			reader =  new BufferedReader(new FileReader("src/gioco/resources/maps/Map"+map+".txt"));
			String s = reader.readLine();
			// lettura dei players e dei nemici
			int enemyID = 0;
			while (!(s.equals("END"))) {
				String[] elements = s.split(" ");
				if (elements[0].equals("player1")) {
					players.add(new Player(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, Settings.PLAYER1));
				} else if (multiplayer && elements[0].equals("player2")) {
					players.add(new Player(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, Settings.PLAYER2));
				} else if (battleRoyale && elements[0].equals("player3")) {
					players.add(new Player(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, Settings.PLAYER3));
				} else if (battleRoyale && elements[0].equals("player4")) {
					players.add(new Player(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, Settings.PLAYER4));
				} else if (battleRoyale && elements[0].equals("player5")) {
					players.add(new Player(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, Settings.PLAYER5));
				} else if (elements[0].equals("enemy1")) {
					Enemy1 e = new Enemy1(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, enemyID);
					enemyID += 1;
					enemies.add(e);
				} else if (elements[0].equals("enemy2")) {
					Enemy2 e = new Enemy2(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, enemyID);
					enemyID += 1;
					enemies.add(e);
				} else if (elements[0].equals("enemy3")) {
					Enemy3 e = new Enemy3(Integer.valueOf(elements[1]) * Settings.LOGICBLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.LOGICBLOCKSIZEY, enemyID);
					enemyID += 1;
					enemies.add(e);
				}
				s = reader.readLine();
			}

			// Lettura della mappa

			int k = 0;
			while (reader.ready()) {
				String line = reader.readLine();
				char[] positions = line.toCharArray();
				for (int i = 0; i < positions.length; ++i) {
					switch (positions[i]) {
					case 'F':
						matrix[k][i] = new Block(Block.FLOOR);
						break;
					case 'I':
						matrix[k][i] = new Block(Block.IRON);
						break;
					case 'B':
						matrix[k][i] = new Block(Block.BRICK);
						bricks.add(new Brick(i, k));
						break;
					case 'V':
						matrix[k][i] = new Block(Block.FLOOR);
						enemies.add(new Enemy1(i * Settings.LOGICBLOCKSIZEX, k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					case 'M':
						matrix[k][i] = new Block(Block.FLOOR);
						enemies.add(new Enemy2(i * Settings.LOGICBLOCKSIZEX, k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					case 'G':
						matrix[k][i] = new Block(Block.FLOOR);
						enemies.add(new Enemy3(i * Settings.LOGICBLOCKSIZEX, k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					default:
						matrix[k][i] = new Block(Block.FLOOR);
					}
				}
				k++;
			}
		} 
		catch (Exception e) {
		}finally {
			if (reader != null)
				reader.close();
		}
	}
	
	/*Aggiorna i nemici , se un nemico è stato colpito e non ha ancora raggiunto lo zero con il tempo di eliminazione , allora il tempo viene decrementato . 
	 * altrimenti viene posto in toBeRemoved per essere rimosso
	 *  per i nemici vivi , allora se non è presente una collisione totale , si muovono nella direzione assegnata 
	 *   altrimenti se non c'è possibilità di movimento viene posto in IDLE_DOWN
 	 */
	public synchronized void updateEnemy() {
		Vector<Enemy> enemiesToBeRemoved = new Vector<Enemy>();
		if (!gameOver) {			
			for (Enemy e : enemies) {
				if (e.getState() != Entity.DYING_EXPLOSION) {
					if (e instanceof Enemy3) {
						Enemy3 e3 = (Enemy3) e;
						e3.changeVisibility();
						if (e3.getUnseenTime() == e3.getRandomVisibilityTime() * 80 / 100 && !e3.isVisible())
							e3.Teleport(getAlives());
					}
					if (collisionBlock(e, e.getDirection()) != Gioco.TOTALCOLLISION
							/* && !collisionBombs(e, e.getDirection()) */ && e.getState() != Entity.IDLE_DOWN) {
						e.move();
					} else {
						ArrayList<Integer> directions = new ArrayList<Integer>();
						if (e.getDirection() != Settings.DOWN
								&& matrix[(e.ycenterBlock() + 1) % height][e.xcenterBlock()].isWalkable())
							directions.add(Settings.DOWN);
						if (e.getDirection() != Settings.LEFT && ((e.xcenterBlock() - 1 >= 0
								&& matrix[e.ycenterBlock()][e.xcenterBlock() - 1].isWalkable())
								|| (e.xcenterBlock() - 1 < 0 && matrix[e.ycenterBlock()][width - 1].isWalkable())))
							directions.add(Settings.LEFT);
						if (e.getDirection() != Settings.UP && ((e.ycenterBlock() - 1 >= 0
								&& matrix[e.ycenterBlock() - 1][e.xcenterBlock()].isWalkable())
								|| (e.ycenterBlock() - 1 < 0 && matrix[height - 1][e.xcenterBlock()].isWalkable())))
							directions.add(Settings.UP);
						if (e.getDirection() != Settings.RIGHT
								&& matrix[e.ycenterBlock()][(e.xcenterBlock() + 1) % width].isWalkable())
							directions.add(Settings.RIGHT);
						e.changeDirection(directions);
					}
				} else {
					if (e.getDyingTime() == 0) {
						
						enemiesToBeRemoved.add(e);
					} else
						e.decreasedyingTime();
				}
			}	
		} else {
			for (Enemy e : enemies)
				if (!e.isDead())
					e.setState(Entity.IDLE_DOWN);
				else {
					if (e.getDyingTime() == 0) {
						enemiesToBeRemoved.add(e);
					} else
						e.decreasedyingTime();
				}
		}
		enemies.removeAll(enemiesToBeRemoved);
	}

	//effettua il movimento per ogni player in players
	private void movePlayers() {
		for (Player player : players) {
			switch (player.getState()) {
			case Player.WALKING_LEFT:
				movePlayer(player, Settings.LEFT);
				break;

			case Player.WALKING_RIGHT:
				movePlayer(player, Settings.RIGHT);
				break;

			case Player.WALKING_UP:
				movePlayer(player, Settings.UP);
				break;

			case Player.WALKING_DOWN:
				movePlayer(player, Settings.DOWN);
				break;
			default:
				break;

			}
		}
	}
	
	//muove il player passato se non c'è una collisione di tipo total , altrimenti se ci sono altri tipi di collisionme, ne cambia la direzione ed effettua il movimento 
	//FONDAMENTALE per rendere il gioco giocabile , infatti l'utente viene aiutato a muoversi in maniera fluida se esiste una collisione poco impattante
	private void movePlayer(Player player, int direction) {
		if (player.getState() == Player.DYING_ENEMY || player.getState() == Player.DYING_EXPLOSION) {
			return;
		}
		int collisionType = collisionBlock(player, direction);
		if (collisionType != Gioco.TOTALCOLLISION) {
			if (collisionType == Gioco.NOCOLLISION)
				player.move(direction);
			else {
				switch (collisionType) {
				case Gioco.DOWNCOLLISION:
					direction = Settings.UP;
					if (player.getY() - player.downBlock() * Settings.LOGICBLOCKSIZEY <= player.getSpeed() / 2) {
						player.setY(player.ycenterBlock() * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY
								- player.getHeight() - 1);
						return;
					}
					break;
				case Gioco.UPCOLLISION:
					direction = Settings.DOWN;
					if (player.getY() - player.ycenterBlock() * Settings.LOGICBLOCKSIZEY <= player.getSpeed() / 2) {
						player.setY(player.ycenterBlock() * Settings.LOGICBLOCKSIZEY + 1);
						return;
					}
					break;
				case Gioco.RIGHTCOLLISION:
					direction = Settings.LEFT;
					if (player.getX() - (player.rightBlock()) * Settings.LOGICBLOCKSIZEX <= player.getSpeed() / 2) {
						player.setX(player.xcenterBlock() * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX
								- player.getWidth() - 1);
						return;
					}
					break;
				case Gioco.LEFTCOLLISION:
					direction = Settings.RIGHT;
					if (player.getX() - player.xcenterBlock() * Settings.LOGICBLOCKSIZEX <= player.getSpeed() / 2) {
						player.setX(player.xcenterBlock() * Settings.LOGICBLOCKSIZEX + 1);
						return;
					}
					break;
				}
				player.move(direction, player.getSpeed());

			}
		}
	}

	//fa ottenere un powerUp
	public boolean givePowerUp(Block b, Player player) {
		boolean tmp = player.addPowerUp(b.getPowerUp());
		powerups.remove(new PowerUp(player.xcenterBlock(), player.ycenterBlock(), b.getPowerUp()));
		b.setPowerUp(PowerUp.EMPTY);
		return tmp;
	}
	//verifica se avviene una collisione con un powerup ed eventualmente ne fa prendere gli effetti 
	public void collisionPowerUp() {
		setCollisionPowerUp(false);
		for (Player p : players) {
			if (!p.isDead()) {
				if (givePowerUp(matrix[p.ycenterBlock()][p.xcenterBlock()], p))
					setCollisionPowerUp(true);
			}
		}
	}
	
	/*
	 * Resistuisce un valore di collisione fra : NO_COLLISION , TOTAL_COLLISION , DOWN_COLLISION , RIGHT_COLLISION, LEFT_COLLISION, UP_COLLISION
	 * i valori diversi da NO_COLLISION e TOTAL_COLLISION sono dei valori poco impattanti , ossia non annullano o concedono il movimento in maniera netta
	 * I valori non impattanti permettono al gioco una maggiore giocabilità aiutando l'utente a muoversi anche se con una parte non significativa del player entra in collisione
	 * Perciò viene modificata la direzione di movimento per aiutare a posizionarsi nella posizione corretta
	 * Inoltre la mappa è TOROIDALE , ossia quando si esce dalla mappa si riappare dalla parte opposta
	 * Per esempio : Se la direzione è LEFT , allora se il prossimo blocco nella posizione centrale dell'entità non è " camminabile" , restituisce una TOTALCOLLISION
	 * altrimenti si guarda al lato UP e DOWN, se esiste una collisione di un lato con un blocco che si trova nell posizione successiva nella direzione LEFT , 
	 * allora è restituita la collisione UP_COLLISION o DOWN_COLLISION, se non è presente alcun tipo di collisione è restituito NO_COLLISION
	 */
	public int collisionBlock(Entity e, int dir) {
		switch (dir) {
		
		
		case Settings.LEFT:
			if (e.getX() - e.getSpeed() < 0) {
				if (matrix[e.ycenterBlock()][width - 1].isWalkable()) {
					e.setX((width - 1) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth());
					return Gioco.NOCOLLISION;
				}
				return TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + 1);
				return TOTALCOLLISION;
			} else if (e.downBlock() < height
					&& !matrix[e.downBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + 1);
				if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return TOTALCOLLISION;
				}
				return DOWNCOLLISION;
			} else if (!matrix[e.upBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX);
				if (e.getY() - e.getSpeed() < 0) {
					return TOTALCOLLISION;
				}
				return UPCOLLISION;
			}
			break;
			
			
		case Settings.RIGHT:
			if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEX * width) {
				if (matrix[e.ycenterBlock()][0].isWalkable()) {
					e.setX(0);
					return NOCOLLISION;
				}
				return TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX]
					.isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth() - 1);
				return TOTALCOLLISION;
			} else if (e.downBlock() < height
					&& !matrix[e.downBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth() - 1);
				if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return TOTALCOLLISION;
				}
				return DOWNCOLLISION;
			} else if (e.upBlock() >= 0
					&& !matrix[e.upBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth());
				if (e.getY() - e.getSpeed() < 0) {
					return TOTALCOLLISION;
				}
				return UPCOLLISION;
			}
			break;
			
			
		case Settings.UP:
			if (e.getY() - e.getSpeed() < 0) {
				if (matrix[height - 1][e.xcenterBlock()].isWalkable()) {
					e.setY((height - 1) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight());
					return NOCOLLISION;
				}
				return TOTALCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.xcenterBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				return TOTALCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.rightBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + 1);
				if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEX * width) {
					return TOTALCOLLISION;
				}
				return RIGHTCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.leftBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY);
				if (e.getX() - e.getSpeed() < 0) {
					return TOTALCOLLISION;
				}
				return LEFTCOLLISION;
			}
			break;
			
			
		case Settings.DOWN:
			if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
				if (matrix[0][e.xcenterBlock()].isWalkable()) {
					e.setY(0);
					return NOCOLLISION;
				}
				return TOTALCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.xcenterBlock()]
					.isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				return TOTALCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.rightBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEX * width) {
					return TOTALCOLLISION;
				}
				return RIGHTCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.leftBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight());
				if (e.getX() - e.getSpeed() < 0) {
					return TOTALCOLLISION;

				}
				return LEFTCOLLISION;
			}
			break;
		}
		return NOCOLLISION;
	}
	
	//Verifica quale esplosione deve essre decrementata e quale deve essere rimossa
	public synchronized void checkExplosions() {
		Vector<Explosion> toBeRemoved = new Vector<Explosion>();
		for (Explosion elem : explosions) {
			if (elem.getDurata() == 0) {
				toBeRemoved.add(elem);
			}
			else
				elem.decreaseDurata();
		}
		for (Explosion e : toBeRemoved)
			explosions.remove(e);
	}

	//verifica la collisione con l'esplosione di un player oppure di un nemico e ne setta lo stato a DYING, ne aggiorna lo stato
	public synchronized boolean collisionExplosion() {
		boolean playerCollision = false;
		if (!multiplayer)
			collisionExplosionEnemy = false;
		collidingExplosionPlayer = false;
		for (Explosion elem : explosions) {
			for (Enemy enemy : enemies) {
				if (enemy.getState() != Entity.DYING_EXPLOSION && enemy.collisionCell(elem.getCell())) {
					if (!(enemy instanceof Enemy3) || ((Enemy3) enemy).isVisible()) {
						enemy.setState(Entity.DYING_EXPLOSION);
						collisionExplosionEnemy = true;
						if (enemy instanceof Enemy1) {
							elem.getPlayer().increasePoints(POINTSENEMY1);
						} else if (enemy instanceof Enemy2) {
							elem.getPlayer().increasePoints(POINTSENEMY2);
						} else if (enemy instanceof Enemy3) {
							elem.getPlayer().increasePoints(POINTSENEMY3);
						}
					}
				}
			}
			
			Vector<Player> playerIncreased = new Vector<Player>();
			for (int i = 0 ; i<players.size();++i) {
				Player player = players.get(i);
				if (player.isDead())
					continue;
				if (player.collisionCell(elem.getCell())) {
					playerCollision = true;
					player.setState(Player.DYING_EXPLOSION);

					if (elem.getPlayer() != player) {
						playerIncreased.add(elem.getPlayer());
						elem.getPlayer().increasePoints(POINTSPLAYER);
					}
					playersAlive -= 1;
				}
			}
			for(Player p :playerIncreased) {
				if(p.isDead())
					elem.getPlayer().increasePoints(-POINTSPLAYER);
			}
		}
		collidingExplosionPlayer = playerCollision;
		return playerCollision;

	}
	
	//verifica la presenza di un contatto fra un nemico e un player , se il player è vivo, allora viene settato a DYING
	//se un nemico è di tipo 3 , allora se è non è visibile non genera collisione
	public boolean collisionEnemyPlayer() {
		boolean collision = false;
		for (Enemy enemy : enemies) {
			for (Player player : players) {
				if (player.isDead())
					continue;
				if (enemy.getState() != Entity.DYING_EXPLOSION) {
					if (((player.rightSide() <= enemy.rightSide() && player.rightSide() >= enemy.getX())
							|| (player.getX() <= enemy.rightSide() && player.getX() >= enemy.getX()))
							&& ((player.downSide() <= enemy.downSide() && player.downSide() >= enemy.getY())
									|| (player.getY() <= enemy.downSide() && player.downSide() >= enemy.getY()))
							|| (enemy.xcenterBlock() == player.xcenterBlock()
									&& enemy.ycenterBlock() == player.ycenterBlock())) {
						if (!(enemy instanceof Enemy3) || ((Enemy3) enemy).isVisible()) {
							player.setState(Player.DYING_ENEMY);
							playersAlive -= 1;
							collision = true;
						}
					}
				}
			}
		}
		collidingEnemyPlayer = collision;
		return collision;
	}
	
	/*
	 * Verifica lo stato della bomba : 
	 * se non ha raggiunto il tempo di esplosione , allora viene decrementato , 
	 * altrimenti rimossa la bomba e  viene creata l'esplosione , ed eventualmente azionata la distruzione dei mattoni
	 * ossia dato un raggio r della bomba, si creano le esplosioni lontane al massimo r nelle 4 direzioni , se è incontrato un ostacolo allora si ferma e non continua nella direzione considerata
	 * L'esplosione è toroidale, ossia se esce fuori dalla mappa , si crea dall'altra parta
	 * */
	public synchronized void checkBombs() {
		boolean stopUp;
		boolean stopDown;
		boolean stopLeft;
		boolean stopRight;
		Vector<Bomb> toBeRemoved = new Vector<Bomb>();
		bombExploded = false;
		bombIncreased = false;
		for (int k = 0; k < bombs.size(); ++k) {
			// matrix[bomb.getY()+1 ][bomb.getX()] = Settings.EXPLOSION;
			stopUp = false;
			stopDown = false;
			stopLeft = false;
			stopRight = false;
			if (bombs.get(k).getTimer() == Settings.BOMBTIME - 1) {
				bombIncreased = true;
			}
			if (bombs.get(k).getTimer() == 0) {
				int type = Explosion.MIDDLE;
				matrix[bombs.get(k).getYCell()][bombs.get(k).getXCell()].setType(Block.FLOOR);
				explosions.add(new Explosion(bombs.get(k).getXCell(), bombs.get(k).getYCell(), Explosion.CENTRAL,
						Settings.RIGHT, bombs.get(k).getPlayer()));
				for (int i = 1; i <= bombs.get(k).getRadius(); ++i) {
					if (i == bombs.get(k).getRadius())
						type = Explosion.LAST;
					if (!stopDown) {
						if (matrix[(bombs.get(k).getYCell() + i) % height][bombs.get(k).getXCell()].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getXCell(),
									(bombs.get(k).getYCell() + i) % height, type, Settings.DOWN,
									bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							if (matrix[(bombs.get(k).getYCell() + i) % height][bombs.get(k).getXCell()].isBreakable())
								getBrick(bombs.get(k).getXCell(), (bombs.get(k).getYCell() + i) % height)
										.decreaseExplosionTime();
							stopDown = true;
						}
					}

					if (!stopUp) {
						if (bombs.get(k).getYCell() - i >= 0) {
							if (matrix[bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isWalkable()) {
								Explosion tmp = new Explosion(bombs.get(k).getXCell(), bombs.get(k).getYCell() - i,
										type, Settings.UP, bombs.get(k).getPlayer());
								explosions.add(tmp);

							} else {
								if (matrix[bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isBreakable())
									getBrick(bombs.get(k).getXCell(), bombs.get(k).getYCell() - i)
											.decreaseExplosionTime();
								stopUp = true;
							}
						}
						if (bombs.get(k).getYCell() - i < 0) {
							if (matrix[height + bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isWalkable()) {
								Explosion tmp = new Explosion(bombs.get(k).getXCell(),
										height + bombs.get(k).getYCell() - i, type, Settings.UP,
										bombs.get(k).getPlayer());
								explosions.add(tmp);

							} else {
								if (matrix[height + bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isBreakable())
									getBrick(bombs.get(k).getXCell(), height + bombs.get(k).getYCell() - i)
											.decreaseExplosionTime();
								stopUp = true;
							}
						}
					}

					if (!stopRight) {
						if (matrix[bombs.get(k).getYCell()][(bombs.get(k).getXCell() + i) % width].isWalkable()) {
							Explosion tmp = new Explosion((bombs.get(k).getXCell() + i) % width,
									bombs.get(k).getYCell(), type, Settings.RIGHT, bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							if (matrix[bombs.get(k).getYCell()][(bombs.get(k).getXCell() + i) % width].isBreakable())
								getBrick((bombs.get(k).getXCell() + i) % width, bombs.get(k).getYCell())
										.decreaseExplosionTime();
							stopRight = true;

						}
					}

					if (!stopLeft) {
						if (bombs.get(k).getXCell() - i >= 0) {
							if (matrix[bombs.get(k).getYCell()][bombs.get(k).getXCell() - i].isWalkable()) {
								Explosion tmp = new Explosion(bombs.get(k).getXCell() - i, bombs.get(k).getYCell(),
										type, Settings.LEFT, bombs.get(k).getPlayer());

								explosions.add(tmp);
							} else {
								stopLeft = true;
								if (matrix[bombs.get(k).getYCell()][bombs.get(k).getXCell() - i].isBreakable())
									getBrick(bombs.get(k).getXCell() - i, bombs.get(k).getYCell())
											.decreaseExplosionTime();
							}
						}
						if (bombs.get(k).getXCell() - i < 0) {
							if (matrix[bombs.get(k).getYCell()][width + bombs.get(k).getXCell() - i].isWalkable()) {
								Explosion tmp = new Explosion(width + bombs.get(k).getXCell() - i,
										bombs.get(k).getYCell(), type, Settings.LEFT, bombs.get(k).getPlayer());

								explosions.add(tmp);
							} else {
								stopLeft = true;
								if (matrix[bombs.get(k).getYCell()][width + bombs.get(k).getXCell() - i].isBreakable())
									getBrick(width + bombs.get(k).getXCell() - i, bombs.get(k).getYCell())
											.decreaseExplosionTime();
							}
						}
					}
				}
				toBeRemoved.add(bombs.get(k));
			} else
				bombs.get(k).setTimer(bombs.get(k).getTimer() - 1);
			if (toBeRemoved.size() != 0) {
				bombExploded = true;
				for (Bomb r : toBeRemoved) {
					r.getPlayer().increaseBombs();
					bombs.remove(r);
				}
			}
		}
	}

	public void print() {
		for (int i = 0; i < matrix.length; ++i) {
			for (int j = 0; j < matrix[0].length; ++j)
				if (matrix[i][j].getType() != Block.IRON)
					System.out.print(matrix[i][j].getType() + " ");
				else
					System.out.print("  ");
			System.out.println();

		}
	}

	public Block[][] getMatrix() {
		return matrix;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Vector<PowerUp> getPowerups() {
		return powerups;
	}
	
	//setta i nuovi powerup
	public synchronized void setPowerups(Vector<PowerUp> powerups) {
		for (PowerUp p : this.powerups) {
			matrix[p.getCell().getyCell()][p.getCell().getxCell()].setPowerUp(PowerUp.EMPTY);
		}
		this.powerups.clear();
		this.powerups = powerups;
		for (PowerUp p : this.powerups) {
			matrix[p.getCell().getyCell()][p.getCell().getxCell()].setPowerUp(p.getType());
		}
	}
	// permette di ottenere il player del tipo considerato
	public Player getPlayer(int p) {
		if (p > Settings.PLAYER5 || p < Settings.PLAYER1 || p - Settings.PLAYER1 >= players.size()) {
			return null;
		}
		return players.get(p - Settings.PLAYER1);
	}

	public synchronized Vector<Enemy> getEnemies() {
		return enemies;
	}

	public synchronized void setEnemies(Vector<Enemy> enemies) {
		this.enemies = enemies;
	}

	public synchronized Vector<Bomb> getBombs() {
		return bombs;
	}

	public synchronized Vector<Explosion> getExplosions() {
		return explosions;
	}
	//resituisce il mattone nella posizione x , y
	private Brick getBrick(int xCell, int yCell) {
		for (Brick brick : bricks) {
			if (brick.equals(xCell , yCell))
				return brick;
		}
		return null;
	}
	//controlla la distruzione dei mattoni: ossia se il tempo di distruzione è stato drecrementato almeno una volta(checkBombs) allora viene decrementato ancora , altrimenti no
	public void checkBricks() {
		Vector<Brick> toBeRemoved = new Vector<Brick>();
		for (Brick brick : bricks) {
			if (brick.getExplosionTime() <= 0) {
				toBeRemoved.add(brick);
				matrix[brick.getCell().getyCell()][brick.getCell().getxCell()].explode();
			}
			if (brick.inExplosion())
				brick.decreaseExplosionTime();
		}
		bricks.removeAll(toBeRemoved);
	}

	public Vector<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(Vector<Brick> bricks) {
		this.bricks = bricks;
	}

	public int getPlayersAlive() {
		return playersAlive;
	}

	public void setBombs(Vector<Bomb> bombs) {
		this.bombs = bombs;
	}

	public synchronized void setExplosions(Vector<Explosion> explosions) {
		this.explosions = explosions;
	}

	public boolean isMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean multiplayer) {
		this.multiplayer = multiplayer;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public boolean isBattleRoyale() {
		return battleRoyale;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}

	public Block getElement(int y, int x) {
		if (x > width || y > height || x < 0 || y < 0)
			return null;
		return matrix[y][x];
	}
	
	//permette di aggiungere la bomba da parte di un player
	public synchronized void addBomb(Player player) {
		bombIncreased = false;
		if (player.getBombs() > 0) {
			int px = player.xcenterBlock();
			int py = player.ycenterBlock();

			if (player.getState() == Player.WALKING_DOWN)
				py = player.upBlock();
			else if (player.getState() == Player.WALKING_LEFT) {
				px = player.rightBlock();
			}
			Bomb b = new Bomb(px, py, player.getRadius(), player);
			if (!bombs.contains(b)) {
				bombs.add(b);
				matrix[py][px].setType(Block.BOMB);
				player.decreaseBombs();
			}
		}
	}

	public int countsAlive() {
		int alive = 0;
		for (Player player : players) {
			if (!player.isDead())
				alive++;
		}
		playersAlive = alive;
		return alive;
	}

	public Vector<Player> getAlives() {
		Vector<Player> alive = new Vector<Player>();
		for (Player p : players) {
			if (!p.isDead())
				alive.add(p);
		}
		return alive;
	}

	public Vector<Player> getPlayers() {
		return players;
	}
	
	//calcola il prossimo passo del gioco (SinglePlayer o Server)
	public void next() {
		updateEnemy();
		checkBombs();
		checkExplosions();
		checkBricks();
		movePlayers();
		collisionPowerUp();
		collisionExplosion();
		collisionEnemyPlayer();
		if (finishLevel())
			setGameOver(true);
	}
	//applica , aggiorna ed elabora i dati non gestiti dal server oppure non utili a causa del GameOver
	public void updateGameMultiplayer() {
		checkExplosions();
		checkBombs();
		checkBricks();
		collisionPowerUp();
		if (finishLevel())
			setGameOver(true);
	}
	
	//ripristina a false i valori booleani che segnalano l'avvenimento di un determinato evento nel gioco
	//quando un evento accade in un determinato passo, allora il valore corrispondente è posto a true 
	// i booleani possono essere letti con i metodi getter. 
	public void resetState() {
		setCollisionEnemyPlayer(false);
		setCollisionExplosionPlayer(false);
		setBombIncrease(false);
		setBombExploded(false);
		setCollisionPowerUp(false);
		setCollisionExplosionEnemy(false);
	}
	
	/**
	 * Restituisce true se il gioco è finito : 
	 * SINGLEPLAYER : tutti i nemici sono morti , il player è morto 
	 * MULTIPLAYER : tutti i nemici sono morti , un player è morto 
	 * BATTLEROYALE : il numero di player vivi sono <=1
	 */
	public boolean finishLevel() {
		if (battleRoyale) {
			if (playersAlive <= 1) {
				for (Player player : players)
					if (!player.isDead())
						player.setState(Player.WINNING);
				return true;
			}
			return false;
		} else if (!multiplayer) {
			Player player1 = getPlayer(Settings.PLAYER1);
			if ((!player1.isDead() && enemies.size() == 0)) {
				player1.setState(Player.WINNING);
				return true;
			} else if (player1.isDead())
				return true;
		} else if (multiplayer) {
			Player player1 = getPlayer(Settings.PLAYER1);
			Player player2 = getPlayer(Settings.PLAYER2);
			boolean DEAD1 = true;
			boolean DEAD2 = true;
			if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION)
				DEAD1 = false;
			if (player2.getState() == Player.DYING_ENEMY || player2.getState() == Player.DYING_EXPLOSION)
				DEAD2 = false;
			if (enemies.size() == 0 || !DEAD1 || !DEAD2) {
				if (DEAD1 && !DEAD2)
					player1.setState(Player.WINNING);
				else if (!DEAD1 && DEAD2) {
					player2.setState(Player.WINNING);
				} else if (DEAD1 && DEAD2) {
					if (player2.getPoints() > player1.getPoints()) {
						player2.setState(Player.WINNING);
						player1.setState(Player.IDLE_DOWN);
					} else if (player1.getPoints() > player2.getPoints()) {
						player1.setState(Player.WINNING);
						player2.setState(Player.IDLE_DOWN);
					} else {
						player2.setState(Player.IDLE_DOWN);
						player1.setState(Player.IDLE_DOWN);
					}
				}
				return true;
			}
		}

		return false;
	}
	
	//indica se il tempo è scaduto e fa terminare il gioco
	public void timeOut() {
		for (Enemy e : enemies) {
			e.setState(Entity.IDLE_DOWN);
		}
		gameOver = true;
		for (Player player : players) {
			if (!player.isDead())
				player.setState(Player.DYING_ENEMY);
		}
	}

	//calcola e imposta il risultato basato gerarchicamente su :
	/*MULTIPLAYER:
	 * 1) VITA 
	 * 2) Punti
	 * ossia il player vivo può vincere , se tutti sono morti , vince quello con più punti , se sono uguali allora PAREGGIO
	 * BATTLEROYALE : 
	 * vince chi riesce a sopravvivere agli altri player
	 */
	public void setResult(int p) {
		if (multiplayer) {
			if (battleRoyale) {
				if (getPlayer(p).getState() == Player.WINNING) {
					System.out.println("YOU WIN!!!");
					result = VICTORY;
					return;
				}
				result = LOSS;
			} else {
				Player player1 = getPlayer(Settings.PLAYER1);
				Player player2 = getPlayer(Settings.PLAYER2);
				if (p == Settings.PLAYER2) {
					player1 = player2;
					player2 = getPlayer(Settings.PLAYER1);
				}
				if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION
						&& player2.getState() != Player.DYING_ENEMY && player2.getState() != Player.DYING_EXPLOSION)
					result = LOSS;
				else if (player2.getState() == Player.DYING_ENEMY || player2.getState() == Player.DYING_EXPLOSION
						&& player1.getState() != Player.DYING_ENEMY && player1.getState() != Player.DYING_EXPLOSION)
					result = VICTORY;
				else if (player1.getPoints() > player2.getPoints())
					result = VICTORY;
				else if (player1.getPoints() < player2.getPoints())
					result = LOSS;
				else
					result = DRAW;
			}
		} else {
			Player player1 = getPlayer(Settings.PLAYER1);
			if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION) {
				result = LOSS;
			} else {
				result = VICTORY;
			}
		}
	}

	public synchronized boolean isCollisionPowerUp() {
		return collidingPowerUp;
	}

	public synchronized void setCollisionPowerUp(boolean collisionPowerUp) {
		this.collidingPowerUp = collisionPowerUp;
	}

	public synchronized boolean isBombIncrease() {
		return bombIncreased;
	}

	public synchronized void setBombIncrease(boolean bombIncrease) {
		this.bombIncreased = bombIncrease;
	}

	public synchronized boolean isBombExploded() {
		return bombExploded;
	}

	public synchronized void setBombExploded(boolean bombExploded) {
		this.bombExploded = bombExploded;
	}

	public synchronized boolean isCollisionEnemyPlayer() {
		return collidingEnemyPlayer;
	}

	public int getResult() {
		return result;
	}

	public synchronized void setCollisionEnemyPlayer(boolean collisionEnemyPlayer) {
		this.collidingEnemyPlayer = collisionEnemyPlayer;
	}

	public synchronized boolean isCollisionExplosionPlayer() {
		return collidingExplosionPlayer;
	}

	public synchronized void setCollisionExplosionPlayer(boolean collisionExplosionPlayer) {
		this.collidingExplosionPlayer = collisionExplosionPlayer;
	}

	public synchronized boolean isCollisionExplosionEnemy() {
		return collisionExplosionEnemy;
	}

	public synchronized void setCollisionExplosionEnemy(boolean collisionExplosionEnemy) {
		this.collisionExplosionEnemy = collisionExplosionEnemy;
	}

}
