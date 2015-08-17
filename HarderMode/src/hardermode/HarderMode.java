package hardermode;

import java.util.List;

import hardermode.entity.*;
import hardermode.block.*;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class HarderMode extends JavaPlugin implements Listener {

	public void onEnable()
	{
		Server server = this.getServer();
		List<Recipe> recipeIterator = server.getRecipesFor(new ItemStack(Material.TORCH));
		
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
		}
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
