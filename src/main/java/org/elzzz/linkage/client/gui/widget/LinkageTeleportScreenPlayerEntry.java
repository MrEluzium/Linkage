package org.elzzz.linkage.client.gui.widget;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.elzzz.linkage.client.gui.screen.LinkageTeleportScreen;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class LinkageTeleportScreenPlayerEntry extends ElementListWidget.Entry<LinkageTeleportScreenPlayerEntry> {
    private static final Identifier REPORT_BUTTON_TEXTURE = new Identifier("textures/gui/report_button.png");
    private final MinecraftClient client;
    private final UUID uuid;
    private final String name;
    private final String dimension;
    private final Supplier<Identifier> skinTexture;

    private final ButtonWidget teleportButton;
    final List<OrderedText> tooltip;
    float timeCounter;
    public static final int BLACK_COLOR;
    public static final int GRAY_COLOR;
    public static final int DARK_GRAY_COLOR;
    public static final int WHITE_COLOR;
    public static final int LIGHT_GRAY_COLOR;

    public LinkageTeleportScreenPlayerEntry(final MinecraftClient client, final LinkageTeleportScreen parent,
                                            UUID uuid, String name, String dimension, Supplier<Identifier> skinTexture) {
        this.client = client;
        this.uuid = uuid;
        this.name = name;
        this.dimension = dimension;
        this.skinTexture = skinTexture;

        this.tooltip = client.textRenderer.wrapLines(Text.translatable("linkage.gui.teleportation_screen.tooltip", this.name), 150);

        this.teleportButton = new TexturedButtonWidget(0, 0, 20, 20, 0, 0, 20, REPORT_BUTTON_TEXTURE, 64, 64, (button) -> {
            this.client.setScreen((Screen)null);
        }, new ButtonWidget.TooltipSupplier() {
            public void onTooltip(ButtonWidget buttonWidget, MatrixStack matrixStack, int i, int j) {
                LinkageTeleportScreenPlayerEntry entry = LinkageTeleportScreenPlayerEntry.this;
                entry.timeCounter += client.getLastFrameDuration();
                if (LinkageTeleportScreenPlayerEntry.this.timeCounter >= 10.0F) {
                    parent.setOnRendered(() -> {
                        LinkageTeleportScreenPlayerEntry.renderTooltip(parent, matrixStack, LinkageTeleportScreenPlayerEntry.this.tooltip, i, j);
                    });
                }
            }
        }, Text.literal("teleport button")) {
            protected MutableText getNarrationMessage() {
                return LinkageTeleportScreenPlayerEntry.this.getNarrationMessage();
            }
        };
    }

    public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int i = x + 4;
        int j = y + (entryHeight - 24) / 2;
        int k = i + 24 + 4;
        int l;

        if (dimension.isEmpty()) {
            DrawableHelper.fill(matrices, x, y, x + entryWidth, y + entryHeight, GRAY_COLOR);
            Objects.requireNonNull(this.client.textRenderer);
            l = y + (entryHeight - 9) / 2;
        } else {
            DrawableHelper.fill(matrices, x, y, x + entryWidth, y + entryHeight, DARK_GRAY_COLOR);
            Objects.requireNonNull(this.client.textRenderer);
            Objects.requireNonNull(this.client.textRenderer);
            l = y + (entryHeight - (9 + 9)) / 2;
            this.client.textRenderer.draw(matrices, dimension, (float)k, (float)(l + 12), LIGHT_GRAY_COLOR);
        }

        RenderSystem.setShaderTexture(0, (Identifier)this.skinTexture.get());
        PlayerSkinDrawer.draw(matrices, i, j, 24);
        this.client.textRenderer.draw(matrices, this.name, (float)k, (float)l, WHITE_COLOR);

        if (this.teleportButton != null) {
            float f = this.timeCounter;
            this.teleportButton.x = x + (entryWidth - this.teleportButton.getWidth() - 4);
            this.teleportButton.y = y + (entryHeight - this.teleportButton.getHeight()) / 2;
            this.teleportButton.render(matrices, mouseX, mouseY, tickDelta);
            if (f == this.timeCounter) {
                this.timeCounter = 0.0F;
            }
        }

    }

    MutableText getNarrationMessage() {
        return Text.translatable("linkage.gui.teleportation_screen.tooltip").append(name);
    }

    static void renderTooltip(LinkageTeleportScreen screen, MatrixStack matrices, List<OrderedText> tooltip, int mouseX, int mouseY) {
        screen.renderOrderedTooltip(matrices, tooltip, mouseX, mouseY);
        screen.setOnRendered((Runnable)null);
    }

    public List<? extends Element> children() { return ImmutableList.of(this.teleportButton); }

    public List<? extends Selectable> selectableChildren() { return ImmutableList.of(this.teleportButton); }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    static {
        BLACK_COLOR = ColorHelper.Argb.getArgb(190, 0, 0, 0);
        GRAY_COLOR = ColorHelper.Argb.getArgb(255, 74, 74, 74);
        DARK_GRAY_COLOR = ColorHelper.Argb.getArgb(255, 48, 48, 48);
        WHITE_COLOR = ColorHelper.Argb.getArgb(255, 255, 255, 255);
        LIGHT_GRAY_COLOR = ColorHelper.Argb.getArgb(140, 255, 255, 255);
    }

}
