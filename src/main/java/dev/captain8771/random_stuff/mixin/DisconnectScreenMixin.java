package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.registry.impl.sync.client.ClientRegistrySync;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectScreenMixin extends Screen {

    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private GridWidget grid;

    protected DisconnectScreenMixin(Text title) {
        super(title);
    }


    // capture the local "additionHelper"
    @Inject(method = "init", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/gui/widget/GridWidget;arrangeElements()V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void RandomStuffMod$init(CallbackInfo ci, GridWidget.AdditionHelper additionHelper) {
        if (!RandomStuffMod.CONFIG.reconnectRelated.reconnectButton() && !RandomStuffMod.CONFIG.reconnectRelated.autoReconnect())
            return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (Objects.isNull(RandomStuffMod.lastServer)) {
            return;
        }

        // add the reconnect button
        // this worked perfectly fine before, y u do dis mojang??? /s
        ButtonWidget rcnBtn = ButtonWidget.builder(Text.translatable("gui.random_stuff.button.reconnect"), (button) -> {
            ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);
            ConnectScreen.connect(this.parent, mc, serverAddress, RandomStuffMod.lastServer, false); // what is quickplay
        }).build();
        // -10 padding because the default padding puts the buttons so far away it looks weird. this puts them next to each other
        additionHelper.add(rcnBtn, this.grid.copyDefaultSettings().alignHorizontallyCenter().setPadding(-10));


        if (RandomStuffMod.CONFIG.reconnectRelated.autoReconnect()) {
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    MinecraftClient minecraft = MinecraftClient.getInstance();
                    // check if the type of screen is the same as the current screen
                    if (!(minecraft.currentScreen instanceof DisconnectedScreen)) return;

                    ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);
                    ServerInfo serverInfo = RandomStuffMod.lastServer;

                    ConnectScreen connectScreen = ConnectScreenAccessor.createConnectScreen(minecraft.currentScreen, Text.empty()); // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    ClientRegistrySync.restoreSnapshot(minecraft);
                    ((ConnectScreenAccessor) connectScreen).callConnect(minecraft, serverAddress, serverInfo);
                }
            };
            Timer timer = new Timer(5000, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    public void RandomStuffMod$onInit2(CallbackInfo ci) {
        if (QuiltLoader.isModLoaded("authme")) {
            boolean shouldMove = false;
            // seems to be possible to get the other buttons using this function: https://github.com/axieum/authme/blob/main/src/main/java/me/axieum/mcmod/authme/mixin/DisconnectedScreenMixin.java#L53

            // array of any type
            Object[] buttons = children().toArray();

            for (Object button : buttons) {
                if (button instanceof ButtonWidget btn) {
                    // check if the key is "gui.authme.button.relogin"
                    if (Objects.equals(btn.getMessage().getString(), Text.translatable("gui.authme.button.relogin").getString())) {
                        shouldMove = true;
                        break;
                    }
                }
            }

            if (shouldMove) {
                RandomStuffMod.LOGGER.info("AuthMe button found, moving reconnect button down to not overlap.");
                for (Object button : buttons) {
                    if (button instanceof ButtonWidget btn) {
                        if (Objects.equals(btn.getMessage().getString(), Text.translatable("gui.random_stuff.button.reconnect").getString())) {
                            // move the button
                            btn.setY(btn.getY() + btn.getHeight() + 8); // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                            break;
                        }
                    }
                }
            }
        }
    }
}
