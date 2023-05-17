package dev.captain8771.random_stuff.mixin;

import dev.captain8771.random_stuff.RandomStuffMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.multiplayer.report.ReportEnvironment;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.registry.impl.sync.client.ClientRegistrySync;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectScreenMixin extends Screen {

    @Shadow
    private int reasonHeight;

    @Shadow
    @Final
    private Screen parent;

    @Shadow
    @Final
    private Text reason;

    protected DisconnectScreenMixin(Text title) {
        super(title);
    }


    @Inject(method = "init", at = @At("TAIL"))
    public void RandomStuffMod$init(CallbackInfo ci) {
        if (!RandomStuffMod.CONFIG.reconnectRelated.reconnectButton() && !RandomStuffMod.CONFIG.reconnectRelated.autoReconnect()) return;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (Objects.isNull(RandomStuffMod.lastServer)) {
            return;
        }
        ButtonWidget.Builder ReconnectButton = ButtonWidget.builder(Text.translatable("gui.random_stuff.button.reconnect"), (button) -> {
            ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);
            ConnectScreen.connect(this.parent, mc, serverAddress, RandomStuffMod.lastServer);
        });
        if (RandomStuffMod.CONFIG.reconnectRelated.reconnectButton()) {
            int y = Math.min(this.height / 2 + this.reasonHeight / 2 + 9, this.height - 30) + 20;
            // check if the 'authme' mod is installed
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
                    y += 30;
                }


            }
            this.addDrawableChild(ReconnectButton.positionAndSize(this.width / 2 - 100, y, 200, 20).build());
        }

        if (RandomStuffMod.CONFIG.reconnectRelated.autoReconnect()) {
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    MinecraftClient minecraft = MinecraftClient.getInstance();
                    // check if the type of screen is the same as the current screen
                    if (!(minecraft.currentScreen instanceof DisconnectedScreen)) return;

                    ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);
                    ServerInfo serverInfo = RandomStuffMod.lastServer;

                    ConnectScreen connectScreen = ConnectScreenAccessor.createConnectScreen(minecraft.currentScreen);
//                    minecraft.disconnect();
//                    minecraft.startOnlineMode();
//                    minecraft.setChatReportContext(ReportEnvironment.create(serverInfo != null ? serverInfo.address : serverAddress.getAddress()));
                    ClientRegistrySync.restoreSnapshot(minecraft);
                    ((ConnectScreenAccessor)connectScreen).callConnect(minecraft, serverAddress, serverInfo);
                }
            };
            Timer timer = new Timer(5000, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
    }
}
