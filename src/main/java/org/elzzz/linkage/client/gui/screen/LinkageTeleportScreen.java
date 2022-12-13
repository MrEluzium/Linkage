package org.elzzz.linkage.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.elzzz.linkage.client.KeyInputHandler;
import org.elzzz.linkage.client.gui.widget.LinkageTeleportScreenWidget;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@Environment(EnvType.CLIENT)
public class LinkageTeleportScreen extends Screen {
    public static final Identifier SOCIAL_INTERACTIONS_TEXTURE = new Identifier("textures/gui/social_interactions.png");
    private static final Text NO_AVAILABLE_PLAYERS_TEXT;
    private static final Text SEARCH_TEXT;
    static final Text EMPTY_SEARCH_TEXT;
    private String currentSearch = "";
    private final boolean show_single_dimension;
    private boolean is_initialized;
    LinkageTeleportScreenWidget playerList;
    TextFieldWidget searchBox;
    @Nullable
    private Runnable onRendered;

    public LinkageTeleportScreen(boolean show_single_dimension) {
        super(Text.translatable("linkage.gui.teleportation_screen.title"));

        this.show_single_dimension = show_single_dimension;
    }

    public void tick() {
        super.tick();
        this.searchBox.tick();
    }

    protected void init() {

        if (this.is_initialized) {
            this.playerList.updateSize(this.width, this.height, 88, this.getPlayerListBottom());
        } else {
            this.playerList = new LinkageTeleportScreenWidget(this, this.client, this.width, this.height, 88, this.getPlayerListBottom(), 36);
        }

        String string = this.searchBox != null ? this.searchBox.getText() : "";
        this.searchBox = new TextFieldWidget(this.textRenderer, this.getSearchBoxX() + 28, 78, 196, 16, SEARCH_TEXT) {
            protected MutableText getNarrationMessage() {
                return !LinkageTeleportScreen.this.searchBox.getText().isEmpty() &&
                        LinkageTeleportScreen.this.playerList.isEmpty() ?
                        super.getNarrationMessage().append(", ").append(LinkageTeleportScreen.EMPTY_SEARCH_TEXT) :
                        super.getNarrationMessage();
            }
        };

        this.searchBox.setMaxLength(16);
        this.searchBox.setDrawsBackground(false);
        this.searchBox.setVisible(true);
        this.searchBox.setEditableColor(16777215);
        this.searchBox.setText(string);
        this.searchBox.setChangedListener(this::onSearchChange);
        this.addSelectableChild(this.searchBox);
        this.addSelectableChild(this.playerList);

        this.playerList.refresh();
        this.is_initialized = true;
    }

    public void renderBackground(MatrixStack matrices) {
        int i = this.getSearchBoxX() + 3;
        super.renderBackground(matrices);
        RenderSystem.setShaderTexture(0, SOCIAL_INTERACTIONS_TEXTURE);
        this.drawTexture(matrices, i, 64, 1, 1, 236, 8);
        int j = this.getRowCount();

        for(int k = 0; k < j; ++k) {
            this.drawTexture(matrices, i, 72 + 16 * k, 1, 10, 236, 16);
        }

        this.drawTexture(matrices, i, 72 + 16 * j, 1, 27, 236, 8);
        this.drawTexture(matrices, i + 10, 76, 243, 1, 12, 12);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);

        if (!this.playerList.isEmpty()) {
            this.playerList.render(matrices, mouseX, mouseY, delta);
        } else if (this.searchBox.getText().isEmpty()) {
            drawCenteredText(matrices, this.client.textRenderer, NO_AVAILABLE_PLAYERS_TEXT, this.width / 2, (78 + this.getPlayerListBottom()) / 2, -1);
        } else {
            drawCenteredText(matrices, this.client.textRenderer, EMPTY_SEARCH_TEXT, this.width / 2, (78 + this.getPlayerListBottom()) / 2, -1);
        }

        if (!this.searchBox.isFocused() && this.searchBox.getText().isEmpty()) {
            drawTextWithShadow(matrices, this.client.textRenderer, SEARCH_TEXT, this.searchBox.getX(), this.searchBox.getY(), -1);
        } else {
            this.searchBox.render(matrices, mouseX, mouseY, delta);
        }

        if (this.onRendered != null) { this.onRendered.run(); }
        super.render(matrices, mouseX, mouseY, delta);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.searchBox.isFocused()) {
            this.searchBox.mouseClicked(mouseX, mouseY, button);
        }

        return super.mouseClicked(mouseX, mouseY, button) || this.playerList.mouseClicked(mouseX, mouseY, button);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.searchBox.isFocused() && KeyInputHandler.openTeleportationScreenKey.matchesKey(keyCode, scanCode)) {
            this.client.setScreen(null);
            return true;
        } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }
    }

    private void onSearchChange(String currentSearch) {
        currentSearch = currentSearch.toLowerCase(Locale.ROOT);
        if (!currentSearch.equals(this.currentSearch)) {
            this.playerList.setCurrentSearch(currentSearch);
            this.currentSearch = currentSearch;
            this.playerList.refresh();
        }

    }

    public void refresh() { this.playerList.refresh(); }

    private int getSearchBoxX() { return (this.width - 238) / 2; }

    private int getScreenHeight() { return Math.max(52, this.height - 128 - 16); }

    private int getRowCount() { return this.getScreenHeight() / 16; }

    private int getPlayerListBottom() { return 80 + this.getRowCount() * 16 - 8; }

    public boolean isLocal() { return this.show_single_dimension; }

    @Override
    public boolean shouldPause() { return false; }

    public void setOnRendered(@Nullable Runnable onRendered) {
        this.onRendered = onRendered;
    }

    static {
        NO_AVAILABLE_PLAYERS_TEXT = Text.translatable("linkage.gui.teleportation_screen.no_available_players").formatted(Formatting.GRAY);
        EMPTY_SEARCH_TEXT = Text.translatable("gui.socialInteractions.search_empty").formatted(Formatting.GRAY);
        SEARCH_TEXT = Text.translatable("gui.socialInteractions.search_hint").formatted(Formatting.ITALIC).formatted(Formatting.GRAY);
    }
}
