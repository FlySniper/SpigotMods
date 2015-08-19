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
	public void onArrowDamage(EntityDamageByEntityEvent evt)
	{
		
		if(evt.getDamager().getType() == EntityType.ARROW)
		{
			Entity proj = evt.getDamager();
			if(proj instanceof Arrow)
			{
				Arrow arrow = (Arrow)proj;
				if( ! (arrow.getShooter() instanceof Skeleton))
				{
					return;
				}
				//Skeleton skeleton = (Skeleton)arrow.getShooter();
				int duration = 600;
				int amplifier = 1;
				Entity target = evt.getEntity();
				if(!(target instanceof LivingEntity))
				{
					return;
				}
				LivingEntity livingtarget = (LivingEntity) target;
				PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, duration, amplifier);
				livingtarget.addPotionEffect(slow);
			}
		}
	}
}
