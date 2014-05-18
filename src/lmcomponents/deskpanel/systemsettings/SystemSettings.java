package lmcomponents.deskpanel.systemsettings;

import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFrame;

import lmcomponents.deskpanel.systemsettings.wallpapersettings.WallpaperFormLayer;

import net.miginfocom.swing.MigLayout;

public class SystemSettings extends JFrame 
{
	public static Font SYSTEM_FONT = new Font("Bitstream Vera Sans", Font.PLAIN, 14);
	public static Font CURSIVE_FONT = new Font("URW Chancery L", Font.PLAIN, 32);
	public static Font LIGHT_FONT = new Font("DejaVu Sans Light", Font.TRUETYPE_FONT, 26);
	public static Font LIGHT_SMALL_FONT = new Font("DejaVu Sans Light", Font.TRUETYPE_FONT, 16);

	public SystemSettings() throws IOException, URISyntaxException 
	{
		super("LaunchMe Settings");
		configure();
		addComponents();
	}
	
	private void configure() 
	{
		this.setLayout(new MigLayout());
		this.setSize(800,450);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
		
	private void addComponents() throws IOException, URISyntaxException 
	{
		this.add(new WallpaperFormLayer(), "wmin 100%, hmin 100%");
	}	
	
}
