package me.ogali.permissionportals;

import me.ogali.permissionportals.listeners.PortalEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class PermissionPortals extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PortalEvent(this), this);
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&5&lPP&f] Permission Portals &a&lEnabled!"));
        config();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&5&lPP&f] Permission Portals &c&lDisabled!"));
    }

    public void config() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
