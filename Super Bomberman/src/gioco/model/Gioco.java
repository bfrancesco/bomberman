package gioco.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import gioco.utilities.Settings;

public class Gioco {
	public static final int VICTORY = 0;
	public static final int DRAW = 1;
	public static final int LOSS = 2;
	
	private int pointsEnemy1 = 1000;
	private int pointsEnemy2 = 1500;
	private int pointsEnemy3 = 3000;
	private int pointsPlayer = 5000;
	private int time = 150;
	private Block[][] matrix;
	private Vector<Brick> bricks; 
	private int height =  Settings.LOGICHEIGHT;
	private int width = Settings.LOGICWIDTH;
	Vector<Player> players;
	private Vector<Enemy> enemies;
	private Vector<Bomb> bombs;
	private Vector<Explosion> explosions;
	private boolean multiplayer;
	private boolean gameOver;
	private boolean started;
	private boolean battleRoyale;
	private int playersAlive;
	private int map;

	public Gioco(boolean multiplayer, boolean battleRoyale, int map) {
		this.multiplayer = multiplayer;
		enemies = new Vector<Enemy>();
		bombs = new Vector<Bomb>();
		explosions = new Vector<Explosion>();
		matrix = new Block[height][width];
		gameOver = false;
		started = false;
		players = new Vector<Player>();
		bricks = new Vector<Brick>();
		if (multiplayer)
			this.battleRoyale = battleRoyale;
		else
			this.battleRoyale = false;
		try {
			
			this.map =  map;
			String mapname = "Map"+map;
			loadMap(mapname);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	/*
	 * DA CONTROLLARE : Player1 non si sovrappone al 2 multiplayer aggiungere errori
	 * in caso di mancata connessione aggiungere nemici gestione mappa Lettura della
	 * mappa scelta su cui si svilupperā il gioco
	 */
	public void loadMap(String mapName) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("src/gioco/resources/maps/" + mapName + ".txt"));
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
			boolean speed = false;
			boolean nbombs = false;
			boolean radius = false;
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
						enemies.add( new Enemy1(i * Settings.LOGICBLOCKSIZEX,
								k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					case 'M':
						matrix[k][i] = new Block(Block.FLOOR);
						enemies.add( new Enemy2(i * Settings.LOGICBLOCKSIZEX,
								k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					case 'G':
						matrix[k][i] = new Block(Block.FLOOR);
						enemies.add(new Enemy3(i * Settings.LOGICBLOCKSIZEX,
								k * Settings.LOGICBLOCKSIZEY, enemyID));
						enemyID += 1;
						break;
					default:
						matrix[k][i] = new Block(Block.FLOOR);
					}
				}
				k++;
			}
		} finally {
			if (reader != null)
				reader.close();
		}
	}
	/*
	 * public void moveEnemy(Enemy e) { if (!collisionBlock(e, e.getDirection()) &&
	 * !collisionBombs(e, e.getDirection())) e.move(); else { e.changeDirection(); }
	 * 
	 * 
	 * }
	 * 
	 * public void movePlayer(Player player, int direction) { if (player.getState()
	 * == Player.DEAD) return;
	 * 
	 * if (!collisionBlock(player1, direction) == 0 && !collisionBombs(player1,
	 * direction)) { player.move(direction); } }
	 * 
	 * public int collisionBlock(Entity e, int dir) { switch (dir) { case
	 * Settings.LEFT: if (e.getX() - e.getSpeed() < 0 ||
	 * !matrix[e.upBlock()][(e.getX() - e.getSpeed()) /
	 * Settings.BLOCKSIZEX].isWalkable() || !matrix[e.downBlock()][(e.getX() -
	 * e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable())
	 * 
	 * return true; break; case Settings.RIGHT: if (e.rightSide() + e.getSpeed() >=
	 * Settings.BLOCKSIZEX * 13 || !matrix[e.upBlock()][(e.rightSide() +
	 * e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable() ||
	 * !matrix[e.downBlock()][(e.rightSide() + e.getSpeed()) /
	 * Settings.BLOCKSIZEX].isWalkable()) { return true; } break; case Settings.UP:
	 * if (e.getY() - e.getSpeed() < 0 || !matrix[(e.getY() - e.getSpeed()) /
	 * Settings.BLOCKSIZEY][e.rightBlock()].isWalkable() || !matrix[(e.getY() -
	 * e.getSpeed()) / Settings.BLOCKSIZEY][e.leftBlock()].isWalkable()) return
	 * true; break; case Settings.DOWN: if (e.downSide() + e.getSpeed() >=
	 * Settings.BLOCKSIZEY * 13 || !matrix[(e.downSide() + e.getSpeed()) /
	 * Settings.BLOCKSIZEY][e.rightBlock()].isWalkable() || !matrix[(e.downSide() +
	 * e.getSpeed()) / Settings.BLOCKSIZEY][e.leftBlock()].isWalkable()) return
	 * true; break; } return false; }
	 */

	public synchronized void updateEnemy() {
		if (!gameOver) {
			Vector<Enemy> enemiesToBeRemoved = new Vector<Enemy>();
			for (Enemy e : enemies) {
				if (e.getState() != Entity.DYING_EXPLOSION) {
					if (e instanceof Enemy3) {
						Enemy3 e3 = (Enemy3) e;
						e3.changeVisibility();
						if (e3.getUnseenTime() == e3.getRandomVisibilityTime() * 80 / 100 && !e3.isVisible())
							if (multiplayer)
								e3.Teleport(players.get(0).xcenterBlock(), players.get(0).ycenterBlock(),
										players.get(1).xcenterBlock(), players.get(1).ycenterBlock());
							else
								e3.Teleport(players.get(0).xcenterBlock(), players.get(0).ycenterBlock());
					}
					if (collisionBlock(e, e.getDirection()) != Settings.TOTALCOLLISION
							/* && !collisionBombs(e, e.getDirection()) */ && e.getState() != Entity.IDLE_DOWN) {
						e.move();
					} else {
						ArrayList<Integer> directions = new ArrayList<Integer>();
						if (e.getDirection() != Settings.DOWN && e.ycenterBlock() + 1 < height
								&& matrix[e.ycenterBlock() + 1][e.xcenterBlock()].isWalkable())
							directions.add(Settings.DOWN);
						if (e.getDirection() != Settings.LEFT && e.xcenterBlock() - 1 >= 0
								&& matrix[e.ycenterBlock()][e.xcenterBlock() - 1].isWalkable())
							directions.add(Settings.LEFT);
						if (e.getDirection() != Settings.UP && e.ycenterBlock() - 1 >= 0
								&& matrix[e.ycenterBlock() - 1][e.xcenterBlock()].isWalkable())
							directions.add(Settings.UP);
						if (e.getDirection() != Settings.RIGHT && e.xcenterBlock() + 1 < width
								&& matrix[e.ycenterBlock()][e.xcenterBlock() + 1].isWalkable())
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
			enemies.removeAll(enemiesToBeRemoved);
		} else {
			for (Enemy e : enemies)
				if(!e.isDead())
				e.setState(Entity.IDLE_DOWN);
		}
	}

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

	private void movePlayer(Player player, int direction) {
		if (player.getState() == Player.DYING_ENEMY || player.getState() == Player.DYING_EXPLOSION) {
			return;
		}
		int collisionType = collisionBlock(player, direction);
		if (collisionType != Settings.TOTALCOLLISION ) {
			if (collisionType == Settings.NOCOLLISION)
				player.move(direction);
			else {
				switch (collisionType) {
				case Settings.DOWNCOLLISION:
					direction = Settings.UP;
					if (player.getY() - player.downBlock() * Settings.LOGICBLOCKSIZEY <= player.getSpeed() / 2) {
						player.setY(player.ycenterBlock() * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY
								- player.getHeight() - 1);
						return;
					}
					break;
				case Settings.UPCOLLISION:
					direction = Settings.DOWN;
					if (player.getY() - player.ycenterBlock() * Settings.LOGICBLOCKSIZEY <= player.getSpeed() / 2) {
						player.setY(player.ycenterBlock() * Settings.LOGICBLOCKSIZEY + 1);
						return;
					}
					break;
				case Settings.RIGHTCOLLISION:
					direction = Settings.LEFT;
					if (player.getX() - (player.rightBlock()) * Settings.LOGICBLOCKSIZEX <= player.getSpeed() / 2) {
						player.setX(player.xcenterBlock() * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX
								- player.getWidth() - 1);
						return;
					}
					break;
				case Settings.LEFTCOLLISION:
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

	public int collisionBlock(Entity e, int dir) {
		switch (dir) {
		case Settings.LEFT:
			if (e.getX() - e.getSpeed() < 0) {
				if (matrix[e.ycenterBlock()][width - 1].isWalkable())
					e.setX((width - 1) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth());
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + 1);
				return Settings.TOTALCOLLISION;
			} else if (e.downBlock()<height && !matrix[e.downBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + 1);
				if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.DOWNCOLLISION;
			} else if (!matrix[e.upBlock()][(e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX);
				if (e.getY() - e.getSpeed() < 0) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.UPCOLLISION;
			}
			break;
		case Settings.RIGHT:
			if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEX * width) {
				if (matrix[e.ycenterBlock()][0].isWalkable())
					e.setX(0);
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth() - 1);
				return Settings.TOTALCOLLISION;
			} else if (e.downBlock() <height && !matrix[e.downBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth() - 1);
				if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.DOWNCOLLISION;
			} else if (e.upBlock() >= 0 && !matrix[e.upBlock()][(e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX].isWalkable()) {
				e.setX((e.xcenterBlock()) * Settings.LOGICBLOCKSIZEX + Settings.LOGICBLOCKSIZEX - e.getWidth());
				if (e.getY() - e.getSpeed() < 0) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.UPCOLLISION;
			}
			break;
		case Settings.UP:
			if (e.getY() - e.getSpeed() < 0) {
				if (matrix[height - 1][e.xcenterBlock()].isWalkable())
					e.setY((height - 1) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight());
				return Settings.TOTALCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.xcenterBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				return Settings.TOTALCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.rightBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + 1);
				if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.RIGHTCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.leftBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY);
				if (e.getX() - e.getSpeed() < 0) {
					return Settings.TOTALCOLLISION;
				}
					return Settings.LEFTCOLLISION;
			}
			break;
		case Settings.DOWN:
			if (e.downSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
				if (matrix[0][e.xcenterBlock()].isWalkable())
					e.setY(0);
				return Settings.TOTALCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.xcenterBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				return Settings.TOTALCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.rightBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight() - 1);
				if (e.rightSide() + e.getSpeed() >= Settings.LOGICBLOCKSIZEY * height) {
					return Settings.TOTALCOLLISION;
				}
				return Settings.RIGHTCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY][e.leftBlock()].isWalkable()) {
				e.setY((e.ycenterBlock()) * Settings.LOGICBLOCKSIZEY + Settings.LOGICBLOCKSIZEY - e.getHeight());
				if (e.getX() - e.getSpeed() < 0) {
					return Settings.TOTALCOLLISION;

				}
				return Settings.LEFTCOLLISION;
			}
			break;
		}
		return Settings.NOCOLLISION;
	}

	/*public boolean collisionBombs(Entity e, int dir) {
		switch (dir) {
		case Settings.LEFT:
			for (Bomb elem : bombs) {
				if (elem.getXCell() == (e.getX() - e.getSpeed()) / Settings.LOGICBLOCKSIZEX
						&& (elem.getYCell() == e.upBlock() || elem.getYCell() == e.downBlock())
						&& elem.getXCell() != e.xcenterBlock())
					return true;
			}
			break;
		case Settings.RIGHT:
			for (Bomb elem : bombs) {
				if (elem.getXCell() == (e.rightSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEX
						&& (elem.getYCell() == e.upBlock() || elem.getYCell() == e.downBlock())
						&& elem.getXCell() != e.xcenterBlock())
					return true;
			}
			break;
		case Settings.UP:
			for (Bomb elem : bombs) {
				if (elem.getYCell() == (e.getY() - e.getSpeed()) / Settings.LOGICBLOCKSIZEY
						&& (elem.getXCell() == e.leftBlock() || elem.getXCell() == e.rightBlock())
						&& elem.getYCell() != e.ycenterBlock())
					return true;
			}
			break;
		case Settings.DOWN:
			for (Bomb elem : bombs) {
				if (elem.getYCell() == (e.downSide() + e.getSpeed()) / Settings.LOGICBLOCKSIZEY
						&& (elem.getXCell() == e.leftBlock() || elem.getXCell() == e.rightBlock())
						&& elem.getYCell() != e.ycenterBlock())
					return true;
			}
			break;
		}
		return false;
	}*/

	public synchronized void checkExplosions() {
		Vector<Explosion> toBeRemoved = new Vector<Explosion>();
		for (Explosion elem : explosions) {
			if (elem.getDurata() == 0)
				toBeRemoved.add(elem);

			else
				elem.decreaseDurata();
		}
		for (Explosion e : toBeRemoved)
			explosions.remove(e);
	}

	public synchronized boolean collisionExplosion() {
		boolean playerCollision = false;
		for (Explosion elem : explosions) {
			for (Enemy enemy : enemies) {
				if (enemy.getState() != Entity.DYING_EXPLOSION && (elem.getXCell() == enemy.xcenterBlock()
						&& (elem.getYCell() == enemy.ycenterBlock())
						|| ((elem.getXCell() == enemy.rightBlock() || elem.getXCell() == enemy.leftBlock())
								&& (elem.getYCell() == enemy.upBlock() || elem.getYCell() == enemy.downBlock())))) {
					if (!(enemy instanceof Enemy3) || ((Enemy3) enemy).isVisible()) {
						enemy.setState(Entity.DYING_EXPLOSION);
						if (enemy instanceof Enemy1) {
							elem.getPlayer().increasePoints(pointsEnemy1);
						} else if (enemy instanceof Enemy2) {
							elem.getPlayer().increasePoints(pointsEnemy2);
						} else if (enemy instanceof Enemy3) {
							elem.getPlayer().increasePoints(pointsEnemy3);
						}
					}
				}
			}

			for (Player player : players) {
				if (player.isDead())
					continue;
				if (elem.getXCell() == player.xcenterBlock() && (elem.getYCell() == player.ycenterBlock())
						|| ((elem.getXCell() == player.rightBlock() || elem.getXCell() == player.leftBlock())
								&& (elem.getYCell() == player.upBlock() || elem.getYCell() == player.downBlock()))) {
					playerCollision = true;
					player.setState(Player.DYING_EXPLOSION);
					if(elem.getPlayer() != player)
						elem.getPlayer().increasePoints(pointsPlayer);
					playersAlive -= 1;
				}
			}
		}

		return playerCollision;

	}

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
		return collision;
	}

	public synchronized void checkBombs() {
		boolean stopUp;
		boolean stopDown;
		boolean stopLeft;
		boolean stopRight;
		Vector<Bomb> toBeRemoved = new Vector<Bomb>();
		for (int k = 0; k < bombs.size(); ++k) {
			// matrix[bomb.getY()+1 ][bomb.getX()] = Settings.EXPLOSION;
			stopUp = false; 
			stopDown = false;
			stopLeft = false;
			stopRight = false;

			if (bombs.get(k).getTimer() == 0) {
				int type = Explosion.MIDDLE;
				matrix[bombs.get(k).getYCell()][bombs.get(k).getXCell()].setType(Block.FLOOR);
				explosions.add(new Explosion(bombs.get(k).getXCell(), bombs.get(k).getYCell(), Explosion.CENTRAL,
						Settings.RIGHT, bombs.get(k).getPlayer()));
				for (int i = 1; i <= bombs.get(k).getPlayer().getRadius(); ++i) {
					// CONTROLLO L'ESPLOSIONE DI SOPRA
					if (i == bombs.get(k).getPlayer().getRadius())
						type = Explosion.LAST;
					if (!stopDown) {
						if (matrix[(bombs.get(k).getYCell() + i) % height][bombs.get(k).getXCell()].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getXCell(),
									(bombs.get(k).getYCell() + i) % height, type, Settings.DOWN,
									bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							if (matrix[(bombs.get(k).getYCell() + i) % height][bombs.get(k).getXCell()].isBreakable())
								getBrick(bombs.get(k).getXCell(),(bombs.get(k).getYCell() + i) % height).decreaseExplosionTime();
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
									getBrick(bombs.get(k).getXCell(),bombs.get(k).getYCell() - i).decreaseExplosionTime();
								stopUp = true;
							}
						}
						if(bombs.get(k).getYCell() - i < 0) {
							if (matrix[height+bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isWalkable()) {
								Explosion tmp = new Explosion(bombs.get(k).getXCell(),height + bombs.get(k).getYCell() - i,
										type, Settings.UP, bombs.get(k).getPlayer());
								explosions.add(tmp);

							} else {
								if (matrix[height+bombs.get(k).getYCell() - i][bombs.get(k).getXCell()].isBreakable())
									getBrick(bombs.get(k).getXCell(),height+bombs.get(k).getYCell() - i).decreaseExplosionTime();
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
								getBrick((bombs.get(k).getXCell() + i) % width,bombs.get(k).getYCell()).decreaseExplosionTime();
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
									getBrick(bombs.get(k).getXCell() - i,bombs.get(k).getYCell()).decreaseExplosionTime();
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
									getBrick(width + bombs.get(k).getXCell() - i, bombs.get(k).getYCell()).decreaseExplosionTime();
									//matrix[bombs.get(k).getYCell()][width + bombs.get(k).getXCell() - i].explode();
							}
						}
					}
				}
				toBeRemoved.add(bombs.get(k));
			} else
				bombs.get(k).setTimer(bombs.get(k).getTimer() - 1);

			for (Bomb r : toBeRemoved) {
				r.getPlayer().increaseBombs();
				bombs.remove(r);

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

	public Player getPlayer(int p) {
		if (p > Settings.PLAYER5 || p < Settings.PLAYER1 || p - Settings.PLAYER1 >= players.size()) {
			System.out.println("Player non valido");
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

	private Brick getBrick(int xCell , int yCell) {
		for(Brick brick : bricks) {
			if(brick.equals(xCell , yCell))
				return brick;
		}
		return null;
	}
	
	public void checkBricks() {
		Vector<Brick> toBeRemoved = new Vector<Brick>();
		for(Brick brick : bricks) {
			if(brick.getExplosionTime()<=0) {
				toBeRemoved.add(brick);
				matrix[brick.getyCell()][brick.getxCell()].explode();
			}
			if(brick.inExplosion())
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

	public synchronized void addBomb(Player player) {
		if (player.getBombs() > 0) {
			int px = player.xcenterBlock();
			int py = player.ycenterBlock();

			if (player.getState() == Player.WALKING_DOWN)
				py = player.upBlock();
			else if (player.getState() == Player.WALKING_LEFT) {
				px = player.rightBlock();
			}
			Bomb b = new Bomb(px, py, player);
			if (!bombs.contains(b)) {
				bombs.add(b);
				matrix[py][px].setType(Block.BOMB);
				player.decreaseBombs();
			}
		}
	}
	
	public int countsAlive() {
		int alive=0;
		for(Player player: players) {
			if(!player.isDead())
				alive++;
		}
		playersAlive = alive;
		return alive;
	}

	public Vector<Player> getPlayers() {
		return players;
	}

	public void next() {
		checkBombs();
		checkExplosions();
		checkBricks();
		movePlayers();
		collisionExplosion();
		collisionEnemyPlayer();
		updateEnemy();
		if (finishLevel())
			setGameOver(true);

	}

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
			if ((player1.getState() != Player.DYING_ENEMY && player1.getState() != Player.DYING_EXPLOSION
					&& enemies.size() == 0)) {
				player1.setState(Player.WINNING);
				return true;
			} else if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION)
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

	public void timeOut() {
		for (Enemy e : enemies) {
			e.setState(Entity.IDLE_DOWN);
		}
		gameOver = true;
		for(Player player : players) {
			if(!player.isDead())
				player.setState(Player.DYING_ENEMY);
		}
	}

	public int results(int p) {
		if (multiplayer) {
			if (battleRoyale) {
				if (getPlayer(p).getState() == Player.WINNING) {
					System.out.println("YOU WIN!!!");
					return VICTORY;
				}
				return LOSS;
			} else {
				Player player1 = getPlayer(Settings.PLAYER1);
				Player player2 = getPlayer(Settings.PLAYER2);
				if (p == Settings.PLAYER2) {
					player1 = player2;
					player2 = getPlayer(Settings.PLAYER1);
				}
				if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION
						&& player2.getState() != Player.DYING_ENEMY && player2.getState() != Player.DYING_EXPLOSION)
					return LOSS;
				else if (player2.getState() == Player.DYING_ENEMY || player2.getState() == Player.DYING_EXPLOSION
						&& player1.getState() != Player.DYING_ENEMY && player1.getState() != Player.DYING_EXPLOSION)
					return VICTORY;
				if (player1.getPoints() > player2.getPoints())
					return VICTORY;
				else if (player1.getPoints() < player2.getPoints())
					return LOSS;
				else
					return DRAW;
			}
		} else {
			Player player1 = getPlayer(Settings.PLAYER1);
			if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION) {
				return LOSS;
			} else {
				return VICTORY;
			}
		}
	}

}
