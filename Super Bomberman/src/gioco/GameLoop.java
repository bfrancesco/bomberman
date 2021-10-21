package gioco;

import gioco.controller.PlayerController;
import gioco.model.Gioco;
import gioco.model.Player;
import gioco.net.Client;
import gioco.net.Protocol;
import gioco.sound.SoundsHandler;
import gioco.utilities.Settings;

/*
 * Estende un thread e permette l'esecuzione del gioco
 * 
 * */
public class GameLoop extends Thread {

	private PlayerController controller;
	private boolean running;

	public GameLoop(PlayerController controller) {
		this.controller = controller;
	}

	
	//segnala che il gioco non deve più essere eseguito e il thread ( while (running) nel metodo run ) può uscire dal ciclo e terminare
	public synchronized void setRunning(boolean r) {
		running = r;
	}
	
	//calcola il tempo rimanente e aggiorna le statistiche da visualizare
	public int updateStat(long startTime , int remainingTime) {
		int walltime = ((Long) (System.currentTimeMillis() - startTime)).intValue() / 1000;
		remainingTime = controller.getGioco().getTime() - walltime;
		if(remainingTime<0)
			remainingTime=0;
		controller.getPanel().setStat(remainingTime);
		return remainingTime;
	}
	
	//Esecuzione di una partita:
	// 1) predisposizione del gioco all'inizio di una partita
	// 2) la variabile running regola l'esecuzione, quando è settata a false , l'esecuzione termina.
	// 3) se il gioco è in pausa, aggiorno solo le statistiche
	// 4) altrimenti se il gioco non è nello stato gameOver . calcolo il prossimo passo (oppure ricevo i dati dal server ed elaboro i dati ricevuti) 
	//    se il gioco è appena entrato in gameover, visualizza il  risultato finale. 
	//	  se il  gioco è nello stato di gameover, allora continua a visualizzare e aggiornare il gioco attendendo l'uscita
	//nel caso della battleroyale è possibile osservare la partita anche dopo la morte del giocatore
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
		int remainingTime = controller.getGioco().getTime();
		
		
		if(controller.isMultiplayer()) {
			startTime = Client.getClient().getStartingTime();
			controller.receivePowerUp();
		}
		controller.getGioco().inizia();
		 
		while (running) {
			if (controller.isPaused() && !controller.isMultiplayer()) {
				controller.getGioco().setTime(remainingTime);
				startTime = System.currentTimeMillis();
			} else { 
				//now è il tempo prima del calcolo logico e della render grafica , serve a determinare il tempo di sleep del gameloop.
				//Ovvero a calcolare quanto tempo è trascorso per calcolare e disegnare 
				now = System.nanoTime();
				controller.render();
				if (!controller.getGioco().isGameOver()) {
					if (!controller.isMultiplayer()) {
						controller.getGioco().next();					
					} else {
						controller.getGioco().resetState();					
						controller.readAndUpdate();
						controller.getGioco().updateGameMultiplayer();
						controller.sendInfo();
					}
					remainingTime=updateStat(startTime , remainingTime);
					//Per il multiplayer il timeOut è delegato al server 
					if (remainingTime <= 0 && !controller.isMultiplayer()) {
						controller.getGioco().timeOut();
					}
					//se il gioco è appena entrato in gameover ottengo i risultati e stabilisco il gameOver
					//vengono resettati gli stati e mostrato il risultato cambaindo gli stati
				} else if (!gameOver) {
					gameOver = true;
					controller.getGioco().checkExplosions();
					controller.getGioco().resetState();	
					controller.showResults();
					//il gioco è in gameover
				} else {
					//il gioco in gameover continua a leggere e ad aggiornarsi nel caso della battleRoyale finchè non termina la partita
					if (controller.isBattleRoyale() && controller.getGioco().countsAlive() > 1 ) {
						remainingTime = updateStat(startTime , remainingTime);
						controller.getGioco().resetState();	
						controller.getGioco().updateGameMultiplayer();
						controller.readAndUpdate();
					}
					else {
						//altrimenti se non è battleRoyale vengono sono aggiornati gli stati e si smette di leggere
						controller.getGioco().resetState();	
						controller.getGioco().updateGameMultiplayer();
						if(!controller.isMultiplayer()) {
							controller.getGioco().updateEnemy();
						}
					}
				} 
				//viene stabilito il tempo di sleep in base a quanto tempo  ha impiegato ad elaborare e disegnare
				updateTime = System.nanoTime() - now;
				sleepTime = (maxTime - updateTime) / 1000000;
				try {
					//prova a dormire per il tempo rimanente per rispettare lo sleepTime massimo
					if (sleepTime > 0)
						Thread.sleep(sleepTime);
					else //un rallentamento non fa crashare il gioco, ma lo rallenta temporaneamente
						Thread.sleep(0);
				} catch (InterruptedException e) {
					return;
				}
			}
		}

	}
}
