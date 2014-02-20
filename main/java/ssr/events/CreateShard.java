package ssr.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import ssr.gameObjs.ObjHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CreateShard 
{
	@SubscribeEvent
	public void pInteract(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		if (world != null && player != null && !world.isRemote) 
			if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.diamond)
			{
				int x = event.x;
				int y = event.y;
				int z = event.z;
				if (isFormed(world, x, y, z))
				{
					if (!player.capabilities.isCreativeMode)
						player.getHeldItem().stackSize--;
					world.setBlockToAir(x, y, z);
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(ObjHandler.sShard)));
				}
			}
	}
	
	private boolean isFormed(World world, int x, int y, int z)
	{
		boolean result = true;
		if (world.getBlock(x, y, z) == Blocks.glowstone)
		{
			int[] pos = {1, -1, 1, -1};
			for (int i = 0; i < 4; i++)
			{
				int newX = x;
				int newZ = z;
				if (i < 2)
					newX += pos[i];
				else newZ += pos[i];
				if (world.getBlock(newX, y, newZ) != Blocks.netherrack)
				{
					result = false;
					break;
				}
			}
			int[] pos2 = {2, -2, 2, -2, 1, -1, 1, -1};
			for (int i = 0; i < 8; i++)
			{
				int newX = x;
				int newZ = z;
				if (i < 2)
					newX += pos2[i];
				else if (i >= 2 && i < 4)
					newZ += pos2[i];
				else if (i >= 4 && i < 6)
				{
					newX += pos2[i];
					newZ += pos2[i];
				}
				else
				{
					newX += pos2[i];
					newZ -= pos2[i];
				}
				if (world.getBlock(newX, y, newZ) != Blocks.end_stone)
				{
					result = false;
					break;
				}
			}
		} else result = false;
		return result;
	}
}
