// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

// Referenced classes of package net.minecraft.src:
//            PanelCrashReport, UnexpectedThrowable

public class MinecraftAppletImpl extends Minecraft
{

    public MinecraftAppletImpl(MinecraftApplet minecraftapplet, Component component, Canvas canvas, MinecraftApplet minecraftapplet1, int i, int j, boolean flag)
    {
        super(component, canvas, minecraftapplet1, getDefaultWidth(), getDefaultHeight(), flag);
        mainFrame = minecraftapplet;
    }

    private static int getDefaultWidth()
    {
    	if(width == 0) loadDefaultResolution();
    	return width;
    }
    
    private static int getDefaultHeight()
    {
    	if(height == 0) loadDefaultResolution();
    	return height;
    }
    
    public static void loadDefaultResolution()
    {
        File mcResConfig = new File(Minecraft.getMinecraftDir(), "mcResConfig.txt");
	    try
	    {
	        if(mcResConfig.exists())
	        {
		        BufferedReader bufferedreader = new BufferedReader(new FileReader(mcResConfig));
		        for(String line = ""; (line = bufferedreader.readLine()) != null;)
		        {
		            String key[] = line.split(":");
		            if(key[0].equals("width"))
		            {
		                width = Integer.parseInt(key[1]);
		            }
		            if(key[0].equals("height"))
		            {
		                height = Integer.parseInt(key[1]);
		            }
		            if(key[0].equals("maximized"))
		            {
		                maximized = Integer.parseInt(key[1]);
		            }
		        }
		        bufferedreader.close();
	        }
	    }
	    catch(Exception exception)
	    {
	        System.out.println("MinecraftResolutions: Failed to load config");
	    }
	    
	    if( width > 0 && height > 0 )
	    {
	    	Frame c = java.awt.Frame.getFrames()[0];
	    	if(maximized == 0)
	    	{
		    	int iw = c.getInsets().left + c.getInsets().right;
		    	int ih = c.getInsets().top + c.getInsets().bottom;
		    	int oldw = c.getSize().width;
		    	int oldh = c.getSize().height;
		    	c.setSize(width + iw, height + ih);
		    	int istw = c.getSize().width - iw;
		    	int isth = c.getSize().height - ih;
		    	if(istw != width || isth != height)
		    	{
		    		c.setSize(oldw, oldh);
		    	}
		    	c.setLocationRelativeTo(null);
	    	}
	    	else
	    	{
	    		c.setExtendedState(Frame.MAXIMIZED_BOTH);
	    	}
	    }
	    else
	    {
	    	width = 854;
	    	height = 480;
	    }
    }

    
    public void displayUnexpectedThrowable(UnexpectedThrowable unexpectedthrowable)
    {
        mainFrame.removeAll();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(new PanelCrashReport(unexpectedthrowable), "Center");
        mainFrame.validate();
    }

    final MinecraftApplet mainFrame; /* synthetic field */
    static int width = 0, height = 0, maximized = 0;

}
