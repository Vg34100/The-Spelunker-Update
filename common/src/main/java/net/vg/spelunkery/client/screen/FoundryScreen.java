package net.vg.spelunkery.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.vg.spelunkery.menu.FoundryMenu;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> implements RecipeUpdateListener {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("spelunkery", "textures/gui/foundry.png");

    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();
    private boolean widthTooNarrow;

    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = imageHeight - 94;
        titleLabelY = 10;
    }

    @Override
    protected void init() {
        super.init();
        widthTooNarrow = width < 379;
        recipeBookComponent.init(width, height, minecraft, widthTooNarrow, menu);
        leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
        addRenderableWidget(new ImageButton(
                leftPos + 20,
                height / 2 - 49,
                20,
                18,
                RecipeBookComponent.RECIPE_BUTTON_SPRITES,
                this::toggleRecipeBook
        ));
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    public void containerTick() {
        super.containerTick();
        recipeBookComponent.tick();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (recipeBookComponent.isVisible() && widthTooNarrow) {
            renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
        } else {
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            recipeBookComponent.render(guiGraphics, mouseX, mouseY, partialTick);
            recipeBookComponent.renderGhostRecipe(guiGraphics, leftPos, topPos, true, partialTick);
        }

        renderTooltip(guiGraphics, mouseX, mouseY);
        recipeBookComponent.renderTooltip(guiGraphics, leftPos, topPos, mouseX, mouseY);
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (recipeBookComponent.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }

        if (widthTooNarrow && recipeBookComponent.isVisible()) {
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void slotClicked(Slot slot, int slotId, int mouseButton, ClickType type) {
        super.slotClicked(slot, slotId, mouseButton, type);
        recipeBookComponent.slotClicked(slot);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (recipeBookComponent.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (recipeBookComponent.charTyped(codePoint, modifiers)) {
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int left, int top, int button) {
        boolean outside = mouseX < left || mouseY < top || mouseX >= left + imageWidth || mouseY >= top + imageHeight;
        return outside && recipeBookComponent.hasClickedOutside(mouseX, mouseY, leftPos, topPos, imageWidth, imageHeight, button);
    }

    @Override
    public void recipesUpdated() {
        recipeBookComponent.recipesUpdated();
    }

    @Override
    public RecipeBookComponent getRecipeBookComponent() {
        return recipeBookComponent;
    }

    private void toggleRecipeBook(Button button) {
        recipeBookComponent.toggleVisibility();
        leftPos = recipeBookComponent.updateScreenPosition(width, imageWidth);
        button.setPosition(leftPos + 20, height / 2 - 49);
    }
}
