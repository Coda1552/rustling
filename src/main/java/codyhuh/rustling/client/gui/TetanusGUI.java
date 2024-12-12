package codyhuh.rustling.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class TetanusGUI implements IGuiOverlay {

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {

        Minecraft minecraft = Minecraft.getInstance();
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        ModGUI fakeGui = new ModGUI(minecraft);

        fakeGui.render(guiGraphics, partialTick);
    }
}
