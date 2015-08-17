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

public class SkeletonEvents implements Listener {

	@EventHandler
	public void onDealDamage(EntityDamageByEntityEvent evt)
	{
		if(evt.getDamager().getType() == EntityType.SKELETON)
		{
			Entity victim = evt.getEntity();
			if(victim instanceof LivingEntity)
			{
				LivingEntity livingvictim = (LivingEntity)victim;
				int duration = 60;
				int amplifier = 1;
				for(PotionEffect pe :livingvictim.getActivePotionEffects())
				{
					if(pe.getType() == PotionEffectType.SLOW)
					{
						amplifier = pe.getAmplifier() + 1;
						duration = pe.getDuration();
						break;
					}
				}
				PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, duration, amplifier);
				livingvictim.addPotionEffect(slow);
			}
		}
	}
}
