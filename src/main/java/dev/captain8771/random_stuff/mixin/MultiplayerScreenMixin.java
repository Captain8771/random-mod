package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {

    @Inject(method = "connect(Lnet/minecraft/client/network/ServerInfo;)V", at = @At("HEAD"))
    public void RandomStuffMod$connect(ServerInfo entry, CallbackInfo ci) {
        RandomStuffMod.lastServer = entry;
    }
}
