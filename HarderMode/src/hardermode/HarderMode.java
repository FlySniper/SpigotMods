package hardermode;


import hardermode.entity.*;
import hardermode.block.*;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HarderMode extends JavaPlugin implements Listener {

	public static Server SERVER = null;
	public static HarderMode ME = null;
	public void onEnable()
	{
		SERVER = this.getServer();
		ME = this;
/*		List<Recipe> recipeIterator = server.getRecipesFor(new ItemStack(Material.TORCH));
		
		server.clearRecipes();
		for(Recipe r : recipeIterator)
		{
			ItemStack res = r.getResult();
			if(res.getType() == Material.TORCH && res.getAmount() == 4)
			{
				ShapedRecipe torchrecipe = new ShapedRecipe(new ItemStack(Material.TORCH,16));
				if(r instanceof ShapedRecipe)
				{
					String [] shape = ((ShapedRecipe)r).getShape();
					torchrecipe.shape(shape);
				}
				server.addRecipe(torchrecipe);
				continue;
			}
			server.addRecipe(r);
		}*/
		PluginManager pluginmanager = SERVER.getPluginManager();
		pluginmanager.registerEvents(this, this);
		pluginmanager.registerEvents(new ZombieEvents(),this);
		pluginmanager.registerEvents(new SkeletonEvents(),this);
		pluginmanager.registerEvents(new SpiderEvents(),this);
		pluginmanager.registerEvents(new CreeperEvents(),this);
		pluginmanager.registerEvents(new ZombiePigmenEvents(),this);
		pluginmanager.registerEvents(new TorchEvents(),this);

	}
	
	public void onDisable()
	{
		
	}
	
	@EventHandler
    public void craftItem(PrepareItemCraftEvent evt)
	{
		Recipe r = evt.getRecipe();
		if(r.getResult().getType() == Material.TORCH)
		{
			ItemStack res = r.getResult();
			res.setAmount(16);
			evt.getInventory().setResult(res);
		}
	}
}
