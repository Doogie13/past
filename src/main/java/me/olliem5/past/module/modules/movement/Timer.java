package me.olliem5.past.module.modules.movement;

import me.olliem5.past.Past;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import me.olliem5.past.settings.Setting;
import me.olliem5.past.util.ColourUtil;

public class Timer extends Module {
    public Timer() {
        super("Timer", "Changes your tick speed", Category.MOVEMENT);
    }

    Setting speed;

    @Override
    public void setup() {
        Past.settingsManager.registerSetting(speed = new Setting("Speed", "TimerSpeed", 1, 3, 20, this));
    }

    public void onUpdate() {
        mc.timer.tickLength = 50f / speed.getValueInt();
    }

    @Override
    public void onDisable() {
        mc.timer.tickLength = 50f;
    }

    public String getArraylistInfo() {
        return ColourUtil.gray + " " + speed.getValueInt();
    }
}
