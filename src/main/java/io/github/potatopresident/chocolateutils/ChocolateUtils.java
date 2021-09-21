package io.github.potatopresident.chocolateutils;

import io.github.potatopresident.chocolateutils.event.PlayerDeathCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

public class ChocolateUtils implements ModInitializer {
    @Override
    public void onInitialize() {
        PlayerDeathCallback.EVENT.register((player, source) -> {
            player.sendMessage(new LiteralText("You died at ").formatted(Formatting.GRAY).append(toReadablePos(player.getBlockPos())), false);
        });
    }

    public MutableText toReadablePos(BlockPos pos) {
        return Texts.bracketed(new TranslatableText("chat.coordinates", pos.getX(), pos.getY(), pos.getZ())).formatted(Formatting.GREEN);
    }
}
