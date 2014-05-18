package lmcomponents.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JToggleButton;

public class LMToggleButton extends JToggleButton {
	public LMToggleButton(String text) {
		super(text);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(getFont());
		
		g.setColor(getForeground());
		super.paintComponent(g);
	}
}
