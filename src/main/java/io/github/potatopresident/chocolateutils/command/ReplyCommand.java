package io.github.potatopresident.chocolateutils.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.potatopresident.chocolateutils.mixin.MessageCommandInvoker;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReplyCommand {
    public static Map<UUID, UUID> replyTargets = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("r")
                        .requires(Permissions.require("chocolate.commands.reply", true))
                        .then(CommandManager.argument("message", MessageArgumentType.message())
                                .executes(context -> execute(context, MessageArgumentType.getMessage(context, "message"))
                                )
                        )
        );
    }

    private static int execute(CommandContext<ServerCommandSource> context, Text message) throws CommandSyntaxException {
        var player = context.getSource().getPlayer();

        if (replyTargets.containsKey(player.getUuid())) {
            var target = context.getSource().getServer().getPlayerManager().getPlayer(replyTargets.get(player.getUuid()));
            if (target != null) {
                return MessageCommandInvoker.invokeExecute(context.getSource(), List.of(target), message);
            }
        }

        context.getSource().sendError(new LiteralText("Cannot find target to reply to"));
        return -1;
    }
}
