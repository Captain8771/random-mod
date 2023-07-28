package dev.captain8771.random_stuff;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class RandomStuffMod implements ModInitializer {
    public static ServerInfo lastServer;
    public static final Logger LOGGER = LoggerFactory.getLogger("Cap's random stuff");
    public static String MenuString = RandomStuffMod.MenuStringBase;
    public static final String MenuStringBase = "Cap's random stuff";
    public static final Text[] Quotes = {
        Text.translatable("gui.random_stuff.splash.1"),
        Text.translatable("gui.random_stuff.splash.2"),
        Text.translatable("gui.random_stuff.splash.3"),
        Text.translatable("gui.random_stuff.splash.4"),
        Text.translatable("gui.random_stuff.splash.5"),
        Text.translatable("gui.random_stuff.splash.6"),
        Text.translatable("gui.random_stuff.splash.7"),
        Text.translatable("gui.random_stuff.splash.8"),
        Text.translatable("gui.random_stuff.splash.9"),
        Text.translatable("gui.random_stuff.splash.10"),
        Text.translatable("gui.random_stuff.splash.11"),
        Text.translatable("gui.random_stuff.splash.12"),
        Text.translatable("gui.random_stuff.splash.13"),
        Text.translatable("gui.random_stuff.splash.14"),
        Text.translatable("gui.random_stuff.splash.15"),
        Text.translatable("gui.random_stuff.splash.16"),
        Text.translatable("gui.random_stuff.splash.17"),
        Text.translatable("gui.random_stuff.splash.18"),
        Text.translatable("gui.random_stuff.splash.19"),
        Text.translatable("gui.random_stuff.splash.20")
    };

    public static String getQuote() {
        MinecraftClient client = MinecraftClient.getInstance();
        String returnText = RandomStuffMod.MenuStringBase;
        RandomConfigSchema.forceQuoteChoices forceQuoteChoices = RandomStuffMod.CONFIG.forceQuote();

        switch (forceQuoteChoices) {
            case NOQUOTE:
                // :3
                break;
            case RANDOM:
                Random rand = new Random();
                int randInt = rand.nextInt(RandomStuffMod.Quotes.length);
                returnText += " ({})".replace("{}", RandomStuffMod.Quotes[randInt].getString());
                break;
            default:
                int index = forceQuoteChoices.ordinal() - 2;
                String pulledString = RandomStuffMod.Quotes[index % RandomStuffMod.Quotes.length].getString();
                returnText += " ({})".replace("{}", pulledString);
                break;
        }

        returnText = returnText.replace("{USER}", client.getSession().getUsername());
        RandomStuffMod.MenuString = returnText;
        return returnText;
    }

    public static final dev.captain8771.random_stuff.RandomStuffConfig CONFIG = dev.captain8771.random_stuff.RandomStuffConfig.createAndLoad();

    @Override
    public void onInitialize(ModContainer mod) {
        // LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());
        // i must say i have no idea how i missed this line when i was making the mod
        // oh well
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        return ((alpha << 24) | (red << 16) | (green << 8) | blue);
    }
}
