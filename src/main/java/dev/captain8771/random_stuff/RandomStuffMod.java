package dev.captain8771.random_stuff;

import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomStuffMod implements ModInitializer {
    public static ServerInfo lastServer;
    public static final Logger LOGGER = LoggerFactory.getLogger("Cap's random stuff");
    public static String MenuString = RandomStuffMod.MenuStringBase;
    public static final String MenuStringBase = "Cap's random stuff";
    public static final String[] Quotes = {
        "Made by Captain8771!",
        "Made with Quilt!",
        "Not made with Fabric!",
        "Don't ask for forge or you forfeit your liver.",
        "The IRS is at my door.",
        "The FBI is at my door.",
        "LGBTQ+ rights!",
        "FUCKING POLITICS",
        "It's raining somewhere else...",
        "I got too silly with this mod.",
        "Naamloos was here",
        "{USER} is not in the sudoers file. This incident will be reported.",
        "The FBI is at your door.",
        "There is a spectre haunting europe... the spectre of {USER}",
        "Don't look over your shoulder",
        "C# > Java",
        "Unfortunately, made in Java!",
        "Not (yet) running in IKVM!",
        "you been busy, huh?",
        "Don't ask for fabric or your kneecaps are mine.",
        "https://www.youtube.com/watch?v=SRLU1bJSLVg"
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
