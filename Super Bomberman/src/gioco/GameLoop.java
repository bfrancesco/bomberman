package gioco;

import gioco.controller.PlayerController;
import gioco.gui.GamePanel;
import gioco.model.Enemy;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.utilities.Settings;

public class GameLoop extends Thread {

	private PlayerController controller;

	public GameLoop(PlayerController controller) {
		this.controller = controller;
	}

	public void moveplayers() {
		if (!controller.getGioco().isMultiplayer()) {
			switch (controller.getGioco().getPlayer1().getState()) {
			case Player.WALKING_LEFT:
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1(), Settings.LEFT);
				break;

			case Player.WALKING_RIGHT:
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1(), Settings.RIGHT);
				break;

			case Player.WALKING_UP:
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1(), Settings.UP);
				break;

			case Player.WALKING_DOWN:
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1(), Settings.DOWN);
				break;
			default:
				break;

			}
		}
	}


	public void next() {
		controller.getGioco().checkBombs();
		controller.getGioco().checkExplosions();
		controller.getGioco().removeEnemies();
		if (controller.getGioco().collisionExplosion() || controller.getGioco().collisionEnemyPlayer()
				|| controller.getGioco().finishLevel())
			controller.getGioco().setGameOver(true);
		controller.getGioco().updateEnemy();
		moveplayers();
	}

	public void render() {
		controller.getPanel().getGiocoView().getNotStatics().update();
	}

	@Override
	public void run() {
		super.run();
		boolean gameOver = false;
		long now = System.nanoTime();
		long updateTime;
		long sleepTime;
		long maxTime = 1000000000 / 40;
		controller.getPanel().paintMap();
		controller.getGioco().inizia();
		// Thread t = new Thread(controller.getPanel());
		while (this.isAlive()) {
			now = System.nanoTime();
			render();
			if (!controller.getGioco().isGameOver())
				next();
			else if (!gameOver) {
				gameOver = true;
				controller.getGioco().checkExplosions();
				if (controller.getGioco().results() == Gioco.VICTORYPLAYER1) {
					System.out.println(
							"YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer1().getPoints() + " !!!");
				} else
					System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
			}
			else
				controller.getGioco().checkExplosions();

			updateTime = System.nanoTime() - now;
			sleepTime = (maxTime - updateTime) / 1000000;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				return;
			}
		}

	}
}
