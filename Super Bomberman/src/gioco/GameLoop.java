package gioco;

import gioco.controller.PlayerController;
import gioco.model.Gioco;
import gioco.utilities.Settings;


public class GameLoop extends Thread {

	private PlayerController controller;
	boolean running;

	public GameLoop(PlayerController controller) {
		this.controller = controller;
	}
	

	public void render() {
		controller.getPanel().getGiocoView().getNotStatics().update();
	}
	
	public synchronized void setRunning(boolean r) {
		running = r;
	}

	@Override
	public void run() {
		super.run();
		running = true;
		boolean gameOver = false;
		long now = System.nanoTime();
		long updateTime;
		long sleepTime;
		long maxTime = 1000000000 / 40;
		long startTime = System.currentTimeMillis();
		controller.getPanel().paintMap();
		controller.getGioco().inizia();
		while (running) {
			now = System.nanoTime();
			render();
			if (!controller.getGioco().isGameOver()) { 
				if(!controller.isMultiplayer()) {
					controller.getGioco().next();
					int walltime = ((Long)(System.currentTimeMillis() - startTime)).intValue()/1000;
					controller.getPanel().setStat(controller.getGioco().getTime() - walltime);
					if(controller.getGioco().getTime() - walltime == 0) {
						controller.getGioco().timeOut();
					}
				}
				else {		
					if(!controller.getClient().isConnected()) {
						break ;
					}
					controller.readAndUpdate();	
					controller.getGioco().checkBombs();
					controller.getGioco().checkExplosions();
					controller.sendAction();	
					controller.getPanel().setStat(200);					
				}

			}
			else if (!gameOver) {
				gameOver = true;
				controller.getGioco().checkExplosions();
				if(!controller.isMultiplayer()) {
					if (controller.getGioco().results(Settings.PLAYER1) == Gioco.VICTORY) {
						System.out.println("YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer(Settings.PLAYER1).getPoints() + " !!!");
					} else
						System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
				}
				else {
					int res = controller.getGioco().results(controller.getClient().getOrderConnection());
					if (res== Gioco.VICTORY) {
						System.out.println("YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer(controller.getClient().getOrderConnection()).getPoints() + " !!!");
					}
					else if (res == Gioco.DRAW)
						System.out.println("DRAW! LUCK WAS BY ITS SIDE, WASN'T IT?");
					else
					System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
				}
			} 
			else {
				controller.getGioco().checkExplosions();
				if(controller.isMultiplayer()) { 
					controller.getGioco().checkBombs();
					controller.readAndUpdate();
				}
				else
					controller.getGioco().updateEnemy();
			}

			updateTime = System.nanoTime() - now;
			sleepTime = (maxTime - updateTime) / 1000000;
			try {
				if(sleepTime>0)
					Thread.sleep(sleepTime);
				else Thread.sleep(0);
			} catch (InterruptedException e) {
				return;
			}
		}

	}
}
