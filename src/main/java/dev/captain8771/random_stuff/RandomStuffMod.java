package dev.captain8771.random_stuff;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public static final dev.captain8771.random_stuff.RandomStuffConfig CONFIG = dev.captain8771.random_stuff.RandomStuffConfig.createAndLoad();

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());

    }

    public static int getColor(int red, int green, int blue, int alpha) {
        return ((alpha << 24) | (red << 16) | (green << 8) | blue);
    }
}
