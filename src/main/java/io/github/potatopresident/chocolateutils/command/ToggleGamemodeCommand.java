package io.github.potatopresident.chocolateutils.command;

import com.mojang.brigadier.CommandDispatcher;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.world.GameMode;

public class ToggleGamemodeCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tg")
                        .requires(Permissions.require("chocolate.commands.tg", 3))
                        .executes(context -> {
                            var player = context.getSource().getPlayer();
                            if (player.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                                player.changeGameMode(GameMode.SURVIVAL);
                            } else {
                                player.changeGameMode(GameMode.SPECTATOR);
                            }

                            return 1;
                        })
        );
    }
}
