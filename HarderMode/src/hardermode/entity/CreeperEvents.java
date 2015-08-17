package hardermode.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CreeperEvents implements Listener {

	@EventHandler
	public void onSpawn(EntitySpawnEvent evt)
	{
		Entity creeper = evt.getEntity();
		if(creeper.getType() == EntityType.CREEPER)
		{
			if(creeper instanceof LivingEntity)
			{
				LivingEntity livingcreeper = (LivingEntity)creeper;
				PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 600, 1);
				livingcreeper.addPotionEffect(speed);
				PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, 600, 1);
				livingcreeper.addPotionEffect(jump);
			}
		}
	}
}
