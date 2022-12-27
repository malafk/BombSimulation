package lol.maltest.bombsimulation;

import co.aikar.commands.PaperCommandManager;
import lol.maltest.bombsimulation.listeners.BlockListener;
import lol.maltest.bombsimulation.listeners.SimulationListener;
import lol.maltest.bombsimulation.queue.QueueManager;
import lol.maltest.bombsimulation.simulation.SimulationManager;
import lol.maltest.bombsimulation.simulation.commands.SimulationCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

public final class BombSimulation extends JavaPlugin {

    public SimulationManager simulationManager;
    public QueueManager queueManager;

    @Override
    public void onEnable() {
        this.simulationManager = new SimulationManager(this);
        this.queueManager = new QueueManager(this);
        loadCommands();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
        Bukkit.getPluginManager().registerEvents(new SimulationListener(this), this);
    }

    public void loadCommands() {
        PaperCommandManager paperCommandManager = new PaperCommandManager(this);

        paperCommandManager.registerCommand(new SimulationCommand(this));
    }
}
