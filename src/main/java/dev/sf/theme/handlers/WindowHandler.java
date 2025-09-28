package dev.sf.theme.handlers;

import org.rusherhack.client.api.ui.window.WindowHandlerBase;
import org.rusherhack.client.api.ui.window.WindowContentHandlerBase;
import org.rusherhack.client.api.ui.window.WindowViewHandlerBase;
import org.rusherhack.client.api.feature.window.Window;

public class WindowHandler extends WindowHandlerBase {
    
    public WindowHandler() {
        super();
    }

    @Override
    public WindowContentHandlerBase getContentHandler() {
        return null;
    }

    @Override
    public WindowViewHandlerBase getViewHandler() {
        return null;
    }

    @Override
    public int getFramePadding(WindowHandlerBase.WindowSide side) {
        return 0;
    }

    @Override
    public void renderWindowFrame(Window window, double mouseX, double mouseY) {
        // Empty - no custom window frame rendering
    }
}
