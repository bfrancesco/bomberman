package gioco.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.BevelBorder;

import com.sun.source.tree.LabeledStatementTree;

import gioco.controller.ToChooseController;
import gioco.utilities.Resources;
import gioco.utilities.Settings;

public class ChooserView extends JPanel {
	private JScrollPane scrollPane;
	private JPanel choosePanel;
	private JSplitPane splitPane;
	private JLabel choosen;
	private ArrayList<ToChoose> toChoose;
	
	public ChooserView(Vector<Image> images , int dimImage) {
		JPanel completePanel = new JPanel();
		
		choosen = new JLabel(new ImageIcon(images.get(0).getScaledInstance(250, 250, Image.SCALE_SMOOTH)));
		choosen.setAlignmentY(TOP_ALIGNMENT);
		choosen.setAlignmentX(CENTER_ALIGNMENT);
		choosen.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED , Color.DARK_GRAY ,  Color.LIGHT_GRAY) , "Selezionato" ));
		choosen.setOpaque(false);
		completePanel.setPreferredSize(new Dimension( Settings.WINDOWWIDTH, Settings.WINDOWHEIGHT*5/8));
		completePanel.setLayout(new BorderLayout());
		completePanel.setOpaque(false);
		toChoose = new ArrayList<ToChoose>();
		choosePanel = new JPanel();
		choosePanel.setOpaque(false);

		WrapLayout layoutMaps  = new WrapLayout();
		layoutMaps.setAlignment(FlowLayout.CENTER);
		choosePanel.setLayout(layoutMaps);
		JViewport  viewPort = new JViewport();
		viewPort.setView(choosePanel);
		for(int i = 0 ; i<Resources.maps.size();++i) {
			ToChoose label = new ToChoose(i+1);
			label.setIcon(new ImageIcon(images.get(i).getScaledInstance(dimImage, dimImage, Image.SCALE_SMOOTH)));
			label.setOpaque(false);
			label.addMouseListener(new ToChooseController(label, toChoose, choosen ));
			toChoose.add(label);
			choosePanel.add(label);
			
		}
		toChoose.get(0).setChoosen(true);
		viewPort.setOpaque(false);
		scrollPane = new JScrollPane(viewPort);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
	
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.LIGHT_GRAY),"Campi di battaglia" ));
		//completePanel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplit.setBorder(BorderFactory.createEmptyBorder());
		horizontalSplit.setDividerSize(0);
		horizontalSplit.setOpaque(false);
		horizontalSplit.setLeftComponent(choosen);
		horizontalSplit.setRightComponent(scrollPane);
		horizontalSplit.setDividerLocation(Settings.WINDOWWIDTH/2);
		completePanel.add(horizontalSplit,BorderLayout.CENTER);
		this.setOpaque(false);
		this.add(completePanel);
		
		
	}
}
