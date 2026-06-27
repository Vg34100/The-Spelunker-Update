package net.vg.spelunkery.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.vg.spelunkery.menu.FoundryMenu;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> {
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("spelunkery", "textures/gui/foundry.png");

    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title, 176, 166);
    }

    @Override
    protected void init() {
        super.init();
        inventoryLabelY = imageHeight - 94;
        titleLabelY = 10;
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor extractor, int mouseX, int mouseY, float partialTick) {
        int left = leftPos;
        int top = topPos;
        extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left, top, 0.0F, 0.0F, imageWidth, imageHeight, 256, 256);

        int heat = menu.getScaledHeatLevel();
        if (heat > 0) {
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left + 74, top + 55 + (12 - heat), 176.0F, 17.0F + (12 - heat), 14, heat + 2, 256, 256);
        }

        int progress = menu.getScaledProgress();
        if (progress > 0) {
            extractor.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, left + 63, top + 35, 176.0F, 0.0F, progress, 16, 256, 256);
        }

        extractor.text(font, Component.literal("Lava: " + menu.getLavaLevel()), left + 60, top + 56, 0x3b3024, false);
    }
}
