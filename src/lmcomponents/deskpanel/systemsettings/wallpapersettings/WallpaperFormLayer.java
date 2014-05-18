package lmcomponents.deskpanel.systemsettings.wallpapersettings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import lmcomponents.deskpanel.systemsettings.SystemSettings;
import lmcomponents.deskpanel.systemsettings.wallpapersettings.ui.ImageSearcher;
import lmcomponents.deskpanel.systemsettings.wallpapersettings.ui.LMThumbnailImage;
import lmcomponents.ui.LMButton;
import lmcomponents.ui.LMImage;
import lmcomponents.ui.LMLabel;
import lmcomponents.ui.LMToggleButton;

public class WallpaperFormLayer extends JLayeredPane {
	
	/******DECLARATIONS*******/
	private		String[]		extension = {"jpeg", "jpg", "png"};
	
	private 	LMButton 		okButton;
	private 	LMButton 		applyButton;
	private 	LMButton 		cancelButton;
	
	private 	JLayeredPane 	westDock;
	private 	JLayeredPane 	eastDock;
	private 	JLayeredPane 	southDock;
	private 	JLayeredPane 	eastNorthDock;
	
	private 	LMToggleButton	normal;
	private 	LMToggleButton	stretch;
	private 	LMToggleButton	fit;
	
	private 	ButtonGroup 	fillStyles;
		
	private		LMImage			testImage;
	private		LMThumbnailImage[]			imageThumbnails;
	
	private 	LMLabel 		fillPropText;
	private 	LMLabel 		previewText;
	private 	LMLabel 		selectWallpaperText;
	
	private 	JScrollPane		imageListContainer;
	private		JLayeredPane	imageListHolder;
	/*****END: DECLRATIONS*****/
	
	/*********METHODS************/
	public WallpaperFormLayer() throws IOException, URISyntaxException 
	{
		configure();
		this.setLayout(new MigLayout());
		addComponents();
	}
	
	private void configure() throws IOException, URISyntaxException
	{
		testImage = new LMImage("/home/abhinav/My Pictures/wallpaper1.jpg");
		testImage.setPreferredSize(new Dimension(160, 90));
		
		
		fillPropText = new LMLabel("Select fill property: ", -1);
		fillPropText.setFont(SystemSettings.LIGHT_FONT);
		fillPropText.setLeftShadow(0, 0, Color.decode("#FFFFFF"));
		fillPropText.setRightShadow(1, 1, Color.decode("#CCCCCC"));
		fillPropText.setHorizontalTextPosition(JLabel.CENTER);
			
		previewText = new LMLabel("Preview: ", -1);
		previewText.setFont(SystemSettings.LIGHT_FONT);
		previewText.setLeftShadow(0, 0, Color.decode("#FFFFFF"));
		previewText.setRightShadow(1, 1, Color.decode("#CCCCCC"));
		previewText.setHorizontalTextPosition(JLabel.CENTER);
			
		selectWallpaperText = new LMLabel("Select a wallpaper: ", -1);
		selectWallpaperText.setFont(SystemSettings.LIGHT_FONT);
		selectWallpaperText.setLeftShadow(0, 0, Color.decode("#FFFFFF"));
		selectWallpaperText.setRightShadow(1, 1, Color.decode("#CCCCCC"));
		selectWallpaperText.setHorizontalTextPosition(JLabel.CENTER);
			
		normal = new LMToggleButton("Normal");
		normal.setFont(SystemSettings.SYSTEM_FONT);
		
		stretch = new LMToggleButton("Stretch");
		stretch.setFont(SystemSettings.SYSTEM_FONT);
		
		fit = new LMToggleButton("Fit");
		fit.setFont(SystemSettings.SYSTEM_FONT);
		
		fillStyles = new ButtonGroup();
		fillStyles.add(normal);
		fillStyles.add(stretch);
		fillStyles.add(fit);
		
		okButton = new LMButton("OK");
		okButton.setFont(SystemSettings.SYSTEM_FONT);
		applyButton = new LMButton("Apply");
		
		applyButton.setFont(SystemSettings.SYSTEM_FONT);
		cancelButton = new LMButton("Cancel");
		
		cancelButton.setFont(SystemSettings.SYSTEM_FONT);
		
		southDock = new JLayeredPane();
		southDock.setLayout(new MigLayout());
		
		westDock = new JLayeredPane(){
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				
				Rectangle rect = new Rectangle(0,0,this.getWidth(), this.getHeight());
				
				g.setColor(Color.white);
				g2d.fill(rect);
				
				rect = new Rectangle(0,0,this.getWidth()-3, this.getHeight()-3);
				g.setColor(Color.decode("#DDDDDD"));
				g2d.draw(rect);
				
				rect = new Rectangle(0,0,this.getWidth()-2, this.getHeight()-2);
				g.setColor(Color.decode("#888888"));
				g2d.draw(rect);
				
				
						
				super.paintComponent(g);
			}
		};
		
		westDock.setLayout(new MigLayout());
		
		eastDock = new JLayeredPane();
		eastDock.setLayout(new MigLayout());
		
		eastNorthDock = new JLayeredPane();
		eastNorthDock.setLayout(new MigLayout());	
				
		GridLayout imageListLayout = new GridLayout(0,3);
		imageListLayout.setHgap(20);
		imageListLayout.setVgap(20);
		
		imageListHolder = new JLayeredPane(){
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D) g;
				
				Rectangle rect = new Rectangle(0,0,this.getWidth()-1, this.getHeight()-1);
			
				g.setColor(Color.white);
				g2d.fill(rect);
				
				g.setColor(Color.decode("#AAAAAA"));
						
				super.paintComponent(g);
			}
		};
		
		imageListHolder.setLayout(imageListLayout);
		
		imageListContainer = new JScrollPane(imageListHolder);
		
		imageThumbnails = ImageSearcher.getImages(new File("/usr/share/wallpapers/"), true);
		
	}
		
	private void addComponents()
	{
		//TODO: Implement image search algorithm
		for(int i= 0; i < imageThumbnails.length && i < 80; i++)
		{
			System.out.println(i+": Adding files...");
			imageListHolder.add(imageThumbnails[i]);
		}
		
		
		southDock.add(okButton, "w 105px, h 32px");
		southDock.add(cancelButton, "w 105px, h 32px");
		southDock.add(applyButton, "w 105px, h 32px");
		
		westDock.add(previewText, "gaptop 20px, gap 10px, span, wmin 100%, hmin 32px, wrap");
		westDock.add(testImage, "gaptop 10px, gapleft 10px, wmin 340px,hmin 200px, wrap");
		westDock.add(fillPropText, "gaptop 20px, gap 10px, span, wmin 100%, hmin 32px, wrap");
		westDock.add(normal, "w 108px, h 48px!, gap 10px, split 3");
		westDock.add(stretch, "w 108px, h 48px!, gap 10px");
		westDock.add(fit, "w 106px, h 48px!, gap 10px");
		
		eastNorthDock.add(selectWallpaperText, "gaptop 20px, gap 10px, span, w 200px, hmin 32px, wrap");
		eastNorthDock.add(imageListContainer, "gaptop 10px, gapbottom 0px, gap 10px, w 90%, h 100%");
		eastDock.add(eastNorthDock, "w 90%, h 85%, gaptop 1px");
		eastDock.add(southDock, "gap 10px, south");
		
		this.add(westDock, "gap 10px, gapright 10px, wmin 372px, hmin 95%");
		this.add(eastDock, "gap 10px, gapleft 10px, wmin 50%, hmin 95%");
	}
}

