package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(DrawableHelper.class)
public class DrawableHelperMixin {

    // public static void drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, String text, int centerX, int y, int color) {
    @Inject(method = "drawCenteredText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", at = @At("HEAD"), cancellable = true)
    private static void RandomStuffMod$drawCenteredText(MatrixStack matrices, TextRenderer textRenderer, String text, int centerX, int y, int color, CallbackInfo ci) {
        int newColor = RandomStuffMod.getColor(
            RandomStuffMod.CONFIG.redgreenblue.RgbRed(),
            RandomStuffMod.CONFIG.redgreenblue.RgbGreen(),
            RandomStuffMod.CONFIG.redgreenblue.RgbBlue(),
            255);
        if (Objects.equals(text, RandomStuffMod.MenuString)) {
            ci.cancel();
            textRenderer.drawWithShadow(matrices, text, (float) (centerX - textRenderer.getWidth(text) / 2), (float) y, newColor);
        }
    }
}
