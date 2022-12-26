package lol.maltest.bombsimulation.queue;

import lol.maltest.bombsimulation.BombSimulation;
import lol.maltest.bombsimulation.queue.model.Queue;
import lol.maltest.bombsimulation.util.ChatUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QueueManager {

    public BombSimulation plugin;

    @Getter
    private ArrayList<Queue> queues = new ArrayList<>();

    public QueueManager(BombSimulation plugin) {
        this.plugin = plugin;
    }

    public void addQueue(Queue queue) {
        queues.add(queue);
    }

    public String getPos(Queue queue) {
        int pos = 0;
        for(int i = 0; i < queues.size(); i++) {
            if(queues.get(i).playerUuid.equals(queue.playerUuid)) {
                pos = i;
                break;
            }
        }
        return pos + "/" + getQueues().size();
    }

    public boolean isQueued(Player player) {
        for(Queue queue : queues) {
            if(queue.playerUuid.equals(player.getUniqueId())) {
                return true;
            }
        }
        return false;
    }

    public Queue getQueue(Player player) {
        for(Queue queue : queues) {
            if(queue.playerUuid.equals(player.getUniqueId())) {
                return queue;
            }
        }
        return null;
    }

    public void removeQueue(Queue queue) {
        queues.remove(queue);
    }

    public void queuePlayer(String queueString, Player player) {
        if (isQueued(player)) {
            player.sendMessage(ChatUtil.clr("&7You are already in a queue! Type &c/simulation &7remove to queue for another game!"));
            return;
        }
        Queue queue = new Queue(player.getUniqueId(), this);
        addQueue(queue);
    }
}
