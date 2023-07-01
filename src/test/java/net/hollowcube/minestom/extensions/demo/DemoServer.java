package net.hollowcube.minestom.extensions.demo;

import net.hollowcube.minestom.extensions.ExtensionBootstrap;

public class DemoServer {
    public static void main(String[] args) {
        var server = ExtensionBootstrap.init();

        server.start("0.0.0.0", 25565);
    }
}
