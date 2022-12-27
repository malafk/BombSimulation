package lol.maltest.bombsimulation.simulation.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Subcommand;
import lol.maltest.bombsimulation.BombSimulation;
import lol.maltest.bombsimulation.simulation.bomb.BombType;
import lol.maltest.bombsimulation.simulation.model.Simulation;
import lol.maltest.bombsimulation.util.ChatUtil;
import lol.maltest.bombsimulation.util.SimUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandAlias("simulations")
@CommandPermission("simulations.commands.main")
public class SimulationCommand extends BaseCommand {

    private BombSimulation plugin;

    public SimulationCommand(BombSimulation plugin) {
        this.plugin = plugin;
    }

    @CommandPermission("simulations.commands.start")
    @Subcommand("start")
    public void startSimulation(Player sender, @Name("type") BombType bombType) {
        plugin.simulationManager.startSimulation(sender);
    }

    @Subcommand("create")
    public void createSimulation(Player sender) {
        plugin.queueManager.queuePlayer(sender);
        sender.sendMessage("You are now queued");
    }

    @Subcommand("leave")
    public void leaveQueue(Player sender) {
        if(plugin.queueManager.isQueued(sender)) {
            if(sender.getWorld().getName().equals("simulations")) {
                sender.sendMessage(ChatUtil.clr("Cant do that here"));
                return;
            }
            plugin.queueManager.removeQueue(plugin.queueManager.getQueue(sender));
        }
    }
}
