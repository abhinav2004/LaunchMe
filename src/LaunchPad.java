import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.print.attribute.standard.Chromaticity;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;

import lmcomponents.deskpanel.systemsettings.SystemSettings;
import lmcomponents.deskpanel.ui.IconObject;
import lmcomponents.ui.LMMenuItem;
import lmcomponents.ui.LMPane;
import lmcomponents.ui.LMPopupMenu;


import net.miginfocom.swing.MigLayout;



public class LaunchPad implements WindowStateListener, WindowFocusListener, MouseListener, ActionListener {
	
	private 	LMPane 		lmp;
	private		Font		mainFont;
	private		String		iconSizeRestriction;
	
	private 	IconObject 	chromium;
	private 	IconObject 	firefox;
	private 	LMPopupMenu contentMenu;
	private 	LMMenuItem 	contentMenuItems[];
	public	static 	Font 		menuFont;
	
	private 	Process		process;
	
	private void run() throws IOException
	{
		iconSizeRestriction = "w 96!, h 96!, ";
		
		mainFont = new Font("Droid Sans", Font.PLAIN, 16);
		menuFont = new Font("Bitstream Vera Sans", Font.PLAIN, 14);
		
		
		lmp = new LMPane();
		
		chromium = new IconObject("Chromium", "chromium", "chromium", "An Open Source Webkit web browser.", 1, lmp);
		chromium.setFont(mainFont);
		chromium.setForeground(Color.white);
		chromium.setLeftShadow(0, 1, Color.decode("#555555"));
	    chromium.setRightShadow(0, 1, Color.decode("#555555"));
		
	    firefox = new IconObject("Firefox", "firefox", "firefox", "An Open Source Gecko web browser.", 1, lmp);
		firefox.setFont(mainFont);
		firefox.setForeground(Color.white);
		firefox.setLeftShadow(0, 1, Color.decode("#333333"));
		firefox.setRightShadow(0, 1, Color.decode("#333333"));
	    
		lmp.getContentPane().setLayout(new MigLayout());
		lmp.getContentPane().add(chromium, iconSizeRestriction+" cell 0 0");
		lmp.getContentPane().add(firefox, iconSizeRestriction+" cell 0 1");
		
		this.configureRightClickMenu();
		
		lmp.toBack();
		lmp.addWindowStateListener(this);
		lmp.addWindowFocusListener(this);
		lmp.addMouseListener(this);
		lmp.setFocusable(false);
		lmp.setFocusableWindowState(false);
		
		lmp.setVisible(true);
		
		
		
	}
	
	private void configureRightClickMenu()
	{
		contentMenu = new LMPopupMenu();
		contentMenuItems = new LMMenuItem[5];
				contentMenuItems[0] = new LMMenuItem("Run command");
					contentMenuItems[0].setFont(menuFont);
				contentMenuItems[1] = new LMMenuItem("Open Terminal");
					contentMenuItems[1].setFont(menuFont);
				contentMenuItems[2] = new LMMenuItem("Open File manager");
					contentMenuItems[2].setFont(menuFont);
				contentMenuItems[3] = new LMMenuItem("System Settings");
					contentMenuItems[3].setFont(menuFont);
				contentMenuItems[4] = new LMMenuItem("Change wallpaper");
					contentMenuItems[4].setFont(menuFont);
		contentMenu.add(contentMenuItems[0]);
		contentMenu.add(new JSeparator());
		contentMenu.add(contentMenuItems[1]);
		contentMenu.add(contentMenuItems[2]);
		contentMenu.add(new JSeparator());
		contentMenu.add(contentMenuItems[3]);
		contentMenu.add(contentMenuItems[4]);
		
		for(int i = 0; i < 5; i++)
			contentMenuItems[i].addActionListener(this);
	}
	
	public static void main(String args[]) throws IOException
	{
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
			com.jgoodies.looks.Options.setPopupDropShadowEnabled(true);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LaunchPad instance = new LaunchPad();
		instance.run();
	}
	
	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub
		lmp.toBack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//chromium.selected = false;
		//firefox.selected = false;
		this.lmp.repaint();
		// TODO Auto-generated method stub
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			contentMenu.show(e.getComponent(), e.getX(), e.getY());		
		}
		else
		{
			lmp.toBack();
		}		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		lmp.toBack();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		lmp.toBack();
	}

	@Override
	public void windowGainedFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		lmp.toBack();
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// TODO Auto-generated method stub
		lmp.toBack();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == contentMenuItems[0])
		{
			try {
				process = Runtime.getRuntime().exec("gmrun");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == contentMenuItems[1])
		{
			try {
				process = Runtime.getRuntime().exec("xterm");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getSource() == contentMenuItems[4])
		{
			try {
				(new SystemSettings()).setVisible(true);
			} catch (IOException | URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		
	}

	
}
