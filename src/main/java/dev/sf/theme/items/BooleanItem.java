package dev.sf.theme.items;

import dev.sf.theme.NhackPlugin;
import dev.sf.theme.Panel;
import dev.sf.theme.Theme;
import org.lwjgl.glfw.GLFW;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.utils.ColorUtils;

import java.awt.*;

public class BooleanItem extends ExtendableItem {
    public BooleanItem(IFeatureConfigurable module, dev.sf.theme.Panel panel, ExtendableItem parent, BooleanSetting setting) {
        super(parent, module, panel, setting);
        this.panel = panel;
        this.module = module;
        this.setting = setting;
        open = false;
    }
    Panel panel;
    IFeatureConfigurable module;
    BooleanSetting setting;



    @Override
    public boolean isHovered(double mouseX, double mouseY, boolean includeSubItems) {
        return false;
    }


    @Override
    public void render(RenderContext context, double mouseX, double mouseY) {
        super.render(context, mouseX, mouseY);
        IRenderer2D renderer = RusherHackAPI.getRenderer2D();

        // Windows XP style checkbox with gradient
        double checkboxWidth = subItems.isEmpty() ? getWidth() : getWidth() - 14 - 1;
        double checkboxHeight = getHeight();
        
        if (setting.getValue()) {
            // Active state - blue gradient (light blue at top, dark blue at bottom)
            Theme.drawXPGradient(renderer, getX(), getY(), checkboxWidth, checkboxHeight,
                    new Color(100, 150, 255), new Color(50, 100, 200));
        } else {
            // Inactive state - gray gradient (light gray at top, dark gray at bottom)
            Theme.drawXPGradient(renderer, getX(), getY(), checkboxWidth, checkboxHeight,
                    new Color(240, 240, 240), new Color(200, 200, 200));
        }
        
        // Checkbox 3D border
        Theme.drawXPBorder(renderer, getX(), getY(), checkboxWidth, checkboxHeight,
                NhackPlugin.theme.xpHighlight.getValue(),
                NhackPlugin.theme.xpShadow.getValue());

        if(!subItems.isEmpty()) {
            // Windows XP style expand button
            double expandButtonX = getX() + (getWidth() - 14) + 1;
            double expandButtonY = getY();
            double expandButtonSize = 13;
            
            // Expand button gradient
            Theme.drawXPButton(renderer, expandButtonX, expandButtonY, expandButtonSize, checkboxHeight,
                    NhackPlugin.theme.xpButtonTop.getValue(),
                    NhackPlugin.theme.xpButtonBottom.getValue(),
                    open);
            
            // Expand button 3D border
            Theme.drawXPBorder(renderer, expandButtonX, expandButtonY, expandButtonSize, checkboxHeight,
                    NhackPlugin.theme.xpHighlight.getValue(),
                    NhackPlugin.theme.xpShadow.getValue());
        }

        // Windows XP hover effect
        if(panel.isHovering(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())) {
            Theme.drawXPGradient(renderer, getX(), getY(), getWidth(), getHeight(),
                    NhackPlugin.theme.xpButtonHoverTop.getValue(),
                    NhackPlugin.theme.xpButtonHoverBottom.getValue());
        }

      if(subItems.isEmpty())  drawText(setting.getDisplayName());
      else drawTextEx(setting.getDisplayName());
      renderSubItems(context, mouseX, mouseY, subItems, open);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && parent.open && subItems.isEmpty()
                ? panel.isHovering(mouseX, mouseY, getX(), getY(), getWidth(), getHeight())
                : panel.isHovering(mouseX, mouseY, getX(), getY(), getWidth() - 1 - 14, getHeight(false)))
        {
            setting.setValue(!setting.getValue());
            return true;
        }
        if(button == GLFW.GLFW_MOUSE_BUTTON_1 && parent.open && !subItems.isEmpty() && panel.isHovering(mouseX, mouseY, getX() + 1 + (getWidth() - 14) +  1, getY(), 13, getHeight(false))) {
            open = !open;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public double getY() {
        return super.getY();
    }

    @Override
    public double getHeight() {
        return super.getHeight();
    }

}
