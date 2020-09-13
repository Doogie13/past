package me.ollieobama.past;

import de.Hero.clickgui.ClickGUI;
import me.ollieobama.past.gui.ingame.PastHUD;
import me.ollieobama.past.module.Module;
import me.ollieobama.past.settings.SettingsManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.ollieobama.past.module.ModuleManager;
import me.ollieobama.past.command.CommandManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(
        name = Past.name,
        modid = Past.modid,
        version = Past.version
)

public class Past {
    public static final String name = "Past Utility Mod";
    public static final String modid = "past";
    public static final String version = "0.1";
    public static final String appid = "754509326902886411";
    public static String prefix = ".";
    public static String nameversion = name + " " + version;

    public static ModuleManager moduleManager;
    public static SettingsManager settingsManager;
    public static ClickGUI clickGUI;
    public static PastHUD pastHUD;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) { Display.setTitle(nameversion); }

    /* Initializing client */
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        settingsManager = new SettingsManager();
        moduleManager = new ModuleManager();
        clickGUI = new ClickGUI();
        pastHUD = new PastHUD();
        CommandManager.init();
        MinecraftForge.EVENT_BUS.register(new CommandManager());
        MinecraftForge.EVENT_BUS.register(this);
    }

    /* Instance shit */
    @Mod.Instance
    private static Past INSTANCE;
    public Past() { INSTANCE = this; }
    public static Past getInstance() { return INSTANCE; }

    /* Toggling modules on key press */
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        for (Module m : moduleManager.getModules()) {
            if (Keyboard.isKeyDown(m.getKey())) {
                m.toggle();
            }
        }
    }
}
