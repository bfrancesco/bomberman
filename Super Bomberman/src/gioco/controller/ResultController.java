package gioco.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import gioco.WindowsHandler;
import gioco.gui.ResultsPanel;
import gioco.model.Gioco;
import gioco.utilities.Settings;

//Controlla il result pane e legge il gioco
//quando il result pane notifica al controller una scelta, esso imposta la schermata principale sulla base del risultato
//Conseguentemente tipo di risultato e di modalità di gioco permette di :
//1) SinglePlayer : tornare al menu di partita personalizzata con ESC oppure caricare la mappa successiva con ENTER
//2) Multiplayer e BattleRoyale : Tornare al menu principale con ESC oppure cercare una nuova partita con ENTER
//Per legere il contenuto necessita del gioco per leggere i dati (risultato , punteggio )
public class ResultController extends KeyAdapter{
	private Gioco gioco;
	private ResultsPanel resultPanel;
	public ResultController(Gioco gioco , ResultsPanel rp) {
		this.gioco = gioco;
		this.resultPanel = rp;	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_ESCAPE:
			WindowsHandler.getWindowsHandler().interruptGame();
			WindowsHandler.getWindowsHandler().showGlassPane(false);
			if(!gioco.isMultiplayer())
				WindowsHandler.getWindowsHandler().setMapChooser();
			else WindowsHandler.getWindowsHandler().setMenu();
			break;
		case KeyEvent.VK_ENTER:
			WindowsHandler.getWindowsHandler().interruptGame();
			WindowsHandler.getWindowsHandler().showGlassPane(false);
			int map = gioco.getMap();
			if (map >= Settings.MAPSNUMBER) {
				WindowsHandler.getWindowsHandler().setMapChooser();
			} else {
				if(gioco.getResult() == Gioco.VICTORY)
					Settings.selectedMap = (map + 1);
				if(gioco.isMultiplayer())
					WindowsHandler.getWindowsHandler().setConnectingView(gioco.isBattleRoyale());
				else 
					WindowsHandler.getWindowsHandler().setGamePanel(false, false);
			}
			break;
		}
		super.keyReleased(e);
	}

	public Gioco getGioco() {
		return gioco;
	}

	public ResultsPanel getResultPanel() {
		return resultPanel;
	}
	
	

}
