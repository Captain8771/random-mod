package dev.captain8771.random_stuff;

import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "random_stuff")
@Config(name = "random_stuff", wrapperName = "RandomStuffConfig")
public class RandomConfigSchema {
    @SectionHeader("splash-config")
    public boolean replaceSplashText = true;

    @Nest
    public RGB redgreenblue = new RGB();
    public static class RGB {
        @RangeConstraint(min = 0, max = 255)
        public int RgbRed = 85;
        @RangeConstraint(min = 0, max = 255)
        public int RgbGreen = 255;
        @RangeConstraint(min = 0, max = 255)
        public int RgbBlue = 255;
    }

    public forceQuoteChoices forceQuote = forceQuoteChoices.RANDOM;
    public enum forceQuoteChoices {
        RANDOM,
        NOQUOTE,
        MADE_BY_CAPTAIN8771,
        MADE_WITH_QUILT,
        NOT_MADE_WITH_FABRIC,
        DONT_ASK_FOR_FORGE_OR_YOU_FORFEIT_YOUR_LIVER,
        THE_IRS_IS_AT_MY_DOOR,
        THE_FBI_IS_AT_MY_DOOR,
        LGBTQ_RIGHTS,
        ITS_RAINING_SOMEWHERE_ELSE,
        I_GOT_TOO_SILLY_WITH_THIS_MOD,
        NAAMLOOS_WAS_HERE,
        USER_IS_NOT_IN_SUDO,
        FBI_AT_YOUR_DOOR,
        SPECTRE,
        DONT_LOOK_SHOULDER,
        CS_OVER_JAVA,
        MADE_IN_JAVA,
        IKVM,
        BUSY,
        FABRIC_ASK,
        JAVA_EVERYWHERE
    }

    @SectionHeader("patch-toggles")

    @Nest
    public RespawnRelated respawnRelated = new RespawnRelated();
    public static class RespawnRelated {
        public boolean allowQuickRespawn = false;

        public boolean allowInstantRespawn = false;
    }

    @Nest
    public ReconnectRelated reconnectRelated = new ReconnectRelated();

    public static class ReconnectRelated {
        public boolean reconnectButton = false;

        public boolean autoReconnect = false;
    }

}
