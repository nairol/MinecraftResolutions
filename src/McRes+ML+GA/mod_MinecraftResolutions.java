package net.minecraft.src;
import java.awt.Frame;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import net.minecraft.client.Minecraft;

public class mod_MinecraftResolutions extends BaseMod
{
	Minecraft mc;
	Frame f;
	boolean sizeRestored = false;
	
	ModSettings ms = new ModSettings("mod_MinecraftResolutions");
	ModSettingScreen mss = new ModSettingScreen("Video size");
	SettingInt sih = new SettingInt("height", 480, 100, Integer.MAX_VALUE);
	SettingInt siw = new SettingInt("width", 854, 100, Integer.MAX_VALUE);
	SettingBoolean sbm = new SettingBoolean("maximized", false);
	Label lSize = new Label();
	Label lSaved = new Label();
	SimpleButtonModel sbmDefault = new SimpleButtonModel();
	SimpleButtonModel sbm720 = new SimpleButtonModel();
	SimpleButtonModel sbm1080 = new SimpleButtonModel();
	Button bDefault = new Button(sbmDefault);
	Button b720  = new Button(sbm720);
	Button b1080 = new Button(sbm1080);
	
	public mod_MinecraftResolutions() {
		mc = ModLoader.getMinecraftInstance();
		f = Frame.getFrames()[0];
		ms.append(siw);
		ms.append(sih);
		ms.append(sbm);
		ms.load();
		initGui();
		ModLoader.SetInGUIHook(this, true, false);
	}
	
	public String Version() {
		return "1.8.1";
	}
	
	public boolean OnTickInGUI(Minecraft minecraft, GuiScreen guiscreen) {
		if(GuiModScreen.currentScreen != null && GuiModScreen.currentScreen.mainwidget == mss.theWidget)
		{
			if( f.getExtendedState() == Frame.MAXIMIZED_BOTH )
				lSize.setText("Current size: maximized");
			else
				lSize.setText(String.format("Current size: %dx%d", mc.displayWidth, mc.displayHeight));
		}
		else if( !sizeRestored )
		{
			setVideoSize((Integer)siw.get(), (Integer)sih.get(), (Boolean)sbm.get());
			sizeRestored = true;
		}
		return true;
	}
	
	private void initGui()
	{
		mss.append(lSize);
		mss.append(new Widget());
		mss.append(lSaved);
		mss.append(bDefault);
		mss.append(new Widget());
		mss.append(new Widget());
		mss.append(b720);
		mss.append(b1080);
		updateDefaultLabel();
		sbmDefault.addActionCallback(new ModAction(this, "setDefault", null));
		sbm720.addActionCallback(new ModAction(this, "set720", null));
		sbm1080.addActionCallback(new ModAction(this, "set1080", null));
		bDefault.setText("Current settings as default");
		b720.setText("1280x720");
		b1080.setText("1920x1080");
	}
	
	private void updateDefaultLabel()
	{
		if( (Boolean)sbm.get() )
			lSaved.setText("Default size:  maximized");
		else
			lSaved.setText(String.format("Default size:  %sx%s", siw.get(), sih.get()));
	}
	
	public void set720()
	{
		GuiModScreen.clicksound();
		setVideoSize(1280, 720, false);
	}
	
	public void set1080()
	{
		GuiModScreen.clicksound();
		setVideoSize(1920, 1080, false);
	}
	
	public void setDefault()
	{
		GuiModScreen.clicksound();
		if( f.getExtendedState() == Frame.MAXIMIZED_BOTH)
		{
			sbm.set(true, ModSettingScreen.guiContext);
		}
		else
		{
			siw.set(getVideoWidth(), ModSettingScreen.guiContext);
			sih.set(getVideoHeight(), ModSettingScreen.guiContext);
			sbm.set(false, ModSettingScreen.guiContext);
		}
		updateDefaultLabel();
	}
	
	private void setVideoSize( int width, int height, boolean maximized )
	{
		if( width == 0 || height == 0)
			return;
    	int iW = f.getInsets().left + f.getInsets().right;
    	int iH = f.getInsets().top + f.getInsets().bottom;
    	int oldW = f.getSize().width;
    	int oldH = f.getSize().height;
    	f.setSize(width + iW, height + iH);
    	if(getVideoWidth() != width || getVideoHeight() != height)
    	{
    		f.setSize(oldW, oldH);
    		return;
    	}
    	f.setLocationRelativeTo(null);
		if(maximized)
		{
			f.setExtendedState(Frame.MAXIMIZED_BOTH);
		}
	}
	
	private int getVideoWidth()
	{
    	int iW = f.getInsets().left + f.getInsets().right;
    	return f.getSize().width - iW;
	}
	
	private int getVideoHeight()
	{
    	int iH = f.getInsets().top + f.getInsets().bottom;
    	return f.getSize().height - iH;
	}
	
}
