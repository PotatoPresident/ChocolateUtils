package io.github.potatopresident.chocolateutils.mixin;

import io.github.potatopresident.chocolateutils.command.ReplyCommand;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

@Mixin(MessageCommand.class)
public abstract class MessageCommandMixin {
    @Inject(
            method = "execute",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private static void saveMessageTargets(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Text message, CallbackInfoReturnable<Integer> cir, UUID uUID, Consumer<Text> consumer2, Iterator<ServerPlayerEntity> players, ServerPlayerEntity serverPlayerEntity) {
        if (source.getEntity() instanceof ServerPlayerEntity senderPlayer) {
            ReplyCommand.replyTargets.put(serverPlayerEntity.getUuid(), senderPlayer.getUuid());
        }
    }
}