package hardermode.entity;

import hardermode.HarderMode;

import org.bukkit.entity.Ghast;

public class GhastMetaRemover implements Runnable {

	Ghast ghast;
	public GhastMetaRemover(Ghast g)
	{
		ghast = g;
	}
	@Override
	public void run() {
		if(ghast.hasMetadata("Fired"))
			ghast.removeMetadata("Fired", HarderMode.ME);

	}

}
