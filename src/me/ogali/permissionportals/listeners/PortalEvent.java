package me.ogali.permissionportals.listeners;

import me.ogali.permissionportals.PermissionPortals;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class PortalEvent implements Listener {
    private final PermissionPortals m;

    public PortalEvent(PermissionPortals m) {
        this.m = m;
    }

    List<String> endMessage = new ArrayList<>();
    List<String> netherMessage = new ArrayList<>();

    public void removeFromList(List<String> list, Player p, String time) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(m, () -> list.remove(p.getName()), m.getConfig().getLong(time) * 20);
    }

    private boolean doStuff(PlayerTeleportEvent e, List<String> list, String configSection, boolean PushBack) {
        Player p = e.getPlayer();
        String name = e.getPlayer().getName();
        if (PushBack) {
            p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(-0.7).setY(0.5));
        }
        if (!(list.contains(name))) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', m.getConfig().getString(configSection)));
            list.add(name);
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPortal(PlayerTeleportEvent e) {
        if (!(e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL) || (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL))))
            return;

        Player p = e.getPlayer();
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
            if (!(p.hasPermission(m.getConfig().getString("End-Portal.Permission")))) {
                e.setCancelled(true);
                boolean ePushBack = m.getConfig().getBoolean("End-Portal.Push-Back");
                if (doStuff(e, endMessage, "End-Portal.No-Perm", ePushBack)) return;
            }
            removeFromList(endMessage, p, "End-Portal.Message-Time");
        }
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            if (!(p.hasPermission(m.getConfig().getString("Nether-Portal.Permission")))) {
                e.setCancelled(true);
                boolean nPushBack = m.getConfig().getBoolean("Nether-Portal.Push-Back");
                if (doStuff(e, netherMessage, "Nether-Portal.No-Perm", nPushBack)) return;
            }
            removeFromList(netherMessage, p, "Nether-Portal.Message-Time");
        }
    }
}
