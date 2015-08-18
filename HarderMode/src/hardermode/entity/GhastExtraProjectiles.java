package hardermode.entity;

import org.bukkit.entity.Ghast;
import org.bukkit.entity.LargeFireball;
import org.bukkit.util.Vector;

public class GhastExtraProjectiles implements Runnable {

	Ghast ghast;
	Vector vector;
	public GhastExtraProjectiles(Ghast g,Vector v)
	{
		ghast = g;
		vector =v;
	}
	@Override
	public void run() {
		ghast.launchProjectile(LargeFireball.class,vector);

	}

}
