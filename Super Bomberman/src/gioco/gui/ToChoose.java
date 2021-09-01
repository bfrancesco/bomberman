package gioco.gui;

import java.awt.Color;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

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
		} else {
			redBorder = BorderFactory.createLineBorder(Color.RED,6);
			normalBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);
		}
		setChoosen(choosen);
	}

	public void setHighlighted(boolean val) {
		if(!choosen) {
			if(val)
				this.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.WHITE, Color.WHITE),title));
			else this.setBorder(normalBorder);
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
