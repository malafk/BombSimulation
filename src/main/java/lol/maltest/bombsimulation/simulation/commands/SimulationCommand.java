package lol.maltest.bombsimulation.simulation.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Name;
import co.aikar.commands.annotation.Subcommand;
import lol.maltest.bombsimulation.simulation.bomb.BombType;
import org.bukkit.command.CommandSender;

@CommandPermission("simulations.commands.main")
public class SimulationCommand extends BaseCommand {

    @Subcommand("start")
    public void startSimulation(CommandSender sender, @Name("type") BombType bombType) {

    }
}
