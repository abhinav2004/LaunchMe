/*
 * This file belongs to the Puma Project.
 *
 * This file is under heavy development. But anyone is
 * entitled to change the code as long as they don't
 * break the whole project or a part of it.
 *
 * File belongs to Abhinav Hardikar
 *
 * Developers:
 * 1) Abhinav Hardikar (a.k.a aHardyX) (Contact: ahardyx@yahoo.in)
 */

package Core.desktop;

import Core.desktop.DesktopSettings.*;
import Core.desktop.Dock.*;
import Core.Packages.errHandler.errDialog;

import java.io.*;
import javax.swing.*;
import javax.swing.UIManager;
import java.awt.*;

import net.miginfocom.layout.*;
import net.miginfocom.swing.*;

public class PumaDesktopFrame extends JWindow
{
	//Getting the users native screen resolution.
	Toolkit toolkit =  Toolkit.getDefaultToolkit();
	Dimension ScreenResolution = toolkit.getScreenSize();

	//Initializing Class variables
	Container DesktopContainer;
	JDesktopPane Desktop;
	JLabel background;
	JLabel[] DockIcons;	
	JLayeredPane DockObject;

	private void Components()
	{
		DockObject = new Dock();

		Image img1 = ((new ImageIcon("Core/desktop/default.jpg").getImage()));

		DesktopContainer = new Container();
			DesktopContainer.setLayout(new MigLayout());
		Desktop = new JDesktopPane();
			Desktop.setLayout(null);
		background = new JLabel(new ImageIcon(img1.getScaledInstance((int)ScreenResolution.getWidth(), (int) ScreenResolution.getHeight(), java.awt.Image.SCALE_SMOOTH)));
			background.setSize(ScreenResolution);
			background.setLocation(0,0);
	}

	private void addComponents()
	{
		

		//Desktop.add(new DesktopSettings());//, "w 400, h 400, split, span");		
		//Desktop.add(background);
			//Desktop.moveToBack(Desktop);
		Desktop.add(DockObject);
			Desktop.moveToFront(DockObject);
		DesktopContainer.add(Desktop, "w 100%, h 100%");
		this.setContentPane(DesktopContainer);
	}

	public PumaDesktopFrame()
	{
		try
		{
			//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			//UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			
			
    			/*for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
			{
        			if ("Nimbus".equals(info.getName())) 
				{
            				UIManager.setLookAndFeel(info.getClassName());
            				break;
       				}
  			}*/
			
		
			this.setLayout(new MigLayout());
			this.setSize(ScreenResolution);
			this.setLocationRelativeTo(null);
			//this.setTitle("Puma");

			Components();
			addComponents();
		}
		catch(Exception e)
		{
			try
			{
				new errDialog(e.toString(), "err");
			}
			catch(Exception main_e)
			{
				System.out.println("ERROR: "+main_e.toString());
			}
		}
	}
}
