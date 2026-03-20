package net.vg.spelunkery.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.vg.spelunkery.menu.FoundryMenu;
import net.vg.spelunkery.recipe.FoundryIngredient;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("spelunkery", "textures/gui/foundry.png");
    private static final int PANEL_WIDTH = 108;
    private static final int PANEL_PADDING = 6;
    private static final int ENTRY_HEIGHT = 22;

    private Button recipeButton;
    private boolean recipesVisible;
    private List<RecipeHolder<FoundryRecipe>> recipes = List.of();

    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = imageHeight - 94;
        titleLabelX = 8;
        titleLabelY = 10;
    }

    @Override
    protected void init() {
        super.init();
        recipeButton = addRenderableWidget(Button.builder(Component.literal("Recipes"), button -> recipesVisible = !recipesVisible)
                .bounds(leftPos + imageWidth - 58, topPos + 4, 54, 16)
                .build());
        recipes = loadRecipes();
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

        if (recipesVisible) {
            renderRecipePanel(guiGraphics, mouseX, mouseY);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
        if (recipesVisible) {
            renderRecipeTooltips(guiGraphics, mouseX, mouseY);
        }
    }

    private List<RecipeHolder<FoundryRecipe>> loadRecipes() {
        if (minecraft == null || minecraft.level == null) {
            return List.of();
        }

        List<RecipeHolder<FoundryRecipe>> loaded = new ArrayList<>(minecraft.level.getRecipeManager().getAllRecipesFor(SpelunkeryRecipeTypes.FOUNDRY_TYPE.get()));
        loaded.sort(Comparator.comparing(holder -> holder.value().getResultItem(minecraft.level.registryAccess()).getHoverName().getString()));
        return loaded;
    }

    private void renderRecipePanel(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int panelX = leftPos - PANEL_WIDTH - 8;
        int panelY = topPos;
        int panelHeight = 24 + recipes.size() * ENTRY_HEIGHT + PANEL_PADDING;

        guiGraphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + panelHeight, 0xF01B1610);
        guiGraphics.fill(panelX, panelY, panelX + PANEL_WIDTH, panelY + 18, 0xF03A2C1F);
        guiGraphics.drawString(font, Component.literal("Foundry Recipes"), panelX + PANEL_PADDING, panelY + 5, 0xF6E7C9, false);

        int y = panelY + 22;
        for (RecipeHolder<FoundryRecipe> holder : recipes) {
            renderRecipeEntry(guiGraphics, holder.value(), panelX + PANEL_PADDING, y);
            y += ENTRY_HEIGHT;
        }
    }

    private void renderRecipeEntry(GuiGraphics guiGraphics, FoundryRecipe recipe, int x, int y) {
        guiGraphics.fill(x - 2, y - 2, x + PANEL_WIDTH - PANEL_PADDING * 2, y + 18, 0x602A2118);

        int inputX = x;
        for (FoundryIngredient ingredient : recipe.ingredients()) {
            ItemStack display = ingredient.ingredient().getItems().length > 0 ? ingredient.ingredient().getItems()[0] : ItemStack.EMPTY;
            if (!display.isEmpty()) {
                guiGraphics.renderItem(display, inputX, y);
                if (ingredient.count() > 1) {
                    guiGraphics.renderItemDecorations(font, new ItemStack(display.getItem(), ingredient.count()), inputX, y);
                }
            }
            inputX += 18;
        }

        guiGraphics.drawString(font, Component.literal("->"), x + 56, y + 4, 0xD8C69B, false);
        guiGraphics.renderItem(recipe.result(), x + 72, y);
        guiGraphics.renderItemDecorations(font, recipe.result(), x + 72, y);
    }

    private void renderRecipeTooltips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int panelX = leftPos - PANEL_WIDTH - 8 + PANEL_PADDING;
        int y = topPos + 22;
        for (RecipeHolder<FoundryRecipe> holder : recipes) {
            FoundryRecipe recipe = holder.value();
            int inputX = panelX;
            for (FoundryIngredient ingredient : recipe.ingredients()) {
                ItemStack display = ingredient.ingredient().getItems().length > 0 ? ingredient.ingredient().getItems()[0] : ItemStack.EMPTY;
                if (!display.isEmpty() && isHoveringRect(inputX, y, 16, 16, mouseX, mouseY)) {
                    guiGraphics.renderTooltip(font, display, mouseX, mouseY);
                    return;
                }
                inputX += 18;
            }

            if (isHoveringRect(panelX + 72, y, 16, 16, mouseX, mouseY)) {
                guiGraphics.renderTooltip(font, recipe.result(), mouseX, mouseY);
                return;
            }
            y += ENTRY_HEIGHT;
        }
    }

    private boolean isHoveringRect(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
