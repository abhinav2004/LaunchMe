package lmcomponents.deskpanel.systemsettings.wallpapersettings.ui;

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
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import org.imgscalr.Scalr;

public class LMThumbnailImage extends JLabel{

	private	URL imgURL;
	private String path;
	private BufferedImage image;
	
	public LMThumbnailImage()
	{
		super();
	}
	
	public LMThumbnailImage(BufferedImage image) throws IOException
	{		
		super(new ImageIcon(image));
	}
}
