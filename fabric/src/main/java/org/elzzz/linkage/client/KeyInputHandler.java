package org.elzzz.linkage.client;

import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.elzzz.linkage.client.gui.screen.LinkageTeleportScreen;
import org.elzzz.linkage.register.ModItems;
import org.lwjgl.glfw.GLFW;

import java.util.Optional;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_LINKAGE = "key.category.linkage";
    public static final String KEY_OPEN_LINKAGE_SCREEN = "key.linkage.open_teleportation_screen";

    public static KeyBinding openTeleportationScreenKey;

    private static void handleTeleportationScreenKey(MinecraftClient client) {
        while (openTeleportationScreenKey.wasPressed()) {
            Optional<TrinketComponent> playerTrinketComponent = TrinketsApi.getTrinketComponent(client.player);

            if (playerTrinketComponent.isPresent()) {
                if (playerTrinketComponent.get().isEquipped(ModItems.GLYPH_RING))
                    client.setScreen(new LinkageTeleportScreen(true));
                else if (playerTrinketComponent.get().isEquipped(ModItems.INTERDIMENSIONAL_GLYPH_RING))
                    client.setScreen(new LinkageTeleportScreen(false));
                else
                    client.player.sendMessage(Text.translatable("linkage.gui.no_ring_found"), true);
            }
        }
    }

    public static void registerTeleportationScreenKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(KeyInputHandler::handleTeleportationScreenKey);
    }

    public static void register() {
        openTeleportationScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_LINKAGE_SCREEN,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_LINKAGE
        ));

        registerTeleportationScreenKeyInputs();
    }

}
