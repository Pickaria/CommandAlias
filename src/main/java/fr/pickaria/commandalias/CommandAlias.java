package fr.pickaria.commandalias;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;


@Plugin(
        id = "command_alias",
        name = "CommandAlias",
        version = BuildConstants.VERSION
)
public class CommandAlias {
    private final ProxyServer proxy;

    @Inject
    public CommandAlias(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        for (RegisteredServer server : proxy.getAllServers()) {
            ChangeServerCommand command = new ChangeServerCommand(server);
            command.registerCommand(this, proxy);
        }
    }
}
