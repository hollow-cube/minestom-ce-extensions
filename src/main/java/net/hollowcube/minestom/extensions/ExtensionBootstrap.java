package net.hollowcube.minestom.extensions;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.ExtensionManager;
import net.minestom.server.utils.validate.Check;
import org.jetbrains.annotations.NotNull;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public final class ExtensionBootstrap {
    private static ExtensionManager extensions = null;

    public static @NotNull ExtensionBootstrap init() {
        return new ExtensionBootstrap(MinecraftServer.init());
    }

    public static @NotNull ExtensionManager getExtensionManager() {
        Check.notNull(extensions, "ExtensionBootstrap has not been initialized yet!");
        return extensions;
    }

    private final MinecraftServer server;

    private ExtensionBootstrap(@NotNull MinecraftServer server) {
        this.server = server;
        extensions = new ExtensionManager(MinecraftServer.process());

        extensions.start();
        extensions.gotoPreInit();
    }

    public void start(@NotNull String address, int port) {
        start(new InetSocketAddress(address, port));
    }

    public void start(@NotNull SocketAddress address) {
        extensions.gotoInit();
        this.server.start(address);
        extensions.gotoPostInit();
    }

    public void shutdown() {
        extensions.shutdown();
    }

}
