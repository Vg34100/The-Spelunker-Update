package net.vg.spelunkery.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.vg.spelunkery.menu.FoundryMenu;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("spelunkery", "textures/gui/foundry.png");

    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = imageHeight - 94;
        titleLabelX = 8;
        titleLabelY = 10;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int left = leftPos;
        int top = topPos;
        guiGraphics.blit(TEXTURE, left, top, 0, 0, imageWidth, imageHeight);

        int heat = menu.getScaledHeatLevel();
        if (heat > 0) {
            guiGraphics.blit(TEXTURE, left + 74, top + 55 + (12 - heat), 176, 17 + (12 - heat), 14, heat + 2);
        }

        int progress = menu.getScaledProgress();
        if (progress > 0) {
            guiGraphics.blit(TEXTURE, left + 63, top + 35, 176, 0, progress, 16);
        }
        guiGraphics.drawString(font, Component.literal("Lava: " + menu.getLavaLevel()), left + 60, top + 56, 0x3b3024, false);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
