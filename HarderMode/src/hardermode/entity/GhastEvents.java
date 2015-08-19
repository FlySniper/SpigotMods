package hardermode.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class GhastEvents implements Listener {

	@EventHandler
	public void onProjectileHit(ProjectileHitEvent evt)
	{
		if(evt.getEntityType() == EntityType.FIREBALL)
		{
			Projectile proj = evt.getEntity();
			ProjectileSource source = proj.getShooter();
			if(source instanceof Ghast && proj instanceof Fireball)
			{
				//System.out.println(ghast.getMetadata("Fired"));
				Fireball f = (Fireball)proj;
				
				f.getWorld().createExplosion(f.getLocation(), 3.0f, true);
				//Vector vel = f.getDirection().clone();
				//vel = vel.multiply(1.02);
				//f.setDirection(vel);
				
			}
		}
	}
}
