package hardermode.entity;

import java.util.Random;

import org.bukkit.World;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ZombiePigmenEvents implements Listener {

	@EventHandler
	public void onSpawn(EntitySpawnEvent evt)
	{
		Entity pingzombie = evt.getEntity();
		if(pingzombie.getType() == EntityType.PIG_ZOMBIE)
		{
			if(pingzombie instanceof LivingEntity)
			{
				Random rand = new Random(100);
				if(rand.nextInt() <= 4)
				{
					evt.setCancelled(true);
					World world = evt.getLocation().getWorld();
					Entity blaze = world.spawnEntity(evt.getLocation(), EntityType.BLAZE);
					if(blaze instanceof Blaze)
					{
						Blaze livingblaze = (Blaze)blaze;
						livingblaze.setMaxHealth(livingblaze.getMaxHealth()+10);
					}
				}
			}
		}
	}
}
