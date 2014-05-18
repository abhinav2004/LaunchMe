package rejectedclasses.lmcomponents.ui;
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
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolTip;

import org.imgscalr.Scalr;


public class IconObject extends JLabel implements MouseListener{
	
	final private int TIMEPERIOD = 10;
	
	BufferedImage 	iconImage;
	
	Runtime 	runtime;
	Process 	executor;
	String 		appName;
	
	lmcomponents.ui.LMPane		mainPane;
	
	BufferedImage tempBuffer,tempBuffer2; 
	BufferedImage blurredImage;
	BufferedImage textImage;
	BufferedImage background ;
	
	Graphics 	mg, g2, fg;
	
	Graphics2D 	g2d, g2d2;
	JToolTip 	tooltip;
	
	public IconObject(String name, String icon, String exec, String info, int tracking, lmcomponents.ui.LMPane p) 
	/*
	 * name = The name of the application.
	 * icon = The launcher icon. PNGs and JPGs are preferred.
	 * exec = Instruction to be executed.
	 * info = Tooltip for the launcher. 
	 */
	
	{
		mainPane = p;
		
		this.tracking = tracking;
		try {
			iconImage = ImageIO.read(new URL("file:///usr/share/icons/hicolor/256x256/apps/"+icon+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.tracking = tracking;
		this.setText(name);
		this.setToolTipText(info);
		this.setVerticalTextPosition(BOTTOM);
		this.setHorizontalTextPosition(CENTER);
		this.addMouseListener(this);
		
		runtime = Runtime.getRuntime();
		appName = exec;		
		
		tooltip = new JToolTip();
		tooltip.setTipText(info);
		tooltip.setComponent(this);
	}
	
	private int tracking;

	  

	  private int left_x, left_y, right_x, right_y;

	  private Color left_color, right_color;

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

	  private void paintIcon(Graphics g)
	  {
		  iconImage = Scalr.resize(iconImage, 64);
		  
		  g.drawImage(iconImage, 16, 8, 64, 64, null);
	  }
	  
	  void paintBlurText(Graphics g)
	  {
		  blurredImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		  Graphics blurG = blurredImage.getGraphics();
		  
		  float[] blurKernel = {
		            1/12f, 1/12f, 1/12f,
		            1/12f, 1/12f, 1/12f,
		            1/12f, 1/12f, 1/12f
		        };
		  char[] chars = getText().toCharArray();

		    FontMetrics fm = this.getFontMetrics(getFont());
		    int h = fm.getAscent();
		    LineMetrics lm = fm.getLineMetrics(getText(), g);
		    g.setFont(getFont());
		    blurG.setFont(g.getFont());

		    int posX;
		    
		    posX = fm.stringWidth(getText());
		    
		    int x = 45 - (posX/2);
		    h = h+72;

		    
		    
		    for (int i = 0; i < chars.length; i++) {
		      char ch = chars[i];
		      int w = fm.charWidth(ch) + tracking;

		      
		      
		      blurG.setColor(left_color);
		      blurG.drawString("" + chars[i], x - left_x, h - left_y);

		      blurG.setColor(right_color);
		      blurG.drawString("" + chars[i], x + right_x, h + right_y);
		      x += w;
		    }
		    
		    BufferedImageOp blurText = new ConvolveOp(new Kernel(3,3,blurKernel));
		    blurredImage = blurText.filter(blurredImage, new BufferedImage(96,96,BufferedImage.TYPE_INT_ARGB));
		    
		    g.drawImage(blurredImage, 0,0,null);
	  }
	  
	  public void paintComponent(Graphics g) {
		  background = mainPane.backgroundImg.getSubimage(this.getX(), this.getY(), 96, 96);
		  paintIcon(g);
		  paintBlurText(g);
		  textImage = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		  
		  Graphics mg = textImage.getGraphics();
		  
	    ((Graphics2D) mg).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
	        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    

	    char[] chars = getText().toCharArray();

	    FontMetrics fm = this.getFontMetrics(getFont());
	    int h = fm.getAscent();
	    LineMetrics lm = fm.getLineMetrics(getText(), g);
	    mg.setFont(getFont());
	  

	    int posX;
	    
	    posX = fm.stringWidth(getText());
	    
	    int x = 45 - (posX/2);
	    h = h+72;
	    
	    //h = h+72;
	    for (int i = 0; i < chars.length; i++) {
		      char ch = chars[i];
		      int w = fm.charWidth(ch) + tracking;
	      //blurG.setColor(getForeground());
	      
	      
	      mg.setColor(this.getForeground());
	      mg.drawString("" + chars[i],x, h );
	      x += w;
	    }
	    g.drawImage(textImage, 0,0, null);

	  }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				mainPane.toBack();
				if(e.getClickCount() == 2)
				{
					executor = runtime.exec(appName);
				}
			}
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		mg = this.getGraphics();
		
		tempBuffer = new BufferedImage(192, 192, BufferedImage.TYPE_INT_ARGB);
		tempBuffer2 = new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		
		fg = tempBuffer2.getGraphics();
		
		g2 = tempBuffer.getGraphics();
		g2d = (Graphics2D) g2;
		g2d2 = (Graphics2D) fg;
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setBackground(new Color(0,0,0,0));
				
		RoundRectangle2D rect = new RoundRectangle2D.Float(0, 0, 191, 191, 16, 16);
		RoundRectangle2D rect2 = new RoundRectangle2D.Float(0, 0, 96, 96, 8, 8);
		fg.setColor(Color.white);
		g2.setColor(Color.white);
		g2d2.draw(rect2);
		g2.setColor(Color.white);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));

		g2d.fill(rect);
		
		g2.setColor(Color.white);
		tempBuffer = Scalr.resize(tempBuffer, 96, Scalr.OP_ANTIALIAS);
		for(float f = 0.0f; f < 0.6f; f = f+ 0.06f)
		{
			try {
				Thread.sleep(TIMEPERIOD);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			fg.drawImage(background, 0,0,null);
			g2d2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,f));
			fg.drawImage(tempBuffer, 0, 0, null);								
			g2d2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
			fg.drawImage(blurredImage,0,0,null);
			fg.drawImage(textImage,0,0,null);	
			this.paintIcon(fg);
			mg.drawImage(tempBuffer2,0,0,null);
			
			
			
		}
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
mg = this.getGraphics();
		
		
		for(float f = 0.6f; f > 0.0f; f = f- 0.06f)
		{
			try {
				Thread.sleep(TIMEPERIOD);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			fg.drawImage(background, 0,0,null);
			g2d2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,f));
			fg.drawImage(tempBuffer, 0, 0, null);			
			g2d2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f));
			fg.drawImage(blurredImage,0,0,null);					
			fg.drawImage(textImage,0,0,null);	
			this.paintIcon(fg);
			mg.drawImage(tempBuffer2,0,0,null);
			
			
			
		}
		
				
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mainPane.toBack();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mainPane.toBack();
	}
	
}
