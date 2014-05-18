package lmcomponents.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;

import lmcomponents.deskpanel.ui.IconObject;

/**
 * 
 */

/**
 * @author abhinav
 *
 */
public class LMPane extends JWindow implements MouseListener{

	private 	LinkedList<IconObject> 	iconArray;
	private 	Dimension 				screenSize;
	private		Graphics				g;
	public	 	BufferedImage			backgroundImg;
	private 	LMContentPane			contentPane;
	private 	JPopupMenu				contentMenu;
	
	private 	JMenuItem				changeBackground;
	private 	Font 					mainFont;
	
	public LMPane()
	{			
		mainFont = new Font("URW Gothic L Book", Font.PLAIN, 16);
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(0,0);
		this.setSize(screenSize);
		this.setAlwaysOnTop(false);
			
			
		try {
			backgroundImg = ImageIO.read(new URL("file:///home/abhinav/My Pictures/wallpaper1.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contentPane = new LMContentPane(backgroundImg, LMContentPane.NORMAL);
		this.setContentPane(contentPane);
		
		
	}	
	
	
	@Override
	public void toFront()
	{
		this.toBack();
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	

}
