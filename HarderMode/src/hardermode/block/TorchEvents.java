package hardermode.block;

import hardermode.HarderMode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TorchEvents implements Listener, Runnable {

	private static LinkedList<Tripple<BukkitTask,Integer,Location>> tasks = new LinkedList<Tripple<BukkitTask,Integer,Location>>();
	public TorchEvents()
	{
	}
	private Tripple<BukkitTask,Integer,Location> tripple = null;
	private TorchEvents(Block t)
	{
		torch = t;
	}
	private Block torch = null;
	@EventHandler
	public void onPlace(BlockPlaceEvent evt)
	{
		if(evt.getBlock().getType() == Material.TORCH)
		{
			Block torch = evt.getBlock();
			Server server = evt.getPlayer().getServer();
			BukkitScheduler scheduler = server.getScheduler();
			//int ticks = 36000;
			int ticks = 360;
			int height = evt.getBlock().getLocation().getBlockY();
			int ticklossperlevel = 400;
			if(height < 60)
			{
				int level = 60 - height;
				ticks -= ticklossperlevel * level;
			}
			TorchEvents te = new TorchEvents(torch);
			BukkitTask bt = scheduler.runTaskLater(HarderMode.ME, te, ticks);
			Tripple<BukkitTask,Integer,Location> tripple = new Tripple<BukkitTask,Integer,Location>(bt,ticks,torch.getLocation());
			te.tripple = tripple;
			tasks.add(tripple);
		}
	}

	@Override
	public void run() {
		torch = torch.getWorld().getBlockAt(torch.getLocation());
		if(torch.getType() == Material.TORCH)
		{
			torch.setType(Material.AIR);
		}
		tasks.removeFirstOccurrence(this.tripple);
	}
	
	public static void saveSchedulerInfo() throws IOException
	{
		File file = new File("plugins"+File.separatorChar+"HarderMode"+File.separatorChar+"TorchCache");
		File dirs = new File("plugins"+File.separatorChar+"HarderMode");
		dirs.mkdirs();
		file.createNewFile();
		String toFile = "";
		for(Tripple<BukkitTask,Integer,Location> tripples : tasks)
		{
			tripples.a.cancel();
			Location loc = tripples.c;
			toFile += tripples.b+" "+loc.getBlockX()+" "+loc.getBlockY()+" "+loc.getBlockZ()+" "+loc.getWorld().getName()+System.lineSeparator();
		}
		
		FileWriter fw = new FileWriter(file);
		fw.write(toFile);
		fw.close();
	}
	
	public static void loadSchedulerInfo() throws IOException
	{
		File file = new File("plugins"+File.separatorChar+"HarderMode"+File.separatorChar+"TorchCache");
		File dirs = new File("plugins"+File.separatorChar+"HarderMode");
		dirs.mkdirs();
		file.createNewFile();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		Scanner sc = new Scanner(br);
		while(sc.hasNextInt())
		{
			int ticks = sc.nextInt();
			int X = sc.nextInt();
			int Y = sc.nextInt();
			int Z = sc.nextInt();
			String worldname = sc.nextLine().substring(1);
			
			World world = HarderMode.SERVER.getWorld(worldname);
			Block torch = world.getBlockAt(X, Y, Z);
			TorchEvents te = new TorchEvents(torch);
			BukkitTask bt = HarderMode.SERVER.getScheduler().runTaskLater(HarderMode.ME, te, ticks);
			Tripple<BukkitTask,Integer,Location> tripple = new Tripple<BukkitTask,Integer,Location>(bt,ticks,new Location(world,X,Y,Z));
			te.tripple = tripple;
			tasks.add(tripple);
		}
		sc.close();
	}
	
	private static class Tripple<A,B,C>
	{
		public A a;
		public Tripple(A a, B b,C c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public B b;
		public C c;
	}
}
