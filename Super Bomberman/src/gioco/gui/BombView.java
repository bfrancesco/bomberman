package gioco.gui;

import java.awt.Image;
import gioco.utilities.Resources;

//permettere di ottenere le immagini della bomba sulla base della durata(tempo in numero di passi del gioco di esistenza della bomba) 
// la velocità di animazione è determinata dai parametri
public class BombView {
	
	public BombView() {
	}
	public Image get( int dur) {
		return Resources.bombSteps.get(dur /6 % Resources.bombSteps.size());
	}
}
