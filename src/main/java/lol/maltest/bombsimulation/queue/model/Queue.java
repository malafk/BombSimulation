package lol.maltest.bombsimulation.queue.model;

import lol.maltest.bombsimulation.queue.QueueManager;
import lol.maltest.bombsimulation.util.ChatUtil;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Queue {

    public UUID playerUuid;

    private QueueManager queueManager;
    private BukkitTask queueReminder;

    public Queue(UUID playerUuid, QueueManager queueManager) {
        this.playerUuid = playerUuid;
        this.queueManager = queueManager;
        queueReminder = new BukkitRunnable() {
            @Override
            public void run() {
                Player player = Bukkit.getPlayer(playerUuid);
                player.sendMessage(ChatUtil.clr("&7You are in position &c" + pos() + " &7for &cbomb simulation"));
            }
        }.runTaskTimerAsynchronously(queueManager.plugin, 0, 30);
    }

    public String pos() {
        return queueManager.getPos(this);
    }

    public void start() {
        // do start simulation
        deleteQueue();
    }

    public void deleteQueue() {
        queueManager.removeQueue(this);
        queueReminder.cancel();
    }
}
