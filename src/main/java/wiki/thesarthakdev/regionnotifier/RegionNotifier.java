package wiki.thesarthakdev.regionnotifier;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import wiki.thesarthakdev.regionnotifier.commands.ReloadCommand;

public final class RegionNotifier extends JavaPlugin {

    private ConfigManager configManager;
    private WorldGuardPlugin worldGuard;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        // Initialize Adventure
        this.adventure = BukkitAudiences.create(this);

        // Load configuration
        configManager = new ConfigManager(this);
        configManager.loadConfig();

        // Initialize WorldGuard
        worldGuard = WorldGuardPlugin.inst();
        if (worldGuard == null) {
            getLogger().severe("WorldGuard not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Register commands and events
        getCommand("regionnotifier").setExecutor(new ReloadCommand(this, configManager));
        getServer().getPluginManager().registerEvents(new RegionListener(this, configManager, adventure), this);

        getLogger().info("RegionNotifier has been enabled!");
    }

    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        getLogger().info("RegionNotifier has been disabled!");
    }

    public BukkitAudiences getAdventure() {
        return adventure;
    }
}