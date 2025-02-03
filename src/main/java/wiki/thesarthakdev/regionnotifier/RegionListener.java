package wiki.thesarthakdev.regionnotifier;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import wiki.thesarthakdev.regionnotifier.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;

public class RegionListener implements Listener {

    private final RegionNotifier plugin;
    private final ConfigManager configManager;
    private final BukkitAudiences adventure;
    private final Map<Player, ProtectedRegion> lastRegions = new HashMap<>();

    public RegionListener(RegionNotifier plugin, ConfigManager configManager, BukkitAudiences adventure) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.adventure = adventure;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("regionnotifier.notify")) return;

        Location from = event.getFrom();
        Location to = event.getTo();
        if (to == null) return;

        // Skip if the player hasn't moved to a new block
        if (from.getBlockX() == to.getBlockX() &&
                from.getBlockY() == to.getBlockY() &&
                from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        // Run region checks asynchronously
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            // Capture the regions in async thread
            ProtectedRegion fromRegion = getRegionAt(from);
            ProtectedRegion toRegion = getRegionAt(to);

            // Handle transitions on the main thread
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                ProtectedRegion lastRegion = lastRegions.get(player);

                // Handle region exit
                if (lastRegion != null && (toRegion == null || !toRegion.getId().equals(lastRegion.getId()))) {
                    handleRegionExit(player, lastRegion);
                }

                // Handle region entry
                if (toRegion != null && (lastRegion == null || !toRegion.getId().equals(lastRegion.getId()))) {
                    handleRegionEntry(player, toRegion);
                }

                // Update the last known region
                lastRegions.put(player, toRegion);
            });
        });
    }

    private ProtectedRegion getRegionAt(Location location) {
        RegionManager regionManager = WorldGuard.getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(com.sk89q.worldedit.bukkit.BukkitAdapter.adapt(location.getWorld()));

        if (regionManager == null) return null;

        return regionManager.getApplicableRegions(com.sk89q.worldedit.bukkit.BukkitAdapter.asBlockVector(location))
                .getRegions()
                .stream()
                .findFirst()
                .orElse(null);
    }

    private void handleRegionEntry(Player player, ProtectedRegion region) {
        ConfigManager.RegionConfig config = configManager.getRegionConfig(region.getId());
        if (config != null && config.enterEnabled) {
            MessageUtils.sendRegionMessages(adventure.player(player), config.enterTitle, config.enterSubtitle, config.enterActionbar);
        }
    }

    private void handleRegionExit(Player player, ProtectedRegion region) {
        ConfigManager.RegionConfig config = configManager.getRegionConfig(region.getId());
        if (config != null && config.exitEnabled) {
            MessageUtils.sendRegionMessages(adventure.player(player), config.exitTitle, config.exitSubtitle, config.exitActionbar);
        }
    }
}