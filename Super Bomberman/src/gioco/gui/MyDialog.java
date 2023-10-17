package gioco.gui;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import gioco.controller.CustomButtonListener;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*Estende la classe JDialog , permette di mostrare dei messaggi di conferma all'utente
 * Ha due opzione YES_OPTION e NO_OPTION che possono essere sostituiti 
 * I due pulsanti di conferma sono due CustomButton con relativo controller che dopo aver settato data con il valore corrispondente, ordinano la dispose della finestra
 * */
public class MyDialog extends JDialog {
   public static final int NO_OPTION = 0;
   public static final int YES_OPTION = 1;
	//per posizionarla all'interno del frame principale
   private Point p;
   private JLabel label;
   private int data;
   private CustomButton confirm;
   private CustomButton cancel;
   public MyDialog(Frame g , String title , String content , Point p) {	  
	   //la variabile true è fondamentale e blocca le altre finestre , impossibilita l'utente dal poter accedere all'altra finestra , finchè non ha chiuso la finestra
      super(g ,title,true);
      this.p=p;
      this.setIconImage(Resources.iconWindow);
      JPanel panel = new JPanel(new BorderLayout()) {
    	  @Override
    	protected void paintComponent(Graphics g) {
    		super.paintComponent(g);
    	}
      };
      this.setResizable(false);
      panel.setBackground(Color.orange);
      JPanel center = new JPanel();
      JPanel bottom = new JPanel();
      center.setOpaque(false);
      bottom.setOpaque(false);
      data = NO_OPTION;
      cancel = new CustomButton(Resources.annulla , CustomButton.STANDARD);
      cancel.setPreferredSize(new Dimension(Settings.wCustomButtonSelected,Settings.hCustomButtonSelected));
      confirm= new CustomButton(Resources.conferma, CustomButton.STANDARD);
      confirm.setPreferredSize(new Dimension(Settings.wCustomButtonSelected,Settings.hCustomButtonSelected));
      cancel.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			data = NO_OPTION;
			dispose();
		}
	});
      cancel.addMouseListener(new CustomButtonListener(cancel));
      confirm.addMouseListener(new CustomButtonListener(confirm));
      confirm.addActionListener(new ActionListener() {
  		@Override
  		public void actionPerformed(ActionEvent e) {
  			data = YES_OPTION;
  			dispose();
  		}
  	});
      bottom.add(cancel);
      bottom.add(confirm);
      label  = new  JLabel(new  ImageIcon(Resources.iconWindow.getScaledInstance(Settings.iconButtonWidth, Settings.iconButtonHeight ,Image.SCALE_SMOOTH)));
      label.setAlignmentX(LEFT_ALIGNMENT);
      label.setFont(Resources.myFont.deriveFont(30f));
      label.setForeground(Color.black);
      label.setIconTextGap(Settings.iconWidth/2);
      label.setText(content);
      center.add(label);
      panel.add(center,BorderLayout.CENTER);
      panel.add(bottom , BorderLayout.SOUTH);
      getContentPane().add(panel);
      pack();
      
   }
   //posiziona nel centro della finestra principale il myDialog e lo rende visibile
   //restituisce data 
   public int showAndWait() {
	  this.setLocation(new Point(p.x-getWidth()/2 , p.y-getHeight()/2));
      this.setVisible(true);
      return data;
   }
   
   //cambia l'icona della label (icona centrale)
   public void setIcon(Image image){
	   if(image != null)
	   label.setIcon(new ImageIcon(image.getScaledInstance(Settings.iconWidth, Settings.iconHeight ,Image.SCALE_SMOOTH)));	   
   }

}