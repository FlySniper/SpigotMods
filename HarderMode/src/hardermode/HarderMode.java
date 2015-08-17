package hardermode;

import hardermode.entity.*;
import hardermode.block.*;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HarderMode extends JavaPlugin implements Listener {

	public void onEnable()
	{
		Server server = this.getServer();
		PluginManager pluginmanager = server.getPluginManager();
		pluginmanager.registerEvents(this, this);
		pluginmanager.registerEvents(new ZombieEvents(),this);
		pluginmanager.registerEvents(new SkeletonEvents(),this);
		pluginmanager.registerEvents(new SpiderEvents(),this);
		pluginmanager.registerEvents(new CreeperEvents(),this);
		pluginmanager.registerEvents(new ZombiePigmenEvents(),this);
		pluginmanager.registerEvents(new TorchEvents(this),this);
	}
	
	public void onDisable()
	{
		
	}
}
