package io.github.potatopresident.chocolateutils;

import io.github.potatopresident.chocolateutils.event.PlayerDeathCallback;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

public class ChocolateUtils implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerDeathCallback.EVENT.register((player, source) -> {
            player.sendMessage(new LiteralText("You died at ").formatted(Formatting.GRAY).append(toReadablePos(player.getBlockPos())), false);
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) ->
                dispatcher.register(
                        CommandManager.literal("tg")
                                .requires(Permissions.require("chocolate.commands.tg", 3))
                                .executes(context -> {
                                    var player = context.getSource().getPlayer();
                                    if (player.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                                        player.changeGameMode(GameMode.SURVIVAL);
                                    } else  {
                                        player.changeGameMode(GameMode.SPECTATOR);
                                    }

                                    return 1;
                                })
                )
        );
    }

    public MutableText toReadablePos(BlockPos pos) {
        return Texts.bracketed(new TranslatableText("chat.coordinates", pos.getX(), pos.getY(), pos.getZ())).formatted(Formatting.GREEN);
    }
}
