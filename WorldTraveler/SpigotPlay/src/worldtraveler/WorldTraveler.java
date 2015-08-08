package worldtraveler;

import java.io.File;
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
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class WorldTraveler extends JavaPlugin implements Listener {
	
	public  World [] world_list;
	public  World [] nether_list;
	Random random = new Random();
	Config config = new Config();
	public void onEnable()
	{
		//this.getServer().getPluginManager().registerEvents(this, this);
		int success = config.read("plugins"+File.separatorChar+"WorldTraveler"+File.separatorChar+"config.json");
		if(success > 0)
		{
			config = new Config();
			config.write("plugins"+File.separatorChar+"WorldTraveler"+File.separatorChar+"config.json");
		}
		if(success < 0)
		{
			System.err.println("World Traveler was unable to load: Bad config.json file");
			return;
		}
		nether_list = new World[config.worlds];
		world_list = new World[config.worlds];
		Server server = this.getServer();
		if(server.getWorlds().size() == 0)
		{
			return;
		}
		World normal = server.getWorld(config.worldnormal);
		if(normal == null)
		{
			normal = server.getWorlds().get(0);
		}
		String worldName = normal.getName();
		world_list[config.worlds - 1] = normal;
		if(server.getWorlds().size() == 1)
		{
			WorldCreator wc = new WorldCreator(worldName+"_nether");
			wc.type(WorldType.NORMAL);
			wc.seed(normal.getSeed());
			wc.environment(Environment.NETHER);
			nether_list[config.worlds - 1] = server.createWorld(wc);
		}
		
		for(int i = 1;i<config.worlds;++i)
		{
			WorldCreator wc = new WorldCreator(worldName+"-"+i);
			wc.type(WorldType.NORMAL);
			wc.seed(random.nextLong());
			world_list[i - 1] = server.createWorld(wc);
			
			WorldCreator wcn = new WorldCreator(worldName+"-"+i+"_nether");
			wcn.type(WorldType.NORMAL);
			wcn.environment(Environment.NETHER);
			wcn.seed(random.nextLong());
			nether_list[i - 1] = server.createWorld(wcn);
		}
		this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void onDisable()
	{
		
	}
	
	@EventHandler
	public void onPlayerEnterPortal(PlayerPortalEvent evt)
	{
		if(evt.getCause().equals(TeleportCause.NETHER_PORTAL) && evt.getPlayer().getWorld().getEnvironment() == Environment.NETHER)
		{
			int index = random.nextInt(config.worlds);
			Location l = new Location(world_list[index], random.nextLong()%config.xmax, 255, random.nextLong()%config.zmax);
			l.setY(Math.max(world_list[index].getHighestBlockYAt(l),world_list[index].getSeaLevel()));
			Location teleloc = evt.getPortalTravelAgent().setSearchRadius(50).findOrCreate(l);
			evt.setTo(teleloc);
			//evt.getPlayer().teleport(l);
			System.err.println(l);
		}
		else if(evt.getCause().equals(TeleportCause.NETHER_PORTAL) && evt.getPlayer().getWorld().getEnvironment() == Environment.NORMAL)
		{
			int index = random.nextInt(config.worlds);
			Location l = new Location(nether_list[index], random.nextLong()%config.xmax, 255, random.nextLong()%config.zmax);
			l.setY(Math.max(nether_list[index].getHighestBlockYAt(l),nether_list[index].getSeaLevel()));
			Location teleloc = evt.getPortalTravelAgent().setSearchRadius(50).findOrCreate(l);
			evt.setTo(teleloc);
			//evt.getPlayer().teleport(l);
			System.err.println(l);
		}
	}
	
	@EventHandler
	public void onPlayerSpawn(PlayerRespawnEvent evt)
	{
		int index = random.nextInt(config.worlds);
		Location l = new Location(world_list[index], random.nextLong()%config.xmax, 255, random.nextLong()%config.zmax);
		l.setY(Math.max(world_list[index].getHighestBlockYAt(l),world_list[index].getSeaLevel()));
		evt.setRespawnLocation(l);
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerSpawnLocationEvent evt)
	{
		long lastplayed = evt.getPlayer().getLastPlayed();
		if(lastplayed == 0)
		{
			int index = random.nextInt(config.worlds);
			Location l = new Location(world_list[index], random.nextLong()%config.xmax, 255, random.nextLong()%config.zmax);
			l.setY(Math.max(world_list[index].getHighestBlockYAt(l),world_list[index].getSeaLevel()));
			evt.setSpawnLocation(l);
		}
	}
}
