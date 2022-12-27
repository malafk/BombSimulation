package lol.maltest.bombsimulation.simulation;

import lol.maltest.bombsimulation.BombSimulation;
import lol.maltest.bombsimulation.simulation.model.Simulation;
import lol.maltest.bombsimulation.util.ChatUtil;
import lol.maltest.bombsimulation.util.SimUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.UUID;

public class SimulationManager {

    private BombSimulation plugin;

    @Getter
    private ArrayList<Simulation> runningSimulations = new ArrayList<>();

    public SimulationManager(BombSimulation plugin) {
        this.plugin = plugin;
    }

    public void startSimulation(Player player) {
        Simulation simulation = new Simulation(plugin, player, SimUtil.getBlocksAround(player.getLocation()), SimUtil.getEntities(player.getLocation()));
        runningSimulations.add(simulation);
        new BukkitRunnable() {
            @Override
            public void run() {
                simulation.start();
            }
        }.runTask(plugin);
        player.sendMessage(ChatUtil.clr("&7Starting your simulation"));
    }

    public void endSimulation(UUID playerUuid, boolean onQuit) {
        Simulation simulation = getSimulation(playerUuid);
        if(onQuit) {
            simulation.delete();
            runningSimulations.remove(simulation);
            return;
        }
        Player player = Bukkit.getPlayer(playerUuid);
        player.sendMessage(ChatUtil.clr("&cSimulation has ended"));
        player.teleport(simulation.originalLocation);
        player.setGameMode(GameMode.SURVIVAL);
        plugin.queueManager.getQueue(player).deleteQueue();
        runningSimulations.remove(simulation);
    }

    public Simulation getSimulation(UUID pUUID) {
        for(Simulation simulation : runningSimulations) {
            if(simulation.playerUuid.equals(pUUID)) {
                return simulation;
            }
        }
        return null;
    }

}
