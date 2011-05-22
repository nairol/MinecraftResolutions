// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.awt.Frame;
import java.util.List;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;

// Referenced classes of package net.minecraft.src:
//            GuiScreen, EnumOptions, GuiButton, GameSettings, 
//            StringTranslate, ScaledResolution, GuiSlider, GuiSmallButton

public class GuiVideoSettings extends GuiScreen
{

    public GuiVideoSettings(GuiScreen guiscreen, GameSettings gamesettings)
    {
        field_22107_a = "Video Settings";
        field_22110_h = guiscreen;
        field_22109_i = gamesettings;
    }

    public void initGui()
    {
        StringTranslate stringtranslate = StringTranslate.getInstance();
        field_22107_a = stringtranslate.translateKey("options.videoTitle");
        int i = 0;
        EnumOptions aenumoptions[] = field_22108_k;
        int j = aenumoptions.length;
        for(int k = 0; k < j; k++)
        {
            EnumOptions enumoptions = aenumoptions[k];
            if(!enumoptions.getEnumFloat())
            {
                controlList.add(new GuiSmallButton(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, field_22109_i.getKeyBinding(enumoptions)));
            } else
            {
                controlList.add(new GuiSlider(enumoptions.returnEnumOrdinal(), (width / 2 - 155) + (i % 2) * 160, height / 6 + 24 * (i >> 1), enumoptions, field_22109_i.getKeyBinding(enumoptions), field_22109_i.getOptionFloatValue(enumoptions)));
            }
            i++;
        }

        controlList.add(new GuiSmallButton(101, width / 2 - 155, height / 6 + 24 * 5, "1280x720"));
        controlList.add(new GuiSmallButton(102, width / 2 - 155 + 160, height / 6 + 24 * 5, "1920x1080"));
        controlList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168, stringtranslate.translateKey("gui.done")));
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if(!guibutton.enabled)
        {
            return;
        }
        if(guibutton.id < 100 && (guibutton instanceof GuiSmallButton))
        {
            field_22109_i.setOptionValue(((GuiSmallButton)guibutton).returnEnumOptions(), 1);
            guibutton.displayString = field_22109_i.getKeyBinding(EnumOptions.getEnumOptions(guibutton.id));
        }
        if(guibutton.id == 200)
        {
            mc.gameSettings.saveOptions();
            mc.displayGuiScreen(field_22110_h);
        }
        if(guibutton.id == 101)
        {
        	setInnerSize(1280, 720);
        }
        if(guibutton.id == 102)
        {
        	setInnerSize(1920, 1080);
        }
        
        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaledWidth();
        int j = scaledresolution.getScaledHeight();
        setWorldAndResolution(mc, i, j);
    }

    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, field_22107_a, width / 2, 20, 0xffffff);
        
        String res;
        if(Display.isFullscreen() == false)
        	res = String.format("Video size: %dx%d", getInnerWidth(), getInnerHeight());
        else
        	res = String.format("Video size: %dx%d", Display.getDisplayMode().getWidth(), Display.getDisplayMode().getHeight());
        drawCenteredString(fontRenderer, res, width / 2, height / 6 + 24 * 4 + 10, 0xffffff);
        
        super.drawScreen(i, j, f);
    }
    
    private void setInnerSize( int width, int height )
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
    
    private int getInnerWidth()
    {
    	Frame c = java.awt.Frame.getFrames()[0];
    	int iw = c.getInsets().left + c.getInsets().right;
    	return c.getSize().width - iw;
    }
    
    private int getInnerHeight()
    {
    	Frame c = java.awt.Frame.getFrames()[0];
    	int ih = c.getInsets().top + c.getInsets().bottom;
    	return c.getSize().height - ih;
    }

    private GuiScreen field_22110_h;
    protected String field_22107_a;
    private GameSettings field_22109_i;
    private static EnumOptions field_22108_k[];

    static 
    {
        field_22108_k = (new EnumOptions[] {
            EnumOptions.GRAPHICS, EnumOptions.RENDER_DISTANCE, EnumOptions.LIMIT_FRAMERATE, EnumOptions.ANAGLYPH, EnumOptions.VIEW_BOBBING, EnumOptions.AMBIENT_OCCLUSION, EnumOptions.GUI_SCALE, EnumOptions.ADVANCED_OPENGL
        });
    }
}
