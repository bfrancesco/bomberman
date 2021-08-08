package gioco.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import gioco.utilities.Settings;

public class Gioco {
	public static final int VICTORYPLAYER1 = 0;
	public static final int VICTORYPLAYER2 = 1;
	public static final int DRAW = 2;
	public static final int LOSS = 3;

	private int time = 150;
	private Block[][] matrix;
	private int height = 13;
	private int width = 15;
	private Player player1;
	private Player player2;
	private Vector<Enemy> enemies;
	private Vector<Enemy> enemiesToBeRemoved;
	private Vector<Bomb> bombs;
	private Vector<Explosion> explosions;
	private boolean multiplayer;
	private boolean gameOver;
	private String map;

	public Gioco(boolean multiplayer, String mapName) {
		this.multiplayer = multiplayer;
		enemies = new Vector<Enemy>();
		bombs = new Vector<Bomb>();
		explosions = new Vector<Explosion>();
		enemiesToBeRemoved = new Vector<Enemy>();
		matrix = new Block[height][width];
		gameOver = true;

		try {
			loadMap(mapName);
			map = mapName;
		} catch (IOException e) {
			System.out.println("IL FILE NON ESISTE PER IL PERCORSO SPECIFICATO:" + mapName);
			e.printStackTrace();
		}
	}

	public void inizia() {
		gameOver = false;
	}
	
	public void setTime(int t) {
		time = t;
	}
	
	public int getTime() {
		return time;
	}
	/*
	 * DA CONTROLLARE : Player1 non si sovrappone al 2 multiplayer aggiungere errori
	 * in caso di mancata connessione aggiungere nemici gestione mappa Lettura della
	 * mappa scelta su cui si svilupperà il gioco
	 */
	public void loadMap(String mapName) throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("src/gioco/resources/maps/" + mapName + ".txt"));
			String s = reader.readLine();
			// lettura dei players e dei nemici
			while (!(s.equals("END"))) {
				String[] elements = s.split(" ");
				if (elements[0].equals("player1")) {
					player1 = new Player(Integer.valueOf(elements[1]) * Settings.BLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.BLOCKSIZEY);
				}

				else if (multiplayer && elements[0].equals("player2")) {
					player2 = new Player(Integer.valueOf(elements[1]), Integer.valueOf(elements[2]));
				} else if (elements[0].equals("enemy1")) {
					Enemy1 e = new Enemy1(Integer.valueOf(elements[1]) * Settings.BLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.BLOCKSIZEY);
					enemies.add(e);
				} else if (elements[0].equals("enemy2")) {
					Enemy2 e = new Enemy2(Integer.valueOf(elements[1]) * Settings.BLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.BLOCKSIZEY);
					enemies.add(e);
				} else if (elements[0].equals("enemy3")) {
					Enemy3 e = new Enemy3(Integer.valueOf(elements[1]) * Settings.BLOCKSIZEX,
							Integer.valueOf(elements[2]) * Settings.BLOCKSIZEY);
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
					case 'G':
						matrix[k][i] = new Block(Block.GRASS);
						break;
					case 'I':
						matrix[k][i] = new Block(Block.IRON);
						break;
					case 'B':
						matrix[k][i] = new Block(Block.BRICK);
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
	
	
	public synchronized void removeEnemies() {
		enemies.removeAll(enemiesToBeRemoved);
		enemiesToBeRemoved.clear();
	}

	public synchronized void updateEnemy() {
		if(!gameOver) {
		for (Enemy e : enemies) {
			if (e.getState() != Entity.DYING_EXPLOSION) {
				if (e instanceof Enemy3) {
					Enemy3 e3 = (Enemy3) e;
					e3.changeVisibility();
					if (e3.getUnseenTime() == e3.getRandomVisibilityTime() * 85 / 100 && !e3.isVisible())
						e3.Teleport(player1.xcenterBlock(), player1.ycenterBlock());
				}
				if (collisionBlock(e, e.getDirection()) != Settings.TOTALCOLLISION
						&& !collisionBombs(e, e.getDirection()) && e.getState() != Entity.IDLE_DOWN) {
					e.move();
				} else {
					ArrayList<Integer> directions = new ArrayList<Integer>();
					if ( e.getDirection()!= Settings.DOWN && e.ycenterBlock() + 1 < height
							&& matrix[e.ycenterBlock() + 1][e.xcenterBlock()].isWalkable())
						directions.add(Settings.DOWN);
					if (e.getDirection()!= Settings.LEFT && e.xcenterBlock() - 1 >= 0
							&& matrix[e.ycenterBlock()][e.xcenterBlock() - 1].isWalkable())
						directions.add(Settings.LEFT);
					if (e.getDirection()!= Settings.UP &&e.ycenterBlock() - 1 >= 0
							&& matrix[e.ycenterBlock() - 1][e.xcenterBlock()].isWalkable())
						directions.add(Settings.UP);
					if (e.getDirection()!= Settings.RIGHT &&e.xcenterBlock() + 1 < width
							&& matrix[e.ycenterBlock()][e.xcenterBlock() + 1].isWalkable())
						directions.add(Settings.RIGHT);
					//if (directions.size() > 0)
						e.changeDirection(directions);
				}
			} else {
				if (e.getDyingTime() == 0)
					enemiesToBeRemoved.add(e);
				else
					e.decreasedyingTime();
			}
		}
		}
		else {
			for(Enemy e :  enemies)
				e.setState(Entity.IDLE_DOWN);
		}
	}

	public void movePlayer(Player player, int direction) {
		if (player.getState() == Player.DYING_ENEMY || player.getState() == Player.DYING_EXPLOSION)
			return;
		int collisionType = collisionBlock(player1, direction);
		if (collisionType != Settings.TOTALCOLLISION && !collisionBombs(player1, direction)) {
			if (collisionType == Settings.NOCOLLISION)
				player.move(direction);
			else {
				switch (collisionType) {
				case Settings.DOWNCOLLISION:
					direction = Settings.UP;
					break;
				case Settings.UPCOLLISION:
					direction = Settings.DOWN;
					break;
				case Settings.RIGHTCOLLISION:
					direction = Settings.LEFT;
					break;
				case Settings.LEFTCOLLISION:
					direction = Settings.RIGHT;
					break;
				}
				player.move(direction, player1.getSpeed() * 2 / 3);
			}
		}
	}

	public int collisionBlock(Entity e, int dir) {
		switch (dir) {
		case Settings.LEFT:
			if (e.getX() - e.getSpeed() < 0) {
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.getX() - e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.downBlock()][(e.getX() - e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				if (e.downSide() + e.getSpeed() >= Settings.BLOCKSIZEY * height)
					return Settings.TOTALCOLLISION;
				return Settings.DOWNCOLLISION;
			} else if (!matrix[e.upBlock()][(e.getX() - e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				if (e.getY() - e.getSpeed() < 0)
					return Settings.TOTALCOLLISION;
				return Settings.UPCOLLISION;
			}
			break;
		case Settings.RIGHT:
			if (e.rightSide() + e.getSpeed() >= Settings.BLOCKSIZEX * width) {
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.ycenterBlock()][(e.rightSide() + e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				return Settings.TOTALCOLLISION;
			} else if (!matrix[e.downBlock()][(e.rightSide() + e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				if (e.downSide() + e.getSpeed() >= Settings.BLOCKSIZEY * height)
					return Settings.TOTALCOLLISION;
				return Settings.DOWNCOLLISION;
			} else if (!matrix[e.upBlock()][(e.rightSide() + e.getSpeed()) / Settings.BLOCKSIZEX].isWalkable()) {
				if (e.getY() - e.getSpeed() < 0)
					return Settings.TOTALCOLLISION;
				return Settings.UPCOLLISION;
			}
			break;
		case Settings.UP:
			if (e.getY() - e.getSpeed() < 0
					|| !matrix[(e.getY() - e.getSpeed()) / Settings.BLOCKSIZEY][e.xcenterBlock()].isWalkable())
				return Settings.TOTALCOLLISION;
			else if (!matrix[(e.getY() - e.getSpeed()) / Settings.BLOCKSIZEY][e.rightBlock()].isWalkable()) {
				if (e.rightSide() + e.getSpeed() >= Settings.BLOCKSIZEY * height)
					return Settings.TOTALCOLLISION;
				return Settings.RIGHTCOLLISION;
			} else if (!matrix[(e.getY() - e.getSpeed()) / Settings.BLOCKSIZEY][e.leftBlock()].isWalkable()) {
				if (e.getX() - e.getSpeed() < 0)
					return Settings.TOTALCOLLISION;
				return Settings.LEFTCOLLISION;
			}
			break;
		case Settings.DOWN:
			if (e.downSide() + e.getSpeed() >= Settings.BLOCKSIZEY * height
					|| !matrix[(e.downSide() + e.getSpeed()) / Settings.BLOCKSIZEY][e.xcenterBlock()].isWalkable())
				return Settings.TOTALCOLLISION;
			else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.BLOCKSIZEY][e.rightBlock()].isWalkable()) {
				if (e.rightSide() + e.getSpeed() >= Settings.BLOCKSIZEY *height)
					return Settings.TOTALCOLLISION;
				return Settings.RIGHTCOLLISION;
			} else if (!matrix[(e.downSide() + e.getSpeed()) / Settings.BLOCKSIZEY][e.leftBlock()].isWalkable()) {
				if (e.getX() - e.getSpeed() < 0)
					return Settings.TOTALCOLLISION;
				return Settings.LEFTCOLLISION;
			}
			break;
		}
		return Settings.NOCOLLISION;
	}

	public boolean collisionBombs(Entity e, int dir) {
		switch (dir) {
		case Settings.LEFT:
			for (Bomb elem : bombs) {
				if (elem.getX() == (e.getX() - e.getSpeed()) / Settings.BLOCKSIZEX
						&& (elem.getY() == e.upBlock() || elem.getY() == e.downBlock())
						&& e.leftBlock() != e.xcenterBlock())
					return true;
			}
			break;
		case Settings.RIGHT:
			for (Bomb elem : bombs) {
				if (elem.getX() == (e.rightSide() + e.getSpeed()) / Settings.BLOCKSIZEX
						&& (elem.getY() == e.upBlock() || elem.getY() == e.downBlock())
						&& e.rightBlock() != e.xcenterBlock())
					return true;
			}
			break;
		case Settings.UP:
			for (Bomb elem : bombs) {
				if (elem.getY() == (e.getY() - e.getSpeed()) / Settings.BLOCKSIZEY
						&& (elem.getX() == e.leftBlock() || elem.getX() == e.rightBlock())
						&& e.upBlock() != e.ycenterBlock())
					return true;
			}
			break;
		case Settings.DOWN:
			for (Bomb elem : bombs) {
				if (elem.getY() == (e.downSide() + e.getSpeed()) / Settings.BLOCKSIZEY
						&& (elem.getX() == e.leftBlock() || elem.getX() == e.rightBlock())
						&& e.downBlock() != e.ycenterBlock())
					return true;
			}
			break;
		}
		return false;
	}

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
				if (enemy.getState() != Entity.DYING_EXPLOSION
						&& (elem.getX() == enemy.xcenterBlock() && (elem.getY() == enemy.ycenterBlock())
								|| ((elem.getX() == enemy.rightBlock() || elem.getX() == enemy.leftBlock())
										&& (elem.getY() == enemy.upBlock() || elem.getY() == enemy.downBlock())))) {
					if (!(enemy instanceof Enemy3) || ((Enemy3) enemy).isVisible()) {
						enemy.setState(Entity.DYING_EXPLOSION);
						if (enemy instanceof Enemy1) {
							elem.getPlayer().increasePoints(1000);
						} else if (enemy instanceof Enemy2) {
							elem.getPlayer().increasePoints(1500);
						} else if (enemy instanceof Enemy3) {
							elem.getPlayer().increasePoints(3000);
						}
					}
				}
			}

			if (elem.getX() == player1.xcenterBlock() && (elem.getY() == player1.ycenterBlock())
					|| ((elem.getX() == player1.rightBlock() || elem.getX() == player1.leftBlock())
							&& (elem.getY() == player1.upBlock() || elem.getY() == player1.downBlock()))) {
				playerCollision = true;
				player1.setState(Player.DYING_EXPLOSION);
			}
			if (multiplayer && elem.getX() == player2.xcenterBlock() && elem.getY() == player2.ycenterBlock()) {
				playerCollision = true;
				player2.setState(Player.DYING_EXPLOSION);
			}
		}

		return playerCollision;

	}

	public boolean collisionEnemyPlayer() {
		for (Enemy enemy : enemies) {
			if (enemy.getState() != Entity.DYING_EXPLOSION
					&& ((player1.rightSide() <= enemy.rightSide() && player1.rightSide() >= enemy.getX())
							|| (player1.getX() <= enemy.rightSide() && player1.getX() >= enemy.getX()))
					&& ((player1.downSide() <= enemy.downSide() && player1.downSide() >= enemy.getY())
							|| (player1.getY() <= enemy.downSide() && player1.downSide() >= enemy.getY()))) {
				if (!(enemy instanceof Enemy3) || ((Enemy3) enemy).isVisible()) {
					player1.setState(Player.DYING_ENEMY);
					return true;
				}
			}
		}
		return false;
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
				explosions.add(new Explosion(bombs.get(k).getX(), bombs.get(k).getY(), Explosion.CENTRAL,
						Settings.RIGHT, bombs.get(k).getPlayer()));
				for (int i = 1; i <= bombs.get(k).getPlayer().getRadius(); ++i) {
					// CONTROLLO L'ESPLOSIONE DI SOPRA
					if (i == bombs.get(k).getPlayer().getRadius())
						type = Explosion.LAST;
					if (!stopDown && bombs.get(k).getY() + i < height) {
						if (matrix[bombs.get(k).getY() + i][bombs.get(k).getX()].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getX(), bombs.get(k).getY() + i, type,
									Settings.DOWN, bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							if (matrix[bombs.get(k).getY() + i][bombs.get(k).getX()].isBreakable())
								matrix[bombs.get(k).getY() + i][bombs.get(k).getX()].exploded();
							stopDown = true;
						}
					}

					if (!stopUp && bombs.get(k).getY() - i >= 0) {
						if (matrix[bombs.get(k).getY() - i][bombs.get(k).getX()].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getX(), bombs.get(k).getY() - i, type,
									Settings.UP, bombs.get(k).getPlayer());
							explosions.add(tmp);

						} else {
							if (matrix[bombs.get(k).getY() - i][bombs.get(k).getX()].isBreakable())
								matrix[bombs.get(k).getY() - i][bombs.get(k).getX()].exploded();
							stopUp = true;
						}
					}

					if (!stopRight && bombs.get(k).getX() + i < width) {
						if (matrix[bombs.get(k).getY()][bombs.get(k).getX() + i].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getX() + i, bombs.get(k).getY(), type,
									Settings.RIGHT, bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							if (matrix[bombs.get(k).getY()][bombs.get(k).getX() + i].isBreakable())
								matrix[bombs.get(k).getY()][bombs.get(k).getX() + i].exploded();
							stopRight = true;

						}
					}

					if (!stopLeft && bombs.get(k).getX() - i >= 0) {
						if (matrix[bombs.get(k).getY()][bombs.get(k).getX() - i].isWalkable()) {
							Explosion tmp = new Explosion(bombs.get(k).getX() - i, bombs.get(k).getY(), type,
									Settings.LEFT, bombs.get(k).getPlayer());

							explosions.add(tmp);
						} else {
							stopLeft = true;
							if (matrix[bombs.get(k).getY()][bombs.get(k).getX() - i].isBreakable())
								matrix[bombs.get(k).getY()][bombs.get(k).getX() - i].exploded();
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

	public synchronized Player getPlayer1() {
		return player1;
	}
	
	public Vector<Enemy> getEnemiesToBeRemoved() {
		return enemiesToBeRemoved;
	}

	public Player getPlayer2() {
		return player2;
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

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public Block getElement(int y, int x) {
		if (x > width || y > height || x < 0 || y < 0)
			return null;
		return matrix[y][x];
	}

	public synchronized void addBomb(String player) {
		if (!multiplayer) {
			if (player1.getBombs() > 0) {
				int px = player1.xcenterBlock();
				int py = player1.ycenterBlock();
				/*
				 * if (player1.getState() == Player.WALKING_DOWN) py = player1.upBlock();
				 */
				if (player1.getState() == Player.WALKING_UP)
					py = player1.downBlock();
				/*
				 * else if (player1.getState() == Player.WALKING_RIGHT) px =
				 * player1.leftBlock();
				 */
				else if (player1.getState() == Player.WALKING_LEFT) {
					px = player1.rightBlock();
				}
				Bomb b = new Bomb(px, py, player1.getRadius(), player1);
				if (!bombs.contains(b)) {
					bombs.add(b);
					player1.decreaseBombs();
				}
			}
		}
	}

	public boolean finishLevel() {
		if (enemies.size() == 0) {
			player1.setState(Player.WINNING);
			return true;
		}
		// if (multiplayer && (player1.getState() == Player.DEAD || player2.getState()
		// == Player.DEAD))
		// return true;
		return false;
	}
	
	public void timeOut() {
		for(Enemy e : enemies) {
			e.setState(Entity.IDLE_DOWN);
		}
		player1.setState(Player.DYING_ENEMY);
		gameOver = true;
	}

	public int results() {
		// if (multiplayer) {
		/*
		 * if (player1.getState() == Player.DEAD && player2.getState() != Player.DEAD)
		 * return VICTORYPLAYER2; else if (player1.getState() != Player.DEAD &&
		 * player2.getState() == Player.DEAD) return VICTORYPLAYER1; if
		 * (player1.getPoints() > player2.getPoints()) return VICTORYPLAYER1; else if
		 * (player1.getPoints() < player2.getPoints()) return VICTORYPLAYER2; else
		 * return DRAW; } else {
		 */
		if (player1.getState() == Player.DYING_ENEMY || player1.getState() == Player.DYING_EXPLOSION) {
			return LOSS;
		} else {
			return VICTORYPLAYER1;
		}
		// }
	}

}
