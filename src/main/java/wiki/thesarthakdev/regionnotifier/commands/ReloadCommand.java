package wiki.thesarthakdev.regionnotifier.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import wiki.thesarthakdev.regionnotifier.ConfigManager;
import wiki.thesarthakdev.regionnotifier.RegionNotifier;

public class ReloadCommand
        implements CommandExecutor {
    private final RegionNotifier plugin;
    private final ConfigManager configManager;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public ReloadCommand(RegionNotifier plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0 || !args[0].equalsIgnoreCase("reload")) {
            this.plugin.getAdventure().sender(sender).sendMessage((Component)this.miniMessage.deserialize("<red>Usage: /regionnotifier reload</red>"));
            return true;
        }
        if (!sender.hasPermission("regionnotifier.reload")) {
            this.plugin.getAdventure().sender(sender).sendMessage((Component)this.miniMessage.deserialize(this.configManager.getNoPermissionMessage()));
            return true;
        }
        this.configManager.loadConfig();
        this.plugin.getAdventure().sender(sender).sendMessage((Component)this.miniMessage.deserialize(this.configManager.getReloadSuccessMessage()));
        return true;
    }
}
