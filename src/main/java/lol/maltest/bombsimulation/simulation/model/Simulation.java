package lol.maltest.bombsimulation.simulation.model;

import lol.maltest.bombsimulation.BombSimulation;
import lol.maltest.bombsimulation.util.ChatUtil;
import lol.maltest.bombsimulation.util.SimUtil;
import lombok.AllArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.*;

public class Simulation {

    private BombSimulation plugin;
    private Player simulationPlayer;
    public ArrayList<Block> blocksOfArea;
    public Collection<Entity> entitiesOfArea;
    public UUID playerUuid;

    private World world;
    public Location originalLocation;

    public ArrayList<Location> blocksPlaced = new ArrayList<>();

    // make it use ququeue

    public Simulation(BombSimulation plugin, Player simulationPlayer, ArrayList<Block> blocksOfArea, Collection<Entity> entitiesOfArea) {
        this.plugin = plugin;
        this.simulationPlayer = simulationPlayer;
        this.blocksOfArea = blocksOfArea;
        this.entitiesOfArea = entitiesOfArea;

        originalLocation = simulationPlayer.getLocation();
        world = Bukkit.getWorld("simulations");
        playerUuid = simulationPlayer.getUniqueId();
    }

    public void start() {
        handlePlayer();
        startCloning();
    }

    public void handlePlayer() {
        simulationPlayer.teleport(new Location(world, simulationPlayer.getLocation().getX(), simulationPlayer.getLocation().getY(), simulationPlayer.getLocation().getZ()));
        simulationPlayer.setGameMode(GameMode.SPECTATOR);
    }

    public void startCloning() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(int i = 0; i <= blocksOfArea.size() / 40; i++) {
                    if(blocksOfArea.size() == 1) {
                        spawnEntities();
                        cancel();
                        return;
                    }
                    Block b = blocksOfArea.get(i);
                    blocksOfArea.remove(b);
                    Location c = new Location(world, b.getX(), b.getY(), b.getZ());
                    c.getBlock().setType(b.getType(), false);
                    c.getBlock().setBlockData(b.getBlockData());
                    c.getBlock().getState().update();
                    blocksPlaced.add(c.getBlock().getLocation());
                }
                startCloning();
            }
        }.runTaskLater(plugin,1L);
    }

    private void spawnEntities() {
        for(Entity e : entitiesOfArea) {
            if(e.getType().equals(EntityType.PLAYER)) continue;
            world.spawnEntity(new Location(world, e.getLocation().getX(), e.getLocation().getY(), e.getLocation().getZ()), e.getType());
        }
        bomb();
    }

    private void bomb() {
        simulationPlayer.sendMessage(ChatUtil.clr("&cBombing the simulation"));
        World world = Bukkit.getWorld("simulations");
        Location location = new Location(world, originalLocation.getX(), originalLocation.getY(), originalLocation.getZ());
        SimUtil.spawnBombs(5, location, world);
        TNTPrimed tntPrimed = (TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT);
        tntPrimed.setFuseTicks(100);
        new BukkitRunnable() {
            @Override
            public void run() {
                simulationPlayer.sendMessage(ChatUtil.clr("&cDeleting simulation"));
                delete();
            }
        }.runTaskLater(plugin, 20 * 20);
    }

    public void delete() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(int i = 0; i <= blocksPlaced.size() / 10; i++) {
                    if(blocksPlaced.size() == 1) {
                        plugin.simulationManager.endSimulation(playerUuid, false);
                        cancel();
                        return;
                    }
                    Location b = blocksPlaced.get(i);
                    b.getBlock().setType(Material.AIR);
                    blocksPlaced.remove(b);
                    new Location(world, b.getX(), b.getY(), b.getZ()).getBlock().setType(Material.AIR, false);
                }
                delete();
            }
        }.runTaskLater(plugin,1L);
    }

}
