package me.olliem5.past.api.module;

import me.olliem5.past.impl.modules.chat.*;
import me.olliem5.past.impl.modules.combat.*;
import me.olliem5.past.impl.modules.core.*;
import me.olliem5.past.impl.modules.exploit.*;
import me.olliem5.past.impl.modules.misc.*;
import me.olliem5.past.impl.modules.movement.*;
import me.olliem5.past.impl.modules.player.*;
import me.olliem5.past.impl.modules.render.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<>();

    public ModuleManager() {
        /* Chat */
        modules.add(new ChatSuffix());
        modules.add(new AutoInsult());
        modules.add(new DurabilityWarn());

        /* Combat */
        modules.add(new AutoTotem());
        modules.add(new BonBedAura());
        modules.add(new AutoCrystal());
        modules.add(new Surround());
        modules.add(new FootEXP());
        modules.add(new Criticals());
        modules.add(new KillAura());
        modules.add(new ChorusSave());
        modules.add(new AutoTrap());
        modules.add(new AutoCreeper());

        /* Core */
        modules.add(new OldClickGUI());
        modules.add(new HUD());
        modules.add(new HUDEditor());
        modules.add(new Render());
        modules.add(new Capes());
        modules.add(new ClickGUI());
        modules.add(new Font());

        /* Exploit */
        modules.add(new Blink());
        modules.add(new PortalGodMode());
        modules.add(new Timer());
        modules.add(new XCarry());
        modules.add(new BowBoost());
        modules.add(new PacketMine());

        /* Misc */
        modules.add(new DiscordRPC());
        modules.add(new EntityAlert());
        modules.add(new FakePlayer());
        modules.add(new AutoBuilder());
        modules.add(new MCF());

        /* Movement */
        modules.add(new Sprint());
        modules.add(new Step());
        modules.add(new Flight());
        modules.add(new Velocity());
        modules.add(new NoSlow());

        /* Player */
        modules.add(new WeaknessAlert());
        modules.add(new AutoLog());
        modules.add(new FastUse());
        modules.add(new NoRotate());
        modules.add(new Burrow());

        /* Render */
        modules.add(new ESP());
        modules.add(new ViewModel());
        modules.add(new SkyColour());
        modules.add(new Fullbright());
        modules.add(new Time());
        modules.add(new HoleESP());
        modules.add(new BlockHighlight());
        modules.add(new NoRender());
        modules.add(new Trajectories());
        modules.add(new HandProgress());
        modules.add(new CrystalCustomize());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}