// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

/* MC RESOLUTIONS ---> */
import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
/* <--- MC RESOLUTIONS */

import java.util.List;

/* MC RESOLUTIONS ---> */
import org.lwjgl.opengl.Display;
/* <--- MC RESOLUTIONS */

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, StringTranslate, EnumOptions, GuiSmallButton, 
//            GameSettings, GuiSlider, GuiButton, ScaledResolution

public class GuiVideoSettings extends GuiScreen
{

    public GuiVideoSettings(GuiScreen guiscreen, GameSettings gamesettings)
    {
        field_22107_a = "Video Settings";
        parentGuiScreen = guiscreen;
        guiGameSettings = gamesettings;
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        field_22107_a = stringtranslate.translateKey("options.videoTitle");
        int i = 0;
        EnumOptions aenumoptions[] = videoOptions;
        int j = aenumoptions.length;
        for(int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];
            if(!enumoptions.getEnumFloat())
            {
                controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, guiGameSettings.getKeyBinding(enumoptions)));
            } else
            {
                controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, guiGameSettings.getKeyBinding(enumoptions), guiGameSettings.getOptionFloatValue(enumoptions)));
            }
            i++;
        }
        /* MC RESOLUTIONS ---> */
        controlList.add(new GuiSmallButton(101, width / 2 - 155, height / 6 + 24 * 6, 75, 20, "1280x720"));
        controlList.add(new GuiSmallButton(102, width / 2 - 155 + 76, height / 6 + 24 * 6, 75, 20, "1920x1080"));
        controlList.add(new GuiSmallButton(103, width / 2 - 155 + 160, height / 6 + 24 * 6, "Current size as default"));
        /* <--- MC RESOLUTIONS */
        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        int i = guiGameSettings.guiScale;
        if(guibutton.id < 100 && (guibutton instanceof GuiSmallButton))
        {
            guiGameSettings.setOptionValue(((GuiSmallButton)guibutton).returnEnumOptions(), 1);
            guibutton.displayString = guiGameSettings.getKeyBinding(EnumOptions.getEnumOptions(guibutton.id));
        }
        if(guibutton.id == 200)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(parentGuiScreen);
        }
        
        /* MC RESOLUTIONS ---> */
        if(guibutton.id == 101)
        {
        	setInnerSize(1280, 720);
        }
        if(guibutton.id == 102)
        {
        	setInnerSize(1920, 1080);
        }
        if(guibutton.id == 103)
        {
        	setDefaultResolution();
        }
        /* <--- MC RESOLUTIONS */
        
        if(guiGameSettings.guiScale != i)
        {
            ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
            int j = scaledresolution.getScaledWidth();
            int k = scaledresolution.getScaledHeight();
            setWorldAndResolution(mc, j, k);
        }
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, field_22107_a, width / 2, 20, 0xffffff);
        /* MC RESOLUTIONS ---> */
        String res;
        if(Display.isFullscreen() == false)
        	res = String.format("Video size: %dx%d", getInnerWidth(), getInnerHeight());
        else
        	res = String.format("Video size: %dx%d", Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        drawCenteredString(fontRenderer, res, width / 2, height / 6 + 24 * 5 + 10, 0xffffff);
        /* <--- MC RESOLUTIONS */
        super.drawScreen(i, j, f);
    }

    /* MC RESOLUTIONS ---> */
    public static void setInnerSize( int width, int height )
    {
    	Frame c = java.awt.Frame.getFrames()[0];
    	int iw = c.getInsets().left + c.getInsets().right;
    	int ih = c.getInsets().top + c.getInsets().bottom;
    	int oldw = c.getSize().width;
    	int oldh = c.getSize().height;
    	c.setSize(width + iw, height + ih);
    	int istw = c.getSize().width - iw;
    	int isth = c.getSize().height - ih;
    	if(istw != width || isth != height)
    		c.setSize(oldw, oldh);
    	c.setLocationRelativeTo(null);
    }
    
    public static int getInnerWidth()
    {
    	Frame c = java.awt.Frame.getFrames()[0];
    	int iw = c.getInsets().left + c.getInsets().right;
    	return c.getSize().width - iw;
    }
    
    public static int getInnerHeight()
    {
    	Frame c = java.awt.Frame.getFrames()[0];
    	int ih = c.getInsets().top + c.getInsets().bottom;
    	return c.getSize().height - ih;
    }
    
    public static void setDefaultResolution()
    {
    	File mcResConfig = new File(Minecraft.getMinecraftDir(), "mcResConfig.txt");
    	try
        {
    		Frame c = java.awt.Frame.getFrames()[0];
            PrintWriter printwriter = new PrintWriter(new FileWriter(mcResConfig));
            printwriter.println((new StringBuilder()).append("width:").append(getInnerWidth()).toString());
            printwriter.println((new StringBuilder()).append("height:").append(getInnerHeight()).toString());
            int maximized = c.getExtendedState() == Frame.MAXIMIZED_BOTH ? 1 : 0;
            printwriter.println((new StringBuilder()).append("maximized:").append(maximized).toString());
            printwriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("MinecraftResize: Failed to save screen resolution");
            exception.printStackTrace();
        }
    }
    /* <--- MC RESOLUTIONS */
    
    private GuiScreen parentGuiScreen;
    protected String field_22107_a;
    private GameSettings guiGameSettings;
    private static EnumOptions videoOptions[];

    static 
    {
        videoOptions = (new EnumOptions[] {
            EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE, EnumOptions.AMBIENT_OCCLUSION, EnumOptions.FRAMERATE_LIMIT, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL, EnumOptions.GAMMA
        });
    }
}
