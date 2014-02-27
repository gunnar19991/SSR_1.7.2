package ssr;


import java.io.File;

import org.apache.logging.log4j.Logger;

import ssr.config.MobBlackList;
import ssr.config.SoulConfig;
import ssr.events.SoulEvents;
import ssr.gameObjs.ObjHandler;
import ssr.utils.EntityWhitelist;
import ssr.utils.TierHandling;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SSRCore.ID, name = SSRCore.Name, version = SSRCore.Version)

public class SSRCore 
{
	@Instance("SSR")
	public static SSRCore instance;
	
	public static final String ID = "SSR";
	public static final String Name = "Soul Shards: Reborn";
	public static final String Version = "Alpha 0.7a";
	
	public static Logger SoulLog = FMLLog.getLogger();
	
	String configDir;
	long timeBegin;
	long timeEnd;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		timeBegin = System.currentTimeMillis();
		configDir = event.getModConfigurationDirectory() + "/Soul Shards Reborn/";
		SoulConfig.init(new File(configDir + "Main.cfg"));
		TierHandling.init();
		EntityWhitelist.init();
		MobBlackList.init(new File(configDir + "Entity Blacklist.cfg"));
		ObjHandler.init();
		SoulEvents.init();
		timeEnd = System.currentTimeMillis();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		//Causes game crash
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{	
		SoulLog.info("SSR: Mod Loaded successfully in "+(timeEnd - timeBegin)+" milliseconds");
	}
}
