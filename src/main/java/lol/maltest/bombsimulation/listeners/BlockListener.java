package lol.maltest.bombsimulation.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import java.util.Objects;

public class BlockListener implements Listener {

    @EventHandler
    public void onPhysics(BlockPhysicsEvent e) {
        if(e.getBlock().getWorld().getName().equals("simulations")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(ExplosionPrimeEvent e) {
        if(e.getEntity().getType() == EntityType.PRIMED_TNT) {
            e.getEntity().getLocation().createExplosion(150);
        }
    }
}
