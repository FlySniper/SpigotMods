package hardermode.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
//import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkeletonEvents implements Listener {

	@EventHandler
	public void onDealDamage(ProjectileHitEvent evt)
	{
		
		if(evt.getEntityType() == EntityType.ARROW)
		{
			Entity proj = evt.getEntity();
			if(proj instanceof Arrow)
			{
				Arrow arrow = (Arrow)proj;
				if( ! (arrow.getShooter() instanceof Skeleton))
				{
					return;
				}
				Skeleton skeleton = (Skeleton)arrow.getShooter();
				int duration = 60;
				int amplifier = 1;
				LivingEntity target = skeleton.getTarget();
				for(PotionEffect pe :target.getActivePotionEffects())
				{
					if(pe.getType() == PotionEffectType.SLOW)
					{
						amplifier = pe.getAmplifier() + 1;
						duration = pe.getDuration();
						break;
					}
				}
				PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, duration, amplifier);
				target.addPotionEffect(slow);
			}
		}
	}
}
