package ssr.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import ssr.SSRCore;

public class SoulConfig 
{
	/**Not implemented for 1.7.2 version**/
	public static boolean allowModMobs;
	
	public static boolean disallowMobs;
	public static boolean canAbsorbSpawners;
	public static int soulStealerID;
	public static int soulStealerWeight;
	public static int maxNumSpawns;
	public static int coolDown[] = new int[5];
	public static int numMobs[] = new int[5];
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		try
		{
			config.load();
			disallowMobs = config.get("Misc", "Set soul shards to accept only peaceful mobs (will be ignored if set to allow non vanilla mobs)", false).getBoolean(false);
			canAbsorbSpawners = config.get("Misc", "Allow levelling up shards by absorbing vanilla spawners", true).getBoolean(true);
			maxNumSpawns = config.get("Misc", "The max amount of mobs spawned by Soul Cages that can be alive at once (setting this to 0 sets it to unlimited)", 80).getInt(80);
			allowModMobs = config.get("Misc", "Allow non vanilla mobs (NOT YET IMPLEMENTED FOR 1.7.2)", false).getBoolean(false);
			soulStealerID = config.get("IDs", "Soul Stealer enchant ID", 85).getInt();
			soulStealerWeight = config.get("Misc", "The weight (probability) of the Soul Stealer enchant", 5).getInt(5);			
			coolDown[0] = config.get("Tier 1 Settings", "Cool-down (in seconds)", 20).getInt(20);
			numMobs[0] = config.get("Tier 1 Settings", "Number of mobs to spawn", 2).getInt(2);
			coolDown[1] = config.get("Tier 2 Settings", "Cool-down (in seconds)", 10).getInt(10);
			numMobs[1] = config.get("Tier 2 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[2] = config.get("Tier 3 Settings", "Cool-down (in seconds)", 5).getInt(5);
			numMobs[2] = config.get("Tier 3 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[3] = config.get("Tier 4 Settings", "Cool-down (in seconds)", 5).getInt(5);
			numMobs[3] = config.get("Tier 4 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[4] = config.get("Tier 5 Settings", "Cool-down (in seconds)", 2).getInt(2);
			numMobs[4] = config.get("Tier 5 Settings", "Number of mobs to spawn", 6).getInt(6);
			
			if (maxNumSpawns > 150)
				maxNumSpawns = 80;
			
			for (int i = 0; i < coolDown.length; i++)
			{
				if (coolDown[i] < 2)
					coolDown[i] = 2;
				if (coolDown[i] > 60)
					coolDown[i] = 60;
			}
			
			for (int i = 0; i < numMobs.length; i++)
			{
				if (numMobs[i] < 1)
					numMobs[i] = 1;
				if (numMobs[i] > 6)
					numMobs[i] = 6;
			}
			
			if (soulStealerWeight > 10 || soulStealerWeight < 1)
				soulStealerWeight = 5;
			
			SSRCore.SoulLog.info("SSR: Loaded main config file.");
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
