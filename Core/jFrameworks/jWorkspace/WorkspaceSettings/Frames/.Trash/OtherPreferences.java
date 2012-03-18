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

package Core.desktop.DesktopSettings.Frames;

import Core.Packages.errHandler.errDialog;
import Core.desktop.DesktopSettings.*;

import java.io.*;
import javax.swing.*;
import javax.swing.UIManager;
import javax.swing.BorderFactory.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import net.miginfocom.layout.*;
import net.miginfocom.swing.*;

public class OtherPreferences extends JLayeredPane implements ItemListener
{
	JCheckBox[] check;
	JButton apply;
	Object DesktopSettingsWindow;
	
	private void Components()
	{
		//DesktopSettingsWindow = new DesktopSettings();
		check = new JCheckBox[3];	
			check[0] = new JCheckBox("Use Global Menu");
			check[1] = new JCheckBox("Make Themes Changable");
				check[1].addItemListener(this);
			check[2] = new JCheckBox("Enable Wallpaper");
	}

	private void addComponents()
	{
		this.add(new JLabel("Select as per your preference:"), "wrap, gapbottom 10");
		for (int i = 0; i<3; i++)
			this.add(check[i], "wrap");
	}

	public void itemStateChanged(ItemEvent event)
	{
		if(event.getSource() == check[1])
		{
			if(event.getStateChange() == ItemEvent.SELECTED)
				new Core.desktop.DesktopSettings.DesktopSettings().disableTheme(false);
			else
				new Core.desktop.DesktopSettings.DesktopSettings().disableTheme(true);
		}
	}

	public OtherPreferences()
	{
		this.setLayout(new MigLayout());
		Components();
		addComponents();
	}
}
