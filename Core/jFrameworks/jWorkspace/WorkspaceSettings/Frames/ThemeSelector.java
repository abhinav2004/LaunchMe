/*
 * This file belongs to the Puma Project.
 *
 * This file is under heavy development. But anyone is
 * entitled to change the code as long as they don't
 * break the whole project or a part of it.
 *
 * File belongs to Abhinav Hardikar
 *
 * Developers (You can add your name if you make any notable changes): 
 * 1) Abhinav Hardikar (a.k.a aHardyX) (Contact: ahardyx@yahoo.in)
 */

package Core.jFrameworks.jWorkspace.WorkspaceSettings.Frames;

import Core.jFrameworks.jErrorHandler.errDialog;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.BorderFactory.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import net.miginfocom.layout.*;
import net.miginfocom.swing.*;

public class ThemeSelector extends JLayeredPane implements ActionListener, ItemListener
{
	JLabel 		THEME_PIC;
	JComboBox 	THEME_LIST;
	String[]	THEME_LIST_ARRAY;
	JButton		apply;
	String		THEME_NAME;
	
	
	private void Components()
	{
		//this.setBorder(BorderFactory.createTitledBorder("Theme Selector"));
		this.setLayout(new MigLayout());
		apply = new JButton("Apply");
			apply.addActionListener(this);
		THEME_PIC = new JLabel();
		THEME_LIST_ARRAY = new String[3];
			THEME_LIST_ARRAY[0] = "Nimbus";
			THEME_LIST_ARRAY[1] = "SeaGlass";
			THEME_LIST_ARRAY[2] = "Metal";
		THEME_LIST = new JComboBox(THEME_LIST_ARRAY);	
			THEME_LIST.addItemListener(this);
	}

	private void addComponents()
	{
		this.add(new JLabel("Picture of the Theme:"), "wrap");
		this.add(THEME_PIC, "w 100%, h 100%, wrap");
		this.add(new JLabel("Select a theme: "), "wrap");
		this.add(THEME_LIST, "w 100%, h 26, wrap");
		this.add(apply, "w 100, h 26, span, x 284, gapright 15");
	}

	private void readSettings()
	{
		try
		{
			
			FileReader fw = new FileReader("Core/jFrameworks/jWorkspace/WorkspaceSettings/Workspace.settings");
			BufferedReader br = new BufferedReader(fw);
				StringTokenizer token = new StringTokenizer(br.readLine(), "=");
				THEME_NAME = token.nextToken();
				THEME_NAME = token.nextToken();
				//System.out.println(THEME_NAME); //DEBUG: Remove first comment to start debugging
				System.out.println("CORE: INFO: Preset theme is "+THEME_NAME);
			br.close();
			fw.close();
			
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
	
	public void itemStateChanged (ItemEvent event)
	{
		if(event.getSource() == THEME_LIST)
		{
			String temp = event.getItem().toString();
			if(temp.equals("SeaGlass"))
			{
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/2.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(temp.equals("Nimbus"))
			{
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/1.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(temp.equals("Metal"))
			{
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/3.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);
			}
		}
	}
	
	private void writeSettings(String THEME)
	{
		try
		{
			FileWriter fw = new FileWriter("Core/jFrameworks/jWorkspace/WorkspaceSettings/Workspace.settings");
				fw.write("Theme="+THEME);
				fw.close();
			
			
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

	public void actionPerformed(ActionEvent event) 
	{
		try
		{
			if(event.getSource() == apply)
			{
				String SELECTED_THEME = new String(THEME_LIST.getSelectedItem().toString());
				if(SELECTED_THEME.equals("SeaGlass"))
				{
					writeSettings(SELECTED_THEME);
					UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					System.out.println("CORE: INFO: Theme changed to SeaGlass");
				}
				else if(SELECTED_THEME.equals("Nimbus"))
				{
					writeSettings(SELECTED_THEME);
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					System.out.println("CORE: INFO: Theme changed to Nimbus");
				}
				else if(SELECTED_THEME.equals("Metal"))
				{
					writeSettings(SELECTED_THEME);
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					System.out.println("CORE: INFO: Theme changed to Metal");
				}
				SwingUtilities.updateComponentTreeUI(this.getParent().getParent().getParent().getParent().getParent().getParent().getParent().getParent());
			}
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
	

	public ThemeSelector()
	{
		Components();
		addComponents();
		readSettings();
			if(THEME_NAME.equals("SeaGlass"))
			{
				THEME_LIST.setSelectedIndex(1);
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/2.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);				
			}
			else if(THEME_NAME.equals("Nimbus"))
			{
				THEME_LIST.setSelectedIndex(0);
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/1.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);
			}
			else if(THEME_NAME.equals("Metal"))
			{
				THEME_LIST.setSelectedIndex(2);
				THEME_PIC.setIcon(new ImageIcon("Core/jFrameworks/jWorkspace/WorkspaceSettings/Frames/Images/3.PNG"));
				THEME_PIC.setHorizontalAlignment(JLabel.CENTER);
			}
	}
}
