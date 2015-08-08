package worldtraveler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Config {

	public int worlds = 5;
	public int xmax = 1000;
	public int zmax = 1000;
	
	public String worldnormal = "world";
	public String worldnether = "world_nether";
	public Config()
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public void write(String filename)
	{
		File f = new File(filename);
		if(f.isDirectory())
		{
			return;
		}
		JSONObject obj = new JSONObject();

	    obj.put("worlds",new Integer(worlds));
	    obj.put("xmax",new Integer(xmax));
	    obj.put("zmax",new Integer(xmax));
	    obj.put("worldnormal",worldnormal);
	    obj.put("worldnether",worldnether);

	    StringWriter out = new StringWriter();
	    try {
	    	f.getParentFile().mkdirs();
			f.createNewFile();
			obj.writeJSONString(out);
		    String jsonText = out.toString();
			FileWriter fw = new FileWriter(f);
			fw.write(jsonText);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int read(String filename)
	{
		File f = new File(filename);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String JSONText = "";
			for(String s = br.readLine(); s!=null;s=br.readLine())
			{
				JSONText += s+"\n";
			}
			JSONParser parser = new JSONParser();
			Object o = parser.parse(JSONText);
			JSONObject obj = (JSONObject)o;
			worlds = ((Long)obj.get("worlds")).intValue();
			xmax = ((Long)obj.get("xmax")).intValue();
			zmax = ((Long)obj.get("zmax")).intValue();
			worldnormal = (String)obj.get("worldnormal");
			worldnether = (String)obj.get("worldnether");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return -1;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return -2;
		}
		return 0;
	}
}
