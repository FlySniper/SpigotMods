package worldtraveler;

import java.util.Random;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldTraveler extends JavaPlugin implements Listener {
	
	public static int worlds = 5;
	public static int xmax = 1000;
	public static int zmax = 1000;
	public static World [] world_list = new World[worlds];
	public static World [] nether_list = new World[worlds];
	Random random = new Random();
	public void onEnable()
	{
		this.getServer().getPluginManager().registerEvents(this, this);
		Server server = this.getServer();
		String worldName = "World";
		worldName = server.getWorlds().size() == 0 ? "World" :server.getWorlds().get(0).getName();
		
		for(int i = 1;i<=worlds;++i)
		{
			WorldCreator wc = new WorldCreator(worldName+"-"+i);
			wc.type(WorldType.NORMAL);
			wc.seed(random.nextLong());
			System.err.println(wc.generatorSettings());
			world_list[i - 1] = server.createWorld(wc);
			
			WorldCreator wcn = new WorldCreator(worldName+"-"+i+"_nether");
			wcn.type(WorldType.NORMAL);
			wcn.environment(Environment.NETHER);
			wcn.seed(random.nextLong());
			nether_list[i - 1] = server.createWorld(wcn);
		}
	}
	
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onPlayerTeleport(PlayerPortalEvent evt)
	{
		if(evt.getCause().equals(TeleportCause.NETHER_PORTAL) && evt.getPlayer().getWorld().getEnvironment() == Environment.NETHER)
		{
			int index = random.nextInt(worlds);
			Location l = new Location(world_list[index], random.nextLong()%xmax, 255, random.nextLong()%zmax);
			l.setY(Math.max(world_list[index].getHighestBlockYAt(l),world_list[index].getSeaLevel()));
			Location teleloc = evt.getPortalTravelAgent().setSearchRadius(50).findOrCreate(l);
			evt.setTo(teleloc);
			//evt.getPlayer().teleport(l);
			System.err.println(l);
		}
		else if(evt.getCause().equals(TeleportCause.NETHER_PORTAL) && evt.getPlayer().getWorld().getEnvironment() == Environment.NORMAL)
		{
			int index = random.nextInt(worlds);
			Location l = new Location(nether_list[index], random.nextLong()%xmax, 255, random.nextLong()%zmax);
			l.setY(Math.max(nether_list[index].getHighestBlockYAt(l),nether_list[index].getSeaLevel()));
			Location teleloc = evt.getPortalTravelAgent().setSearchRadius(50).findOrCreate(l);
			evt.setTo(teleloc);
			//evt.getPlayer().teleport(l);
			System.err.println(l);
		}
	}
}
