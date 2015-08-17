package hardermode.block;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class TorchEvents implements Listener, Runnable {

	private Plugin plugin;
	public TorchEvents(Plugin plugin)
	{
		this.plugin = plugin;
	}
	private Block torch = null;
	@EventHandler
	public void onPlace(BlockPlaceEvent evt)
	{
		if(evt.getBlock().getType() == Material.TORCH)
		{
			torch = evt.getBlock();
			Server server = evt.getPlayer().getServer();
			BukkitScheduler scheduler = server.getScheduler();
			int ticks = 36000;
			int height = evt.getBlock().getLocation().getBlockY();
			int ticklossperlevel = 400;
			if(height < 60)
			{
				int level = 60 - height;
				ticks -= ticklossperlevel * level;
			}
			scheduler.runTaskLater(plugin, this, ticks);
		}
	}

	@Override
	public void run() {
		torch = torch.getWorld().getBlockAt(torch.getLocation());
		if(torch.getType() == Material.TORCH)
		{
			torch.setType(Material.AIR);
		}
	}
}
