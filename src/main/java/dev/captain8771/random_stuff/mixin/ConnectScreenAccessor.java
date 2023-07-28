package dev.captain8771.random_stuff.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ConnectScreen.class)
public interface ConnectScreenAccessor {
    @Invoker("<init>")
    static ConnectScreen createConnectScreen(Screen parent, Text failureErrorMessage) {
        throw new UnsupportedOperationException();
    }

    @Invoker()
    void callConnect(final MinecraftClient client, final ServerAddress address, @Nullable final ServerInfo serverInfo);
}
