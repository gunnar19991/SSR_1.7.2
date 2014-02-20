package ssr.gameObjs;

import net.minecraft.tileentity.TileEntity;
import ssr.SSRCore;
import ssr.config.SoulConfig;
import ssr.creativeTab.SoulTab;
import ssr.enchants.SoulStealer;
import ssr.gameObjs.items.CageItem;
import ssr.gameObjs.items.SoulShard;
import cpw.mods.fml.common.registry.GameRegistry;


public class ObjHandler
{
	public static final SoulStealer sStealer = new SoulStealer(SoulConfig.soulStealerID, SoulConfig.soulStealerWeight);
	public static SoulTab sTab = new SoulTab();
	public static SoulCage sCage = new SoulCage();
	public static SoulShard sShard = new SoulShard();
	
	public static void init()
	{
		SSRCore.SoulLog.info("SSR: Registering game objects...");
		GameRegistry.registerBlock(sCage, CageItem.class, sCage.getUnlocalizedName());
		GameRegistry.registerItem(sShard, sShard.getUnlocalizedName());
		TileEntity.addMapping(CageTile.class, "cage_tile");
		SSRCore.SoulLog.info("SSR: Done!");
	}
}
