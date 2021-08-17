package gioco;

import gioco.controller.PlayerController;
import gioco.model.Gioco;


public class GameLoop extends Thread {

	private PlayerController controller;

	public GameLoop(PlayerController controller) {
		this.controller = controller;
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
		long startTime = System.currentTimeMillis();
		controller.getPanel().paintMap();
		controller.getGioco().inizia();
		while (this.isAlive()) {
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
					controller.readAndUpdate();	
					controller.sendAction();	
					controller.getPanel().setStat(200);
					if(!controller.getClient().isConnected()) {
						break ;
					}
				}

			}
			else if (!gameOver) {
				gameOver = true;
				controller.getGioco().checkExplosions();
				if(!controller.isMultiplayer()) {
					if (controller.getGioco().results() == Gioco.VICTORY) {
						System.out.println("YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer1().getPoints() + " !!!");
					} else
						System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
				}
				else {
					int res = controller.getGioco().results();
					if (res== Gioco.VICTORY) {
						System.out.println("YOU WIN!!! TOTAL POINTS: " + controller.getGioco().getPlayer1().getPoints() + " !!!");
					}
					else if (res == Gioco.DRAW)
						System.out.println("DRAW! LUCK WAS BY ITS SIDE, WASN'T IT?");
					else
					System.out.println("OH NO! YOU LOSE!!! ARE YOU BRAVE ENOUGH TO TRY AGAIN?");
				}
			}
			else
				controller.getGioco().checkExplosions();

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
