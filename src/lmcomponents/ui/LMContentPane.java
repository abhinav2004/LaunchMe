package lmcomponents.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;

public class LMContentPane extends JComponent
{
	public	final	static	int			NORMAL = 0;
	public	final	static 	int			STRETCH = 1;
	public	final	static 	int			FIT = 2;
	
	private 	Image 		image;
	private 	Dimension 	screenSize;
	private 	int			imgX, imgY;
	private     int			FILL_STYLE;
    public LMContentPane(Image image, int type)
    {    	
    
    	FILL_STYLE = type;
    	
    	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.image = image;
        imgX = image.getWidth(null);
        imgY = image.getHeight(null);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    	
    	if(FILL_STYLE == NORMAL)
    		g.drawImage(image, 0, 0, null);
    	else if(FILL_STYLE == STRETCH)
    		g.drawImage(image, -Math.abs((screenSize.width/2)-(imgX/2)), -Math.abs((screenSize.height/2)-(imgY/2)), null);
    	else
    		g.drawImage(image, 0, 0, screenSize.width, screenSize.height, null);
    }
}
