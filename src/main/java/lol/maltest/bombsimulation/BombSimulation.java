package lol.maltest.bombsimulation;

import co.aikar.commands.PaperCommandManager;
import lol.maltest.bombsimulation.simulation.commands.SimulationCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BombSimulation extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new SimulationCommand());
    }
}
