package ssr.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraftforge.common.config.Configuration;
import ssr.SSRCore;
import ssr.utils.EntityWhitelist;

public class MobBlackList 
{
	public static HashMap<String, Boolean> bList = new HashMap(); 
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		try
		{
			config.load();
			List<String>list = new ArrayList(EntityWhitelist.peacefuls);
			list.addAll(EntityWhitelist.mobs);
			Iterator<String>iter = list.iterator();
			while(iter.hasNext())
			{
				String name = iter.next();
				boolean val = config.get("Entity Blacklist", name, false).getBoolean(false);
				if (val)
					bList.put(name, true);
			}
			SSRCore.SoulLog.info("SSR: Loaded Entity Blacklist config file.");
		}
		catch(Exception e)
		{
			SSRCore.SoulLog.fatal("Soul-Shards Reborn had a problem loading it's configuration files.");
			e.printStackTrace();
		}
		finally
		{
			if (config.hasChanged())
				config.save();
		}
	}
}
