package ssr.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ssr.config.SoulConfig;
import ssr.gameObjs.ObjHandler;
import ssr.utils.EntityWhitelist;
import ssr.utils.NameTranslation;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AbsorbSpawner 
{
	@SubscribeEvent
	public void playerInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		
		if (!world.isRemote && SoulConfig.canAbsorbSpawners && player != null)
			if (player.getHeldItem() != null && player.getHeldItem().getItem() == ObjHandler.sShard)
			{
				ItemStack stack = player.getHeldItem();
				TileEntity tile = world.getTileEntity(event.x, event.y, event.z);
				TileEntityMobSpawner spawner = null;
				if (tile instanceof TileEntityMobSpawner)
					spawner = (TileEntityMobSpawner)tile;
				if (spawner != null)
				{
					String entName = spawner.func_145881_a().getEntityNameToSpawn(); //func_ is MobSpawnerBaseLogic
					String translate = NameTranslation.entityName(entName);
					if (EntityWhitelist.isEntityAccepted(translate))
					{
						if (!stack.hasTagCompound())
						{
							stack.setTagCompound(new NBTTagCompound());
							stack.stackTagCompound.setString("EntityType", "empty");
						}
						
						String nbtName = stack.stackTagCompound.getString("EntityType");
						int nbtKills = stack.stackTagCompound.getInteger("KillCount");
						
						if ((nbtName.equals("empty") || translate.equals(nbtName)) && (nbtKills != 1024 || nbtName.equals("empty")))
						{
							int totalKills = nbtKills + 200;
							totalKills = totalKills > 1024 ? 1024 : totalKills; 
							stack.stackTagCompound.setInteger("KillCount", totalKills);
							if (nbtName.equals("empty"))
							{
								stack.stackTagCompound.setString("EntityType", translate);
								stack.stackTagCompound.setString("EntityId", entName);
							}
							world.setBlockToAir(event.x, event.y, event.z);
						}
					}
				}
			}	
	}
}
