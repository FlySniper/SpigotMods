package hardermode.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SpiderEvents implements Listener {

	@EventHandler
	public void onTarget(EntityTargetEvent evt)
	{
		if(evt.getEntityType() == EntityType.SPIDER)
		{
			Entity target = evt.getTarget();
			Entity spider = evt.getEntity();
			LivingEntity livingtarget = null;
			if(target instanceof LivingEntity)
			{
				livingtarget = (LivingEntity)target;
			}
			else
			{
				return;
			}
			PotionEffect slow = new PotionEffect(PotionEffectType.SLOW, 80, 1);
			livingtarget.addPotionEffect(slow);
			
			Location sloc = spider.getLocation();
			Location tloc = livingtarget.getEyeLocation();
			
			Vector tmp = tloc.subtract(sloc).toVector();
			tmp.normalize();
			spider.setVelocity(tmp);
		}
	}
}
