package gioco;

import gioco.controller.PlayerController;
import gioco.gui.GamePanel;
import gioco.model.Enemy;
import gioco.model.Gioco;
import gioco.model.Player;

public class GameLoop extends Thread {

	private PlayerController controller;

	public GameLoop(PlayerController controller) {
		this.controller = controller;
	}
	
	public void moveplayers() {
		if(!controller.getGioco().isMultiplayer()) {
			switch(controller.getGioco().getPlayer1().getState()) {
			case Player.WALKING_LEFT : 
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1() ,Settings.LEFT);
				break;
			
			case Player.WALKING_RIGHT : 
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1() ,Settings.RIGHT);
				break;
			
			case Player.WALKING_UP : 
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1() ,Settings.UP);
				break;
			
			case Player.WALKING_DOWN : 
				controller.getGioco().movePlayer(controller.getGioco().getPlayer1() ,Settings.DOWN);
				break;
			case Player.DEAD : 
				break;
			default:
				break;
			}
		}	
	}
	
	public void moveEnemies() {
		for(Enemy e : controller.getGioco().getEnemies()) {
			controller.getGioco().moveEnemy(e);	
		}
	}
		@Override
	public void run() {
		super.run();
		Thread t = new Thread(controller.getPanel());
		t.start();
		controller.getGioco().inizia();
		while (!controller.getGioco().isGameOver()) {
			controller.getGioco().checkBombs();
			controller.getGioco().checkExplosions();
			
			if(controller.getGioco().collisionExposion() || controller.getGioco().collisionEnemyPlayer() || controller.getGioco().finishLevel())
				controller.getGioco().setGameOver(true);
			moveEnemies();
			moveplayers();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				return;
			}
		}
		controller.getPanel().getGiocoView().getNotStatics().update();
		int result = controller.getGioco().results();
		if(result == Gioco.VICTORYPLAYER1) {
			System.out.println("YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer1().getPoints() + " !!!");
		}
		else System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
		
	}
}
