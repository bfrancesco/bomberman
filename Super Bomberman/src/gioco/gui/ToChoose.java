package gioco.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class ToChoose extends JLabel {
	private static final long serialVersionUID = -8082942945747198431L;
	private boolean choosen;
	private int index;
	private Border redBorder;
	private Border normalBorder ;
	public ToChoose(int i) {
		choosen = false;
		this.index = i;
		redBorder =  BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED,6),"Mappa N. " + (index) );
		normalBorder = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY),"Mappa N. " + (index) );
		this.setBorder(normalBorder);
	}

	public boolean isChoosen() {
		return choosen;
	}
	
	

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setChoosen(boolean choosen) {
		this.choosen = choosen;
		if(!choosen) {
			this.setBorder(normalBorder);
		}
		else {
			this.setBorder(redBorder);
		}
	}
	
	
}
