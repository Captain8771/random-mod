package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffConfig;
import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin {
    @Shadow
    protected abstract void setButtonsActive(boolean active);

    @Shadow
    private int ticksSinceDeath;

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void RandomStuffMod$onInit(CallbackInfo ci) {
        if (RandomStuffMod.CONFIG.respawnRelated.allowInstantRespawn()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player != null) {
                client.player.requestRespawn();
                ci.cancel();
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void RandomStuffMod$render(CallbackInfo ci) {
        if (RandomStuffMod.CONFIG.respawnRelated.allowInstantRespawn()) {
            ci.cancel();
        }
    }


    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void RandomStuffMod$tick(CallbackInfo ci) {
        if (RandomStuffMod.CONFIG.respawnRelated.allowInstantRespawn()) {
            ci.cancel();
            return;
        }
        if (!RandomStuffMod.CONFIG.respawnRelated.allowQuickRespawn()) return;
        if (this.ticksSinceDeath == 1) {
            this.setButtonsActive(true);
            this.ticksSinceDeath = 21;
        }

    }
}
