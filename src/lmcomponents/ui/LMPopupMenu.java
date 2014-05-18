package lmcomponents.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JPopupMenu;

public class LMPopupMenu extends JPopupMenu {
	public LMPopupMenu() {
		super();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(getFont());
		
		int X, Y;
		
		X = this.getWidth();
		Y = this.getHeight();
		
		Rectangle shadowRect = new Rectangle(0, 0, X+100, Y+100);
		
		g.setColor(Color.black);
		g2d.draw(shadowRect);
		
		g2d.fill(shadowRect);
		
		g.setColor(getForeground());
		super.paintComponent(g);
	}
}
