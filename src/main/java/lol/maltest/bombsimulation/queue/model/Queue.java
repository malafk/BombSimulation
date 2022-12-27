package lol.maltest.bombsimulation.queue.model;

import lol.maltest.bombsimulation.queue.QueueManager;
import lol.maltest.bombsimulation.util.ChatUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Queue {

    public UUID playerUuid;
    public Location playerLocation;

    private QueueManager queueManager;
    private BukkitTask queueReminder;

    public Queue(UUID playerUuid, QueueManager queueManager) {
        this.playerUuid = playerUuid;
        this.queueManager = queueManager;
        playerLocation = Bukkit.getPlayer(playerUuid).getLocation();
        queueReminder = new BukkitRunnable() {
            @Override
            public void run() {
                Player player = Bukkit.getPlayer(playerUuid);
                if(player.getWorld().getName().equals("simulations")) return;
                player.sendMessage(ChatUtil.clr("&7You are in position &c" + pos() + " &7for &cbomb simulation"));
                tryStart();
            }
        }.runTaskTimer(queueManager.plugin, 0, 20 * 10);
    }

    public String pos() {
        return queueManager.getPos(this);
    }

    public void tryStart() {
        System.out.println(pos());
        if(!pos().startsWith("0")) return;
        queueManager.plugin.simulationManager.startSimulation(Bukkit.getPlayer(playerUuid));
    }

    public void deleteQueue() {
        Bukkit.getPlayer(playerUuid).sendMessage(ChatUtil.clr("&cYour queue was deleted!"));
        queueManager.removeQueue(this);
        queueReminder.cancel();
    }
}
