package codyhuh.rustling.client.gui;

import codyhuh.rustling.RustlingMod;
import codyhuh.rustling.registry.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class ModGUI extends ForgeGui {

    protected static final ResourceLocation PUMPKIN_BLUR_LOCATION = new ResourceLocation(RustlingMod.MOD_ID, "textures/misc/tetanus_outline.png");

    public ModGUI(Minecraft mc) {
        super(mc);
    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick) {
        this.screenWidth = this.minecraft.getWindow().getGuiScaledWidth();
        this.screenHeight = this.minecraft.getWindow().getGuiScaledHeight();
        RenderSystem.enableBlend();

        var tetanus = this.minecraft.player.getEffect(ModEffects.TETANUS.get());
        if (tetanus != null) {
            this.renderTextureOverlay(guiGraphics, PUMPKIN_BLUR_LOCATION, 1.0F);
        }
    }

}
