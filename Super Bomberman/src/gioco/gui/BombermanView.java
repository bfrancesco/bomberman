package gioco.gui;

import java.awt.Image;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import gioco.model.Player;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

// Permette di visualizzare gli stati di un player 
// Ogni Player è associato a un BombermanView che ha un determinato colore e uno stato corrente e un indice
// Se lo stato rimane lo stesso , allora l'indice potrebbe incrementare per mostrare tutta l'animazione associata allo stato
//se lo stato cambia , allora si azzera l'indice e si ricomincia con la nuova animazione
// l'introduzione di parametri permette di decidere la velocità dell'animazione durante il metodo get
//Ovvero ad ogni stato-indice è associato una immagine del corrispettivo vettore 
public class BombermanView {

	private Image currentImage;
	private int currentState;
	private int index;
	private int color;
	
	public BombermanView(int color) {

		currentState = Player.IDLE_DOWN;
		this.color = color;
		
	}
	
	public void setState(int state) {
		this.currentState = state;			
	}
	
	
	
	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Image getCurrentImage() {
		return currentImage;
	}


	public int getState() {
		return currentState;
	}
	
	//prende l'immagine corretta in base all'indice e al colore tramite il metodo di Resources adatto, se lo stato è uguale al precedente , incrementa l'indice, altrimenti lo pone a 0
	public void update(int state) {
		switch (state) {
			case Player.IDLE_DOWN :
				index = 0;
				currentImage = Resources.downBomberman(color).get(index);
				currentState = state;
				break;
			case Player.IDLE_UP :
				index = 0;
				currentImage = Resources.upBomberman(color).get(index);
				currentState = state;
				break;
			case Player.IDLE_RIGHT :
				index = 0;
				currentImage =Resources.rightBomberman(color).get(index);
				currentState = state;
				break;
			case Player.IDLE_LEFT :
				index = 0;
				currentImage = Resources.leftBomberman(color).get(index);
				currentState = state;
				break;
			case Player.WALKING_DOWN :
				if(state == currentState)
					index =++index % (Resources.downBomberman(color).size()*3);
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.downBomberman(color).get(index/3);
				break;
			case Player.WALKING_UP :
				if(state == currentState) 
					index = ++index % (Resources.upBomberman(color).size()*3);						
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.upBomberman(color).get(index/3); 
				break;
			case Player.WALKING_RIGHT :
				if(state == currentState) 
					index = ++index % (Resources.rightBomberman(color).size()*3);
				
				else 
					index = 0;
				currentState = state;
				currentImage = Resources.rightBomberman(color).get(index/3); 
				break;
			case Player.WALKING_LEFT :
				if(state == currentState) 
					index = ++index % (Resources.leftBomberman(color).size()*3);
				else 
				index = 0;
				currentState = state;
				currentImage = Resources.leftBomberman(color).get(index/3);
				break;
			case Player.DYING_ENEMY : 
				if(state == currentState) {
					if(index!=(Resources.dyingByEnemyBomberman(color).size()-1)*3)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingByEnemyBomberman(color).get(index/3);
				break;
			case Player.DYING_EXPLOSION : 
				if(state == currentState) {
					if(index!=(Resources.dyingExplosionBomberman(color).size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.dyingExplosionBomberman(color).get(index/5);
				break;
			case Player.WINNING: 
				if(state == currentState) {
					if(index!=(Resources.victoryBomberman(color).size()-1)*5)
						++index;
				}
				else {
					index = 0;
					currentState = state;
				}
				currentImage = Resources.victoryBomberman(color).get(index/5);
				break;
		}
	}
}
