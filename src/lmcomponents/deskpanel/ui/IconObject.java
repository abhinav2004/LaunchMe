package lmcomponents.deskpanel.ui;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.LineMetrics;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import lmcomponents.ui.LMPane;

import org.imgscalr.Scalr;


public class IconObject extends JLabel implements MouseListener
{
	
	public	boolean	selected;
	
	/* Declaring content pane instance and other variables */
	
	private 	URL				iconURL;
	private 	LMPane			mainPane;
	private 	String 			tooltipText;
	private 	int 			tracking;
	private 	int 			left_x;
	private 	int   			left_y;
	private 	int 			right_x;
	private 	int 			right_y;
	
	private 	Color 			left_color;
	private 	Color			right_color;

	
	/* Declaring Graphic objects */
	
	private		Graphics		componentG;
	
	private 	Graphics		backgroundG;
	private 	Graphics		highlightG;
	private 	Graphics		iconG;
	private 	Graphics		textG;
	
	/* Declaring Graphics 2D Objects */
	
	private 	Graphics2D		componentG2D;
	
	private 	Graphics2D		backgroundG2D;
	private 	Graphics2D		highlightG2D;
	private 	Graphics2D		iconG2D;
	private 	Graphics2D		textG2D;
		
	/* Declaring BufferedImage */
	
	private 	BufferedImage 	backgroundImage;
	private 	BufferedImage 	highlightImage;
	private		BufferedImage	iconImage;
	private 	BufferedImage 	textImage;
	
	/* Position of component on the content pane */
	
	private 	Dimension		contentPanePos;
	
	/* Constant Variables */
	
	protected final Dimension 	componentSize;
	private final 	int 		TIMEPERIOD = 10;
	
	/* Declaring Runtime Objects */
	
	private 	Runtime			runtime;
	private 	Process	 		process;
	private 	String 			processName;
			
	public IconObject(String name, String icon, String exec, String info, int tracking, LMPane p) throws IOException
	{
		//Defining constant Objects
		componentSize = new Dimension(96, 96);
		
		selected = false;
		
		//Defining BufferedImages
		backgroundImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		highlightImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		iconImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		textImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		
		// Setting the Graphics object
		componentG = this.getGraphics();
		backgroundG = backgroundImage.getGraphics();
		highlightG = highlightImage.getGraphics();
		iconG = iconImage.getGraphics();
		textG = textImage.getGraphics();
		
		componentG2D = (Graphics2D) componentG;
		backgroundG2D = (Graphics2D) backgroundG;
		highlightG2D = (Graphics2D) highlightG;
		iconG2D = (Graphics2D) iconG;
		textG2D = (Graphics2D) textG;
		
		//configuring the component
		iconURL = new URL("file:///usr/share/icons/hicolor/256x256/apps/"+icon+".png");
		processName = exec;
		tooltipText = info;
		this.tracking = tracking;
		mainPane = p;
		
		this.setText(name);
		this.addMouseListener(this);
		
		//Setting runtime
		runtime = Runtime.getRuntime();
		
		
		
	}
	
	
	  public void setLeftShadow(int x, int y, Color color) {
	    left_x = x;
	    left_y = y;
	    left_color = color;
	  }

	  public void setRightShadow(int x, int y, Color color) {
	    right_x = x;
	    right_y = y;
	    right_color = color;
	  }

	  public Dimension getPreferredSize() {
	    String text = getText();
	    FontMetrics fm = this.getFontMetrics(getFont());

	    int w = fm.stringWidth(text);
	    w += (text.length() - 1) * tracking;
	    w += left_x + right_x;

	    int h = fm.getHeight();
	    h += left_y + right_y;

	    return new Dimension(w, h);
	  }
	  
	  private void paintBackground(Graphics g)
	  {
		  contentPanePos = new Dimension(this.getX(), this.getY());		  
		  BufferedImage scaledImage = Scalr.crop(mainPane.backgroundImg, contentPanePos.width, contentPanePos.height, 96, 96);
		  g.drawImage(scaledImage, 0,0,null);
	  }
	  
	  private void paintIcon(Graphics g) throws IOException
	  {  
		  BufferedImage iconBuffer = ImageIO.read(iconURL);
		  iconBuffer = Scalr.resize(iconBuffer, 64);
		  g.drawImage(iconBuffer, 16, 8, 64, 64, null);
		  
	  }
	  
	  private void paintText(Graphics g)
	  {		
		  BufferedImage tempBuffer = new BufferedImage(96,96,BufferedImage.TYPE_INT_ARGB);
		  Graphics tempBG = tempBuffer.getGraphics();
		  Graphics2D tempBG2D = (Graphics2D) tempBG;
		  
		  tempBG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		  
		  textG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		  
		  float[] blurKernel = {
		            1/21f, 1/21f, 1/21f,
		            1/21f, 1/21f, 1/21f,
		            1/21f, 1/21f, 1/21f
		        };
		  char[] chars = getText().toCharArray();

		  FontMetrics fm = this.getFontMetrics(getFont());
		  int h = fm.getAscent();
		  LineMetrics lm = fm.getLineMetrics(getText(), g);
		  g.setFont(getFont());
		  tempBG.setFont(getFont());
		  
		  int posX;
		  posX = fm.stringWidth(getText());
		    
		  int x = 45 - (posX/2);
		  h = h+72;
		  for (int i = 0; i < chars.length; i++) {
			  char ch = chars[i];
		      int w = fm.charWidth(ch) + tracking;
 
		      tempBG.setColor(left_color);
		      tempBG.drawString("" + chars[i], x - left_x, h - left_y);

		      tempBG.setColor(right_color);
		      tempBG.drawString("" + chars[i], x + right_x, h + right_y);
		      x += w;
		  }
		  
		  BufferedImageOp blurText = new ConvolveOp(new Kernel(3,3,blurKernel));
		  tempBuffer = blurText.filter(tempBuffer, new BufferedImage(96,96,BufferedImage.TYPE_INT_ARGB));
		  
		  g = textImage.getGraphics();
		  g.setFont(getFont());
		  ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		  
		  g.drawImage(tempBuffer, 0,0,null);
		  
		  x = 45 - (posX/2);
		  for (int i = 0; i < chars.length; i++) {
		      char ch = chars[i];
		      int w = fm.charWidth(ch) + tracking;
		      g.setColor(this.getForeground());
		      g.drawString("" + chars[i],x, h );
		      x += w;
		  }
	  }
	  
	  private void paintHighlight(Graphics g)
	  {
		  g.setColor(Color.white);
		  highlightG2D.fillRoundRect(0, 0, 95, 95, 0, 0);
	  }
	 
	  @Override
	  public void paintComponent(Graphics g)
	  {
		  componentG = this.getGraphics();
			componentG2D = (Graphics2D) componentG;
		  this.paintBackground(backgroundG);
		  
		  try {
			this.paintIcon(iconG);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  this.paintText(textG);
		  this.paintHighlight(highlightG);
		  g.drawImage(backgroundImage, 0,0,null);
		  g.drawImage(iconImage, 0,0,null);
		  g.drawImage(textImage, 0,0,null);
		  
	  }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			if(arg0.getClickCount() == 1)
				selected = true;
			else if(arg0.getClickCount() == 2)
				try {
					selected = false;
					process = runtime.exec(processName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		mainPane.toBack();
		  
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		BufferedImage tempBuffer = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		Graphics G = tempBuffer.getGraphics();
		Graphics2D G2D = (Graphics2D) G;
		
		
		for(float opacity = 0.0f; opacity < 0.4f; opacity = opacity + 0.04f)
		  {
				try {
					Thread.sleep(TIMEPERIOD);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			  G.drawImage(backgroundImage, 0,0,null);
			  	G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity));
			  G.drawImage(highlightImage, 0,0,null);
			  if(selected)
				  G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
			  else
				  G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity*2));
			  G.setColor(Color.decode("#EEEEEE"));
			  G.drawRoundRect(0, 0, 95, 95,0, 0);
			  	G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
			  G.drawImage(iconImage, 0,0,null);
			  G.drawImage(textImage, 0,0,null);
			  
			  componentG.drawImage(tempBuffer, 0,0,null);
		  }
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		BufferedImage tempBuffer = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		Graphics G = tempBuffer.getGraphics();
		Graphics2D G2D = (Graphics2D) G;
		
		for(float opacity = 0.4f; opacity >= 0.0f; opacity = opacity - 0.04f)
		  {
				try {
					Thread.sleep(TIMEPERIOD);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			  G.drawImage(backgroundImage, 0,0,null);
			  
			  	G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity));
			  G.drawImage(highlightImage, 0,0,null);
			  if(selected)
				  G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.8f));
			  else
				  G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opacity*2));
			  G.setColor(Color.decode("#EEEEEE"));
			  G.drawRoundRect(0, 0, 95, 95, 0, 0);
			  	G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
			  G.drawImage(iconImage, 0,0,null);
			  G.drawImage(textImage, 0,0,null);
			  
			  componentG.drawImage(tempBuffer, 0,0,null);
		  }
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
