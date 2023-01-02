package org.elzzz.linkage.client.gui.widget;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import org.elzzz.linkage.client.LinkageClient;
import org.elzzz.linkage.client.gui.screen.LinkageTeleportScreen;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Environment(EnvType.CLIENT)
public class LinkageTeleportScreenWidget extends ElementListWidget<LinkageTeleportScreenPlayerEntry> {
    private final LinkageTeleportScreen parent;
    private final List<LinkageTeleportScreenPlayerEntry> players = Lists.newArrayList();
    @Nullable
    private String currentSearch;

    public LinkageTeleportScreenWidget(LinkageTeleportScreen parent, MinecraftClient client, int width, int height,
                                       int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
        this.parent = parent;
        this.setRenderBackground(false);
        this.setRenderHorizontalShadows(false);
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        double d = this.client.getWindow().getScaleFactor();
        RenderSystem.enableScissor((int)((double)this.getRowLeft() * d),
                (int)((double)(this.height - this.bottom) * d),
                (int)((double)(this.getScrollbarPositionX() + 6) * d),
                (int)((double)(this.height - (this.height - this.bottom) - this.top - 4) * d));
        super.render(matrices, mouseX, mouseY, delta);
        RenderSystem.disableScissor();
    }

    public void refresh() {
        this.players.clear();

        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.player.networkHandler;
        Collection<LinkageTeleportScreenPlayerEntry> players = new HashSet<>();

        String local_player_name = clientPlayNetworkHandler.getProfile().getName();
        String local_player_dimension = clientPlayNetworkHandler.getWorld().getDimensionKey().getValue().toString();

        LinkageClient.clientAvailablePlayers.forEach((uuid, dimension) -> {
            PlayerListEntry playerListEntry = clientPlayNetworkHandler.getPlayerListEntry(uuid);
            if (playerListEntry != null) {
                String name = playerListEntry.getProfile().getName();

                if (Objects.equals(local_player_name, name) ||
                        (parent.isLocal() && !Objects.equals(local_player_dimension, dimension))) { return; }

                players.add(new LinkageTeleportScreenPlayerEntry(
                        this.client,
                        this.parent,
                        uuid,
                        name,
                        parent.isLocal() ? "" : dimension,
                        playerListEntry::getSkinTexture)
                );
            }
        });

        this.players.addAll(players);
        this.filterPlayersBySearch();
        this.sortPlayers();
        this.replaceEntries(this.players);

        // In reference widget, scroll amount was always assigned to return value of getScrollAmount() method
        this.setScrollAmount(this.getScrollAmount());
    }

    private void filterPlayersBySearch() {
        if (this.currentSearch != null) {
            this.players.removeIf((player) -> !player.getName().toLowerCase(Locale.ROOT).contains(this.currentSearch));
            this.replaceEntries(this.players);
        }
    }

    private void sortPlayers() {
        this.players.sort(Comparator.comparing(LinkageTeleportScreenPlayerEntry::getName));
    }

    public void setCurrentSearch(String currentSearch) { this.currentSearch = currentSearch; }

    public boolean isEmpty() { return this.players.isEmpty(); }
}
