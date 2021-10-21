package gioco.sound;

import java.util.Vector;

import gioco.utilities.Resources;
/*Gestisce i suoni dell'applicazione , permettendo tramite gli appositi metodi di cambiare il volume , 
 * fa partire il corrispettivo suono e lo arresta di conseguenza
 * */
public class SoundsHandler {
	private Sound gameSoundtrack;
	private Sound menuSoundtrack;
	private Sound steps;
	private Sound explosion;
	private Sound placeBomb;
	private Sound pause;
	private Sound bombermanDeath;
	private Sound win;
	private Sound draw;
	private Sound loss;
	private Sound click;
	private Sound powerUp;
	private Sound dyingEnemy;
	private Sound menuButton;
	private Sound standardButton;
	private Sound bombButton;
	private Vector<Sound> allEffects;
	private Vector<Sound> allMusics;
	
	private float musicVolume;
	private float effectsVolume;
	private boolean effectMute;
	private boolean musicMute;

	private static SoundsHandler soundsHandler;

	public static SoundsHandler getSoundsHandler() {
		if (soundsHandler == null)
			soundsHandler = new SoundsHandler();
		return soundsHandler;

	}
	//inizializza i vari suoni
	private SoundsHandler() {
		allEffects = new Vector<Sound>();
		allMusics = new Vector<Sound>();
		gameSoundtrack = new Sound(Resources.gameSoundtrack);
		menuSoundtrack = new Sound(Resources.menuSoundtrack);
		allMusics.add(gameSoundtrack);
		allMusics.add(menuSoundtrack);
		explosion = new Sound(Resources.explosion);
		allEffects.add(explosion);
		steps = new Sound(Resources.steps);
		allEffects.add(steps);
		pause = new Sound(Resources.pause);
		allEffects.add(pause);
		click = new Sound(Resources.clicked);
		allEffects.add(click);
		placeBomb = new Sound(Resources.placeBomb);
		allEffects.add(placeBomb);
		powerUp = new Sound(Resources.getPowerUp);
		allEffects.add(powerUp);
		dyingEnemy = new Sound(Resources.dyingEnemySound);
		allEffects.add(dyingEnemy);
		menuButton = new Sound(Resources.enteredMenuButton);
		allEffects.add(menuButton);
		standardButton = new Sound(Resources.enteredStandardButton);
		allEffects.add(standardButton);
		bombermanDeath = new Sound(Resources.playerDeathSound);
		allEffects.add(bombermanDeath);
		win = new Sound(Resources.victorySound);
		allEffects.add(win);
		loss = new Sound(Resources.lossSounds);
		allEffects.add(loss);
		draw = new Sound(Resources.drawSound);
		allEffects.add(draw);
		bombButton = new Sound(Resources.bombSelection);
		allEffects.add(bombButton);
		effectMute = false;
		musicMute = false;
		changeEffectsVolume(70);
		changeMusicVolume(60);
	}
	//modifica il volume degli effetti
	public void changeEffectsVolume(int volume) {
		effectsVolume = volume;
		for(Sound sound: allEffects) {
			sound.setVolume(effectsVolume);
		}
	}
	//fa partire l'audio di eliminazione di un nemico
	public void dyingEnemy() {
		if (!effectMute) {
			dyingEnemy.restart();
		}
	}

	//fa partire l'audio di sconfitta
	public void loss() {
		if (!effectMute) {
			loss.restart();
		}
	}
	//audio di vittoria
	public void victory() {
		if (!effectMute) {
			win.restart();
		}
	}
	//audio del pareggio
	public void draw() {
		if (!effectMute) {
			draw.restart();
		}
	}

	public boolean isEffectMute() {
		return effectMute;
	}
	//audio morte bomberman
	public void bombermanDeath() {
		if (!effectMute)
			bombermanDeath.restart();
	}
	//reset della musica della partita
	public void resetGameSoundtrack() {
		gameSoundtrack.reset();
	}

	public float getMusicVolume() {
		return musicVolume;
	}
	
	//audio del CustomButton tipo MENU  
	public void enteredMenuButton() {
		if (!effectMute) {
			menuButton.restart();
		}
	}

	public void exitedMenuButton() {
		menuButton.stop();
	}
	
	//audio di un CustomButton Standard
	public void enteredStandardButton() {
		if (!effectMute) {
			standardButton.restart();
		}
	}

	public void exitedStandardButton() {
		standardButton.stop();
	}

	public float getEffectsVolume() {
		return effectsVolume;
	}

	public boolean isMusicMute() {
		return musicMute;
	}

	public synchronized void changeMusicVolume(int volume) {
		musicVolume = volume;
		applyMusicVolume(volume);
	}

	public void changeMusicVolume(float volume) {
		musicVolume = volume;
		for(Sound sound : allMusics) {
			sound.setVolume(volume);
		}
	}
	//audio posizionamento bomba
	public void placeBomb() {
		if (!effectMute)
			placeBomb.restart();
	}
	//audio musica del menu
	public void setMenuSoundtrack() {
		if (!menuSoundtrack.isRunning()) {
			for(Sound sound : allMusics) {
				sound.stop();
			}
			menuSoundtrack.restart();
			menuSoundtrack.loop();
		}
	}
	//imposta la musica della partita
	public void setGameSoundtrack() {
		if (!gameSoundtrack.isRunning()) {
			for(Sound sound : allMusics) {
				sound.stop();
			}
			gameSoundtrack.restart();
			gameSoundtrack.loop();
		}
	}
	//audio powerup
	public void powerUp() {
		if (!effectMute)
			powerUp.restart();
	}

	public void changeEffectMute() {
		effectMute = !effectMute;
	}
	//cambia il volume dei suoni
	public void changeMusicMute() {
		if (musicMute) {
			musicMute = false;
			changeMusicVolume(musicVolume);
		} else {
			musicMute = true;
			applyMusicVolume(0f);
		}
	}

	public Sound getGameSoundtrack() {
		return gameSoundtrack;
	}

	public void setGameSoundtrack(Sound gameSoundtrack) {
		this.gameSoundtrack = gameSoundtrack;
	}

	public Sound getMenuSoundtrack() {
		return menuSoundtrack;
	}

	public void setMenuSoundtrack(Sound menuSoundtrack) {
		this.menuSoundtrack = menuSoundtrack;
	}
	
	private void applyMusicVolume(float volume) {
		for(Sound sound : allMusics) {
			sound.setVolume(volume);
		}
	}
	// blocca i suoni
	public void noSoundtrack() {
		explosion.stop();
		for(Sound sound : allMusics) {
			sound.stop();
		}
	}
	//fa ripartire la musica di gioco
	public synchronized void resumeGameSoundtrack() {
		gameSoundtrack.start();
		gameSoundtrack.loop();
	}
	//inizio passi
	public void startSteps() {
		if (!effectMute)
			steps.loop();
	}
	//suono del click , applicato ai bottoni del menu
	public void clickSound() {
		if (!effectMute)
			click.restart();
	}
	//suono del menu di pausa
	public void pauseMenu() {
		if (!effectMute)
			pause.restart();
	}
	//fa terminare l'audio dei passi
	public void stopSteps() {
		steps.stop();
	}
	

	public void enteredBombButton() {
		if (!effectMute)
			bombButton.restart();
	}

	public void exitedBombButton() {
		bombButton.stop();
	}
	//audio dell'esplosione
	public void explosion() {
		if (!effectMute) {
			explosion.stop();
			explosion.restart();
		}
	}

}
