package me.olliem5.past.gui.click;

import me.olliem5.past.Past;
import me.olliem5.past.gui.click.elements.ModuleButton;
import me.olliem5.past.module.Category;
import me.olliem5.past.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.ArrayList;

public class Panel {
    protected Minecraft mc = Minecraft.getMinecraft();

    public ArrayList<Component> components;
    public String title;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean isDragging;
    private boolean open;
    public int dragX;
    public int dragY;
    Category cat;

    public Panel(String title, int x, int y, int width, int height, Category cat) {
        this.components = new ArrayList<>();
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragX = 0;
        this.isDragging = false;
        this.open = true;
        this.cat = cat;
        int tY = this.height;

        for (Module mod : Past.moduleManager.getModules()) {
            if (mod.getCategory().equals(cat)) {
                ModuleButton modButton = new ModuleButton(mod, this, tY);
                this.components.add(modButton);
                tY += 12;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) { //Initial header of module category.
        Gui.drawRect(x, y, x + width, y + height, 0xFF222222); //TODO: Make red!
        mc.fontRenderer.drawStringWithShadow(title, x + 2, y + height / 2 - mc.fontRenderer.FONT_HEIGHT / 2, -1);

        if (this.open) { //Rendering components when the GUI is opened.
            if (!this.components.isEmpty()) {
                for (Component component : components) {
                    component.renderComponent();
                }
            }
        }
    }

    public boolean isWithinHeader(int x, int y) {
        if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
            return true;
        } else {
            return false;
        }
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - dragX);
            this.setY(mouseY - dragY);
        }
    }

    public ArrayList<Component> getComponents() { return components; }

    public int getWidth() { return width; }

    public void setDragging(boolean drag) { this.isDragging = drag; }

    public boolean isOpen() { return open; }
    public void setOpen(boolean open) { this.open = open; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int newX) { this.x = newX; }
    public void setY(int newY) { this.y = newY; }
}
