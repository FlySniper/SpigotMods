package hardermode.block;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
public class TorchEvents implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent evt)
	{
		if(!evt.isCancelled()&&evt.getBlock().getType() == Material.TORCH)
		{
			Block torch = evt.getBlock();
			World world = torch.getWorld();
			if(torch.getLocation().getBlockY() <= 30 && world.getEnvironment() == Environment.NORMAL)
			{
				evt.setCancelled(true);
			}
		}
	}
}
