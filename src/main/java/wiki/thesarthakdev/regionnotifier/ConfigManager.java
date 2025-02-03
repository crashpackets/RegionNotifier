package wiki.thesarthakdev.regionnotifier;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;
    private final Map<String, RegionConfig> regions = new HashMap<String, RegionConfig>();
    private String noPermissionMessage;
    private String reloadSuccessMessage;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
        this.regions.clear();
        this.noPermissionMessage = this.plugin.getConfig().getString("messages.no-permission", "<red>No permission!</red>");
        this.reloadSuccessMessage = this.plugin.getConfig().getString("messages.reload-success", "<green>Config reloaded!</green>");
        ConfigurationSection regionsSection = this.plugin.getConfig().getConfigurationSection("regions");
        if (regionsSection == null) {
            return;
        }
        for (String regionName : regionsSection.getKeys(false)) {
            ConfigurationSection regionSection = regionsSection.getConfigurationSection(regionName);
            if (regionSection == null) continue;
            ConfigurationSection enterSection = regionSection.getConfigurationSection("enter");
            ConfigurationSection exitSection = regionSection.getConfigurationSection("exit");
            RegionConfig config = new RegionConfig(enterSection != null ? enterSection.getString("title") : null, enterSection != null ? enterSection.getString("subtitle") : null, enterSection != null ? enterSection.getString("actionbar") : null, enterSection != null ? enterSection.getBoolean("enabled", true) : false, exitSection != null ? exitSection.getString("title") : null, exitSection != null ? exitSection.getString("subtitle") : null, exitSection != null ? exitSection.getString("actionbar") : null, exitSection != null ? exitSection.getBoolean("enabled", true) : false);
            this.regions.put(regionName, config);
        }
    }

    public String getNoPermissionMessage() {
        return this.noPermissionMessage;
    }

    public String getReloadSuccessMessage() {
        return this.reloadSuccessMessage;
    }

    public RegionConfig getRegionConfig(String regionName) {
        return this.regions.get(regionName);
    }

    public static class RegionConfig {
        public final String enterTitle;
        public final String enterSubtitle;
        public final String enterActionbar;
        public final boolean enterEnabled;
        public final String exitTitle;
        public final String exitSubtitle;
        public final String exitActionbar;
        public final boolean exitEnabled;

        public RegionConfig(String enterTitle, String enterSubtitle, String enterActionbar, boolean enterEnabled, String exitTitle, String exitSubtitle, String exitActionbar, boolean exitEnabled) {
            this.enterTitle = enterTitle;
            this.enterSubtitle = enterSubtitle;
            this.enterActionbar = enterActionbar;
            this.enterEnabled = enterEnabled;
            this.exitTitle = exitTitle;
            this.exitSubtitle = exitSubtitle;
            this.exitActionbar = exitActionbar;
            this.exitEnabled = exitEnabled;
        }
    }
}
