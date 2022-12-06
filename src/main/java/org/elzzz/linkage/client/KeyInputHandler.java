package org.elzzz.linkage.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_LINKAGE = "key.category.linkage";
    public static final String KEY_OPEN_LINKAGE_SCREEN = "key.linkage.open_teleportation_screen";

    private static KeyBinding openTeleportationScreenKey;

    public static void registerTeleportationScreenKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openTeleportationScreenKey.wasPressed()) {
                client.player.sendMessage(Text.literal("Linkage screen opened"), true);
            }
        });
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
