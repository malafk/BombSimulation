package lol.maltest.bombsimulation.listeners;

import lol.maltest.bombsimulation.BombSimulation;
import lol.maltest.bombsimulation.util.SimUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class SimulationListener implements Listener {

    private BombSimulation plugin;

    public SimulationListener(BombSimulation plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        if (e.getPlayer().isOp()) return;
        if (!e.getPlayer().getWorld().getName().equals("simulations")) return;
        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND) || e.getCause().equals(PlayerTeleportEvent.TeleportCause.SPECTATE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent e) {
        if (plugin.queueManager.isQueued(e.getPlayer())) {
            plugin.queueManager.removeQueue(plugin.queueManager.getQueue(e.getPlayer()));
        }
        if (plugin.simulationManager.getSimulation(e.getPlayer().getUniqueId()) != null) {
            plugin.simulationManager.endSimulation(e.getPlayer().getUniqueId(), true);
        }
    }
}
