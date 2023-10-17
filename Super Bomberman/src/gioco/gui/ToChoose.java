package gioco.gui;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import gioco.utilities.Resources;

//Bottone che permette di modificare la visualizzazione del bordo , contiene all'interno un'immagine
// può avere o non avere un titolo reso visibile dall'apposito metodo setTitleVIsibile
// ha un controlle che regola come deve cambiare la visualizzazione in base agli eventi notificati
public class ToChoose extends JButton {
	private static final long serialVersionUID = -8082942945747198431L;
	private boolean choosen;
	private Border redBorder;
	private Border normalBorder ;
	private String title;
	private MouseListener ml;
	
	
	public ToChoose( ) {
		title = "";
		choosen = false;
		setTitleVisibile(true);
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorder(normalBorder);
	}

	public boolean isChoosen() {
		return choosen;
	}
	
	public void setController(MouseListener ml) {
		this.ml = ml;
		addMouseListener(ml);
	}
	
	

	public MouseListener getController() {
		return ml;
	}

	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTitleVisibile(boolean visibility) {
		if(visibility) {
			redBorder =  BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED,6),title );
			normalBorder = BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.BLACK),title );
			TitledBorder tmpNormal = (TitledBorder) normalBorder;
			tmpNormal.setTitleFont(Resources.myFont);
			tmpNormal.setTitleColor(Color.black);
			TitledBorder tmpRed = (TitledBorder) redBorder;
			tmpRed.setTitleFont(Resources.myFont);
			tmpRed.setTitleColor(Color.black);
		} else {
			redBorder = BorderFactory.createLineBorder(Color.RED,6);
			normalBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);
		}
		setChoosen(choosen);
	}

	public void setHighlighted(boolean val) {
		if(!choosen) {
			if(val) {
				TitledBorder tmp = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE),title);
				tmp.setTitleFont(Resources.myFont);
				tmp.setTitleColor(Color.white);
				this.setBorder(tmp);
			
			} else this.setBorder(normalBorder);
		}
			
	}
	
	public void setChoosen(boolean choosen) {
		this.choosen = choosen;
		if(!choosen) {
			this.setBorder(normalBorder);
			//this.setEnabled(true);
		}
		else {
			this.setBorder(redBorder);
			//this.setEnabled(false);
		}
	}
	
	
}
