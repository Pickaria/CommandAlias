package fr.pickaria.commandalias;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class ChangeServerCommand {
    private final RegisteredServer targetServer;

    ChangeServerCommand(RegisteredServer registeredServer) {
        this.targetServer = registeredServer;
    }

    private BrigadierCommand createBrigadierCommand() {
        String serverName = targetServer.getServerInfo().getName();

        LiteralCommandNode<CommandSource> helloNode = BrigadierCommand.literalArgumentBuilder(serverName)
                .executes(context -> {
                    CommandSource source = context.getSource();

                    if (source instanceof Player player) {
                        player.getCurrentServer().ifPresent(currentServer -> {
                            if (!currentServer.getServer().equals(targetServer)) {
                                player.createConnectionRequest(targetServer).connect();
                            } else {
                                player.sendMessage(Component.translatable("velocity.error.already-connected", NamedTextColor.RED));
                            }
                        });
                    }

                    return Command.SINGLE_SUCCESS;
                })
                .build();

        return new BrigadierCommand(helloNode);
    }

    public void registerCommand(CommandAlias plugin, ProxyServer proxy) {
        String serverName = targetServer.getServerInfo().getName();

        CommandManager commandManager = proxy.getCommandManager();
        CommandMeta commandMeta = commandManager.metaBuilder(serverName)
                .plugin(plugin)
                .build();

        commandManager.register(commandMeta, this.createBrigadierCommand());
    }
}
