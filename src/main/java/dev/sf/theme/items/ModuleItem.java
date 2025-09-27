package dev.sf.theme.items;

import dev.sf.theme.NhackPlugin;
import dev.sf.theme.Panel;
import dev.sf.theme.Theme;
import org.lwjgl.glfw.GLFW;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.RenderContext;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.setting.BindSetting;
import org.rusherhack.core.feature.IFeatureConfigurable;
import org.rusherhack.core.interfaces.IToggleable;

import java.awt.*;


public class ModuleItem extends ExtendableItem {
    Panel panel;
    private double rendererHeight;

    public ModuleItem(IFeatureConfigurable module, Panel panel){
        super(null, module, panel, null);
        this.panel = panel;

        if(module instanceof ToggleableModule) {
            addSubItem(new BindItem(this, module, panel, new BindSetting("Bind", RusherHackAPI.getBindManager().getBind((ToggleableModule) module)), true));
        }

        addSettingItems(module.getSettings());

        rendererHeight = 14F;
    }
    @Override
    public double getX() {
        return panel.getX() + 1;
    }
    @Override
    public double getWidth() {
        return panel.getWidth() - 2;
    }

    @Override
    public double getHeight() {
        return rendererHeight;
    }

    @Override
    public double getHeight(boolean total) {
        if(total) return rendererHeight;
        return 14;
    }

    @Override
    public boolean isHovered(double mouseX, double mouseY, boolean includeSubItems) {
        return false;
    }

    @Override
    public void render(RenderContext context, double mouseX, double mouseY) {
        super.render(context, mouseX, mouseY);
        final IRenderer2D renderer = RusherHackAPI.getRenderer2D();
        final IFontRenderer fontRenderer = getFontRenderer();
        boolean isToggled = !(module instanceof IToggleable) || ((IToggleable) module).isToggled();
        

        // Windows XP style module button with gradient
        double buttonWidth = getWidth() - 1 - 16;
        double buttonHeight = getHeight(false);
        
        if (isToggled) {
            // Active state - blue gradient (light blue at top, dark blue at bottom)
            Theme.drawXPGradient(renderer, getX() + 1, getY(), buttonWidth, buttonHeight,
                    new Color(100, 150, 255), new Color(50, 100, 200));
        } else {
            // Inactive state - gray gradient (light gray at top, dark gray at bottom)
            Theme.drawXPGradient(renderer, getX() + 1, getY(), buttonWidth, buttonHeight,
                    new Color(240, 240, 240), new Color(200, 200, 200));
        }

        // Module button 3D border
        Theme.drawXPBorder(renderer, getX() + 1, getY(), buttonWidth, buttonHeight,
                NhackPlugin.theme.xpHighlight.getValue(),
                NhackPlugin.theme.xpShadow.getValue());

        // Windows XP style expand button
        if (!subItems.isEmpty()) {
            double expandButtonX = getX() + 1 + (getWidth() - 16) + 1;
            double expandButtonY = getY();
            double expandButtonSize = 13;
            
            // Expand button gradient
            Theme.drawXPButton(renderer, expandButtonX, expandButtonY, expandButtonSize, buttonHeight,
                    NhackPlugin.theme.xpButtonTop.getValue(),
                    NhackPlugin.theme.xpButtonBottom.getValue(),
                    open);

            // Expand button 3D border
            Theme.drawXPBorder(renderer, expandButtonX, expandButtonY, expandButtonSize, buttonHeight,
                    NhackPlugin.theme.xpHighlight.getValue(),
                    NhackPlugin.theme.xpShadow.getValue());

            // Windows XP style arrow for expand button
            String arrow = open ? "▲" : "▼";
            int arrowColor = open ? NhackPlugin.theme.xpTextInverted.getValueRGB() : NhackPlugin.theme.xpText.getValueRGB();
            fontRenderer.drawString(arrow, expandButtonX + 4, expandButtonY + buttonHeight / 2 - fontRenderer.getFontHeight() / 2 + 1, arrowColor);
        }


//        if(module instanceof ToggleableModule){
//            if(((ToggleableModule) module).isToggled())
//                renderer.drawOutlinedRectangle(
//                        getX(),
//                        getY() - 1,
//                        getWidth(),
//                        getHeight(false) + 1,
//                        ExamplePlugin.theme.outlineWidth.getValue(),
//                        ExamplePlugin.theme.getColorSetting().getValue().getRGB(),
//                        ExamplePlugin.theme.moduleOutlineColor.getValueRGB()
//                );
//        }
//        else{
//            renderer.drawOutlinedRectangle(
//                    getX(),
//                    getY() - 1,
//                    getWidth(),
//                    getHeight(false) + 1,
//                    ExamplePlugin.theme.outlineWidth.getValue(),
//                    ExamplePlugin.theme.getColorSetting().getValue().getRGB(),
//                    ExamplePlugin.theme.moduleOutlineColor.getValueRGB()
//            );
//        }

        // Settings outline removed to eliminate black box
        
        // Always use white text
        fontRenderer.drawString(module.getName(),
                getX() + buttonWidth / 2 - fontRenderer.getStringWidth(module.getName()) / 2,
                getY() + buttonHeight / 2 - fontRenderer.getFontHeight() / 2 + 1,
                Color.WHITE.getRGB());
        
        renderSubItems(context, mouseX, mouseY, subItems, open);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && panel.isHovering(mouseX, mouseY, getX(), getY(), getWidth() - 1 - 16, getHeight(false))) {
            if(module instanceof IToggleable){
                ((IToggleable) module).toggle();
            }
            return true;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_1 && panel.isHovering(mouseX, mouseY, getX() + 1 + (getWidth() - 16) +  1, getY(), 13, getHeight(false))) {
            this.open = !this.open;
            return true;
        }
        
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean charTyped(char character) {
        return super.charTyped(character);
    }

    @Override
    public boolean keyTyped(int key, int scanCode, int modifiers) {
        return super.keyTyped(key, scanCode, modifiers);
    }
    protected void possibleHeightUpdate() {
        double temp = 14f;
        if (open)
            temp += subItems.stream().mapToDouble(i -> i.setting.isHidden() ? 0 : (i.getHeight(true) + 3)).sum();
        rendererHeight = temp;
    }

}
