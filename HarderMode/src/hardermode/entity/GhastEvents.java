package hardermode.entity;

import hardermode.HarderMode;

import java.util.Random;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class GhastEvents implements Listener {

	@EventHandler
	public void spawnProjectile(ProjectileLaunchEvent evt)
	{
		if(evt.getEntityType() == EntityType.FIREBALL)
		{
			Projectile proj = evt.getEntity();
			ProjectileSource source = proj.getShooter();
			if(source instanceof Ghast)
			{
				Ghast ghast = (Ghast)source;
				//System.out.println(ghast.getMetadata("Fired"));
				if(ghast.hasMetadata("Fired"))
				{
					//System.out.println("Fired");
					return;
				}
				Random r = new Random(1);
				if(r.nextInt()==0)
				{
					Random variance = new Random(2000);
					Vector vel = proj.getVelocity();
					Vector originalvel = proj.getVelocity();
					double x = vel.getX()+(variance.nextInt()-1000)/5000.0;
					double y = vel.getY()+(variance.nextInt()-1000)/5000.0;
					double z = vel.getZ()+(variance.nextInt()-1000)/5000.0;
					
					vel.setX(x);
					vel.setY(y);
					vel.setZ(z);
					proj.setVelocity(vel); //First Projectile
					
					vel = originalvel;
					x = vel.getX()+(variance.nextInt()-1000)/5000.0;
					y = vel.getY()+(variance.nextInt()-1000)/5000.0;
					z = vel.getZ()+(variance.nextInt()-1000)/5000.0;
					
					Vector v = new Vector(x,y,z);
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastExtraProjectiles(ghast,originalvel), 1);//Second Projectile
					vel = originalvel;
					x = vel.getX()+(variance.nextInt()-1000)/5000.0;
					y = vel.getY()+(variance.nextInt()-1000)/5000.0;
					z = vel.getZ()+(variance.nextInt()-1000)/5000.0;
					
					v = new Vector(x,y,z);
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastExtraProjectiles(ghast,originalvel), 2);//Third Projectile
					ghast.setMetadata("Fired", new FixedMetadataValue(HarderMode.ME, "true"));
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastMetaRemover(ghast), 40);
				}
				else
				{
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastExtraProjectiles(ghast,proj.getVelocity()), 33);
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastExtraProjectiles(ghast,proj.getVelocity()), 12);
					ghast.setMetadata("Fired", new FixedMetadataValue(HarderMode.ME, "true"));
					HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, new GhastMetaRemover(ghast), 40);
				}
			}
		}
	}
}
