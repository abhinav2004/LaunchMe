package lmcomponents.deskpanel.systemsettings.wallpapersettings.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class ImageSearcher {

	private static LMThumbnailImage[] imageThumbnails;

	public static LMThumbnailImage[] getImages(File directory, boolean resize) throws IOException
	{
		String[] directories = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        
		int count = 0;
		imageThumbnails = new LMThumbnailImage[directories.length];
		
		while(count < directories.length)
		{
			System.out.println(count+": Getting file: "+directory.toString()+"/"+directories[count]+"/contents/screenshot.png");
			File file2 = new File(directory.toString()+"/"+directories[count]+"/contents/screenshot.png");
			if(file2.exists() == false)
				file2 = new File(directory.toString()+"/"+directories[count]+"/contents/screenshot.jpg");
			if(file2.exists() == false)
				file2 = new File(directory.toString()+"/"+directories[count]+"/contents/contents.png");
			if(file2.exists() == false)
				file2 = new File(directory.toString()+"/"+directories[count]+"/contents/contents.jpg");
			
			if(file2.exists() == true)
			{
				BufferedImage buffer = ImageIO.read(file2);
				if(resize)
				buffer = Scalr.resize(buffer, 80, Scalr.OP_ANTIALIAS); 
				imageThumbnails[count] = new LMThumbnailImage(buffer);
				count++;
			}
		}
		
		return imageThumbnails;
		
	}

	
}
