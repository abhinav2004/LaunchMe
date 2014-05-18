package lmcomponents.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.imgscalr.Scalr;

public class LMImage extends JComponent{

	private	URL imgURL;
	private String path;
	private BufferedImage image;
	private boolean border = true;
	
	public LMImage()
	{
		super();
	}
	
	public LMImage(String pathToImage) throws IOException
	{
		imgURL = new URL("file://"+pathToImage);
		path = pathToImage;
		image = ImageIO.read(imgURL);
	}
	
	public long getFileSize() throws URISyntaxException
	{
		return ((new File(path)).length());
	}
	
	public Dimension getImageSize()
	{
		return new Dimension(image.getWidth(), image.getHeight());
	}
	
	public String getPath()
	{
		return path;
	}
	
	
	public void disableBorder(boolean b)
	{
		border = !b;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		image = Scalr.resize(image, this.getWidth(),  this.getHeight());
		g.drawImage(image, 0,0,null);
		if(border)
		{
			g.setColor(Color.decode("#AAAAAA"));
			g.drawRect(0, 0, image.getWidth()-1,  image.getHeight()-1);
		}
		
		super.paintComponents(g);
	}
}
