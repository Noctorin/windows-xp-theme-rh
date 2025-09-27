package dev.sf.theme;

import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.Nullable;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.feature.hud.HudElement;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.ui.hud.HudHandlerBase;
import org.rusherhack.client.api.ui.panel.PanelHandlerBase;
import org.rusherhack.client.api.ui.theme.ThemeBase;
import org.rusherhack.client.api.ui.window.WindowHandlerBase;
import org.rusherhack.core.logging.ILogger;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NumberSetting;

import java.awt.*;
import java.util.List;

public class Theme extends ThemeBase {
    public ILogger logger;

    public NumberSetting<Integer> alpha = new NumberSetting<>("Alpha", 255, 0, 255);
    public ColorSetting fontColor = new ColorSetting("FontColor", new Color(0, 0, 0));
    public BooleanSetting forceVanilla = new BooleanSetting("ForceVanilla", false);
    public ColorSetting categoryColor = new ColorSetting("CategoryColor", new Color(255, 255, 255, 255));
    public ColorSetting buttonMColor = new ColorSetting("MButtonColor", new Color(49, 106, 197, 255));
    public ColorSetting buttonCColor = new ColorSetting("CButtonColor", new Color(49, 106, 197, 255));
    public ColorSetting panelColor = new ColorSetting("PanelColor", new Color(255, 255, 255, 255));

    public BooleanSetting settingsOutline = new BooleanSetting("SettingsOutline", true);
    public NumberSetting<Float> outlineWidth = new NumberSetting<>("OutlineWidth", 1F, 0.1F, 5F);
    public ColorSetting outlineColor = new ColorSetting("OutlineColor", new Color(200, 200, 200));

    public ColorSetting backgroundColor = new ColorSetting("BackgroundColor", new Color(0, 0, 0, 30));
    
    // Windows XP Classic Color Palette
    public ColorSetting xpTitleBarTop = new ColorSetting("XPTitleBarTop", new Color(16, 52, 166)); // Dark blue top
    public ColorSetting xpTitleBarBottom = new ColorSetting("XPTitleBarBottom", new Color(24, 132, 217)); // Light blue bottom
    public ColorSetting xpPanelTop = new ColorSetting("XPPanelTop", new Color(236, 233, 216)); // Light beige top
    public ColorSetting xpPanelBottom = new ColorSetting("XPPanelBottom", new Color(212, 208, 200)); // Darker beige bottom
    public ColorSetting xpButtonTop = new ColorSetting("XPButtonTop", new Color(240, 240, 240)); // Light gray for inactive
    public ColorSetting xpButtonBottom = new ColorSetting("XPButtonBottom", new Color(200, 200, 200)); // Darker gray for inactive
    public ColorSetting xpButtonPressedTop = new ColorSetting("XPButtonPressedTop", new Color(70, 130, 220)); // Light blue for active
    public ColorSetting xpButtonPressedBottom = new ColorSetting("XPButtonPressedBottom", new Color(40, 90, 180)); // Darker blue for active
    public ColorSetting xpButtonHoverTop = new ColorSetting("XPButtonHoverTop", new Color(255, 255, 255)); // Hover light
    public ColorSetting xpButtonHoverBottom = new ColorSetting("XPButtonHoverBottom", new Color(240, 240, 240)); // Hover dark
    public ColorSetting xpText = new ColorSetting("XPText", new Color(255, 255, 255)); // White text everywhere
    public ColorSetting xpTextInverted = new ColorSetting("XPTextInverted", new Color(255, 255, 255)); // White text everywhere
    public ColorSetting xpBorder = new ColorSetting("XPBorder", new Color(128, 128, 128)); // Gray border
    public ColorSetting xpShadow = new ColorSetting("XPShadow", new Color(128, 128, 128, 128)); // Shadow
    public ColorSetting xpHighlight = new ColorSetting("XPHighlight", new Color(255, 255, 255, 128)); // Highlight

    public NumberSetting<Float> scrollSpeed = new NumberSetting<>("ScrollSpeed", 15F, 1F, 20F);

    public NumberSetting<Float> x = new NumberSetting<>("X", 0F, -200F, 200F);
    public NumberSetting<Float> y = new NumberSetting<>("Y", 0F, -200F, 200F);
    public NumberSetting<Float> x1 = new NumberSetting<>("X1", 0F, -200F, 200F);
    public NumberSetting<Float> y1 = new NumberSetting<>("Y1", 0F, -200F, 200F);
    public NumberSetting<Float> x2 = new NumberSetting<>("X2", 0F, -200F, 200F);
    public NumberSetting<Float> y2 = new NumberSetting<>("Y2", 0F, -200F, 200F);


    public Theme(String name, String description, Color defaultColor) {
        super(name, description, defaultColor);
        logger = RusherHackAPI.createLogger("nhack_theme");
        // TODO: maybe add more settings later idk
        registerSettings(
                alpha,

                fontColor,
                forceVanilla,

                categoryColor,
                buttonCColor,
                buttonMColor,
                panelColor,

                outlineWidth,
                settingsOutline,
                outlineColor,

                backgroundColor,
                
                xpTitleBarTop,
                xpTitleBarBottom,
                xpPanelTop,
                xpPanelBottom,
                xpButtonTop,
                xpButtonBottom,
                xpButtonPressedTop,
                xpButtonPressedBottom,
                xpButtonHoverTop,
                xpButtonHoverBottom,
                xpText,
                xpTextInverted,
                xpBorder,
                xpShadow,
                xpHighlight,

                scrollSpeed,
                x,
                y,
                x1,
                y1,
                x2,
                y2
        );

        List<HudElement> elements = ImmutableList.copyOf(RusherHackAPI.getHudManager().getFeatures());
        for (HudElement element : elements) {
            registerIsOpen(element);
            registerShouldRender(element);
        }
    }


    public void registerIsOpen(HudElement element) {
        registerSettings(new BooleanSetting(element.getName().toLowerCase() + "#is_open", false).setHidden(true).setVisibility(() -> false));
    }

    public void registerShouldRender(HudElement element) {
        registerSettings(new BooleanSetting(element.getName().toLowerCase() + "#should_render", false).setHidden(true).setVisibility(() -> false));
    }

    @Override
    public @Nullable PanelHandlerBase<?> getClickGuiHandler() {
        return NhackPlugin.guiHandler;
    }

    @Override
    public @Nullable HudHandlerBase getHudHandler() {
        return NhackPlugin.hudHandler;
    }

	@Override
	public @Nullable WindowHandlerBase getWindowHandler() {
		return null;
	}






    public static int changeAlpha(int color, int alpha) {
        Color c = new Color(color);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }
    
    // Windows XP gradient drawing utility
    public static void drawXPGradient(org.rusherhack.client.api.render.IRenderer2D renderer, double x, double y, double width, double height, Color topColor, Color bottomColor) {
        for (int i = 0; i < height; i++) {
            float ratio = (float) i / (float) height;
            // Interpolate from topColor to bottomColor
            int r = (int) (topColor.getRed() + ratio * (bottomColor.getRed() - topColor.getRed()));
            int g = (int) (topColor.getGreen() + ratio * (bottomColor.getGreen() - topColor.getGreen()));
            int b = (int) (topColor.getBlue() + ratio * (bottomColor.getBlue() - topColor.getBlue()));
            int a = (int) (topColor.getAlpha() + ratio * (bottomColor.getAlpha() - topColor.getAlpha()));
            renderer.drawRectangle(x, y + i, width, 1, new Color(r, g, b, a).getRGB());
        }
    }

    // Windows XP button gradient
    public static void drawXPButton(org.rusherhack.client.api.render.IRenderer2D renderer, double x, double y, double width, double height, Color topColor, Color bottomColor, boolean pressed) {
        if (pressed) {
            // Pressed button - reverse gradient
            drawXPGradient(renderer, x, y, width, height, bottomColor, topColor);
        } else {
            // Normal button
            drawXPGradient(renderer, x, y, width, height, topColor, bottomColor);
        }
    }

    // Windows XP 3D border effect
    public static void drawXPBorder(org.rusherhack.client.api.render.IRenderer2D renderer, double x, double y, double width, double height, Color lightColor, Color darkColor) {
        // Top and left - light
        renderer.drawRectangle(x, y, width, 1, lightColor.getRGB());
        renderer.drawRectangle(x, y, 1, height, lightColor.getRGB());
        // Bottom and right - dark
        renderer.drawRectangle(x, y + height - 1, width, 1, darkColor.getRGB());
        renderer.drawRectangle(x + width - 1, y, 1, height, darkColor.getRGB());
    }

    // Windows XP rounded rectangle with gradient (top corners only) using circles and rectangle
    public static void drawXPRoundedGradient(org.rusherhack.client.api.render.IRenderer2D renderer, double x, double y, double width, double height, Color topColor, Color bottomColor, double radius) {
        // Draw the main gradient rectangle (covers most of the area)
        drawXPGradient(renderer, x + radius, y, width - 2 * radius, height, topColor, bottomColor);
        
        // Draw the left rounded corner using a circle
        drawXPGradientCircle(renderer, x + radius, y + radius, radius, topColor, bottomColor, true, true);
        
        // Draw the right rounded corner using a circle
        drawXPGradientCircle(renderer, x + width - radius, y + radius, radius, topColor, bottomColor, false, true);
        
        // Fill any gaps with small rectangles
        drawXPGradient(renderer, x, y + radius, radius, height - radius, topColor, bottomColor);
        drawXPGradient(renderer, x + width - radius, y + radius, radius, height - radius, topColor, bottomColor);
    }
    
    // Helper method to draw a solid color circle for rounded corners
    private static void drawXPGradientCircle(org.rusherhack.client.api.render.IRenderer2D renderer, double centerX, double centerY, double radius, Color topColor, Color bottomColor, boolean leftCorner, boolean topCorner) {
        int radiusInt = (int) radius;
        
        for (int i = 0; i <= radiusInt; i++) {
            for (int j = 0; j <= radiusInt; j++) {
                double distance = Math.sqrt(i * i + j * j);
                
                if (distance <= radius) {
                    // Calculate the position relative to the corner
                    double pixelX = leftCorner ? (centerX - i) : (centerX + i);
                    double pixelY = topCorner ? (centerY - j) : (centerY + j);
                    
                    // Use solid top color for the entire circle to match the gradient start
                    renderer.drawRectangle(pixelX, pixelY, 1, 1, topColor.getRGB());
                }
            }
        }
    }

}
