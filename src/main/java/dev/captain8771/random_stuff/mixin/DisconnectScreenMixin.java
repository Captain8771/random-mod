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
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
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
        ButtonWidget.Builder ReconnectButton = ButtonWidget.builder(Text.literal("Reconnect"), (button) -> {
            ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);
            ConnectScreen.connect(mc.currentScreen, mc, serverAddress, RandomStuffMod.lastServer);
        });
        if (RandomStuffMod.CONFIG.reconnectRelated.reconnectButton()) {
            this.addDrawableChild(ReconnectButton.positionAndSize(this.width / 2 - 100, Math.min(this.height / 2 + this.reasonHeight / 2 + 9, this.height - 30) + 20, 200, 20).build());
        }

        if (RandomStuffMod.CONFIG.reconnectRelated.autoReconnect()) {
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    RandomStuffMod.LOGGER.error("hey shithead, it's your code, not the timer");
                    MinecraftClient minecraft = MinecraftClient.getInstance();
                    // check if the type of screen is the same as the current screen
                    if (!(minecraft.currentScreen instanceof DisconnectedScreen)) return;

                    ServerAddress serverAddress = ServerAddress.parse(RandomStuffMod.lastServer.address);

                    ConnectScreen connectScreen = ConnectScreenAccessor.createConnectScreen(minecraft.currentScreen);
                    ((ConnectScreenAccessor)connectScreen).callConnect(minecraft, serverAddress, RandomStuffMod.lastServer);
                }
            };
            Timer timer = new Timer(5000, taskPerformer);
            timer.setRepeats(false);
            timer.start();
        }
    }
}
