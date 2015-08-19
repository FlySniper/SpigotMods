package hardermode.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
//import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieEvents implements Listener {

	@EventHandler
	public void onDealDamage(EntityDamageByEntityEvent evt)
	{
		if(evt.getDamager().getType() == EntityType.ZOMBIE)
		{
			Entity victim = evt.getEntity();
			if(victim instanceof LivingEntity)
			{
				LivingEntity livingvictim = (LivingEntity)victim;
				PotionEffect weakness = new PotionEffect(PotionEffectType.WEAKNESS, 600, 1);
				livingvictim.addPotionEffect(weakness);
			}
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent evt)
	{
		if(evt.getEntityType() == EntityType.ZOMBIE)
		{
			Entity zombie = evt.getEntity();
			if(zombie instanceof LivingEntity)
			{
				LivingEntity livingzombie = (LivingEntity)zombie;
				PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 600, 1);
				PotionEffect res = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1);
				livingzombie.addPotionEffect(speed);
				livingzombie.addPotionEffect(res);
			}
		}
	}
	
}
