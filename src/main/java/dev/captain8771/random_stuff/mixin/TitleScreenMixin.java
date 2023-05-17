package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomConfigSchema;
import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Shadow
	@Nullable
	private String splashText;

	@Inject(method = "init", at = @At("TAIL"))
	public void RandomStuffMod$onInit(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (RandomStuffMod.CONFIG.replaceSplashText()) {

            RandomStuffMod.MenuString = RandomStuffMod.MenuStringBase;
            RandomConfigSchema.forceQuoteChoices forceQuoteChoices = RandomStuffMod.CONFIG.forceQuote();

            if (forceQuoteChoices.equals(RandomConfigSchema.forceQuoteChoices.NOQUOTE)) {
                RandomStuffMod.MenuString += ""; // :3
            } else if (forceQuoteChoices.equals(RandomConfigSchema.forceQuoteChoices.RANDOM)) {
                Random rand = new Random();
                int randInt = rand.nextInt(RandomStuffMod.Quotes.length);
                RandomStuffMod.MenuString += " ({})".replace("{}", RandomStuffMod.Quotes[randInt].getString());
            } else {
                int index = forceQuoteChoices.ordinal() - 2;
                String pulledString = RandomStuffMod.Quotes[index % RandomStuffMod.Quotes.length].getString();
                RandomStuffMod.MenuString += " ({})".replace("{}", pulledString);
            }

            RandomStuffMod.MenuString = RandomStuffMod.MenuString.replace("{USER}", client.getSession().getUsername());
            this.splashText = RandomStuffMod.MenuString;
        } else {
            this.splashText = client.getSplashTextLoader().get();
        }
    }
}
