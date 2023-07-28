package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffConfig;
import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {

    @Shadow
    public abstract int drawShadowedText(TextRenderer renderer, @Nullable String text, int x, int y, int color);

    @Inject(method = "drawCenteredShadowedText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", at = @At("HEAD"), cancellable = true)
    private void drawCenteredShadowedText(TextRenderer renderer, String text, int centerX, int y, int color, CallbackInfo ci) {
        if (Objects.equals(text, RandomStuffMod.MenuString)) {
            ci.cancel();
            int newColor = RandomStuffMod.CONFIG.RGB().argb();
            this.drawShadowedText(renderer, text, centerX - renderer.getWidth(text) / 2, y, newColor);
        }
    }
}
