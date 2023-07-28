package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomConfigSchema;
import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.SplashTextRenderer;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
	@Mutable
    @Final
    @Shadow
	@Nullable
	private String splashText;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void RandomStuffMod$onInit(CallbackInfo ci) {
        if (RandomStuffMod.CONFIG.replaceSplashText()) {
            this.splashText = RandomStuffMod.getQuote();
        }
    }
}
