package lol.maltest.bombsimulation.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;

import java.util.*;

public class SimUtil {
    public static ArrayList<Block> getBlocksAround(Location start) {
        int radius = 30;
        ArrayList<Block> blocks = new ArrayList<>();
        for(double x = start.getX() - radius; x <= start.getX() + radius; x++){
            for(double y = start.getY() - radius; y <= start.getY() + radius; y++){
                for(double z = start.getZ() - radius; z <= start.getZ() + radius; z++){
                    Location loc = new Location(start.getWorld(), x, y, z);
                    if(loc.getBlock().getType().toString().contains("AIR")) continue;
                    Block b = loc.getBlock();
                    blocks.add(b);
                }
            }
        }
        return blocks;
    }

    public static Collection<Entity> getEntities(Location start) {
        return start.getWorld().getNearbyEntities(start, 100, 100, 100);
    }

    public static void spawnBombs(int amount, Location center, World world) {
        int radius = 30;
        Random random = new Random();

        for (int i = 0; i < amount; i++) {
            double x = random.nextDouble() * radius * 2 - radius;
            double z = random.nextDouble() * radius * 2 - radius;
            Location location = center.clone().add(x, 0, z);
            location.setY(world.getHighestBlockYAt(location) + 10);
            TNTPrimed tntPrimed = (TNTPrimed) world.spawnEntity(location, EntityType.PRIMED_TNT);
            tntPrimed.setFuseTicks(100);
        }
    }
}
