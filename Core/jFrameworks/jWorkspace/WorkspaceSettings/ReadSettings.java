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

package Core.desktop.DesktopSettings;

import Core.desktop.DesktopSettings.Frames.*;
import Core.Packages.errHandler.errDialog;

import java.io.*;
import java.util.*;

public ReadSettings
{
	FileReader fw = new FileReader("Core/desktop/DesktopSettings/Desktop.settings");
	BufferedReader br = new BufferedReader(fw);
	StringTokenizer token;

	public Object readSettings(int SETTING_SERIAL_NO)
	{
		int count = 0;
		String temp;
		while(count < SETTING_SERIAL_NO)
		{
			temp = br.readLine();
			count++;
		}
		token = new StringTokenizer(temp, "=");
			token.nextToken();
			return token.nextToken();
	}
}
