package ssr.events;

import net.minecraft.block.Block;
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
	public void createShard(PlayerInteractEvent event)
	{
		World world = event.entityPlayer.worldObj;
		EntityPlayer player = event.entityPlayer;
		if (!world.isRemote && player != null)
		{
			Block block;
			if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.diamond)
			{
				block = world.getBlock(event.x, event.y, event.z);
				if (block == Blocks.glowstone && (isFormed (world, event.x, event.y, event.z) || isFormedVertically(world, event.x, event.y, event.z)))
				{
					if (!player.capabilities.isCreativeMode)
						player.getHeldItem().stackSize -= 1;
					world.spawnEntityInWorld(new EntityItem(world, event.x, event.y, event.z, new ItemStack(ObjHandler.sShard)));
					world.setBlockToAir(event.x, event.y, event.z);
				}
			}
		}
	}
	
	private boolean isFormed(World world, int x, int y, int z)
	{

		boolean result = true;
		Block blocks1[] = {world.getBlock(x + 1, y, z), world.getBlock(x - 1, y, z), world.getBlock(x, y, z + 1), world.getBlock(x, y, z - 1)};
		Block blocks2[] = {world.getBlock(x + 2, y, z), world.getBlock(x -2 , y, z), world.getBlock(x, y, z + 2), world.getBlock(x, y, z - 2), 
				world.getBlock(x + 1, y, z + 1), world.getBlock(x + 1, y, z - 1), world.getBlock(x - 1, y, z + 1), world.getBlock(x - 1, y, z - 1)};
		result = Check(blocks1, blocks2);
	
		return result;
	}
	
	private boolean isFormedVertically(World world, int x, int y, int z)
	{
		boolean result = true;
		Block blocks1[] = {world.getBlock(x, y + 1, z), world.getBlock(x, y - 1, z), world.getBlock(x, y, z + 1), world.getBlock(x, y, z - 1)};
		Block blocks2[] = {world.getBlock(x, y + 1, z - 1), world.getBlock(x, y - 1, z - 1), world.getBlock(x, y + 1, z + 1), world.getBlock(x, y - 1, z + 1),
				world.getBlock(x, y + 2, z), world.getBlock(x, y - 2, z), world.getBlock(x, y, z + 2), world.getBlock(x, y, z - 2)};	
		result = Check(blocks1, blocks2);
		return result;
	}
	
	
	private boolean Check(Block blocks[], Block blocks2[])
	{
		boolean result = true;
		for (int i = 0; i < blocks.length - 1; i++)
		{
			int j = i + 1;
			if (blocks[i] != blocks[j] || blocks[i] != Blocks.netherrack)
			{
				result = false;
				break;
			}
		}
		if (result)
			for (int i = 0; i < blocks2.length - 1; i++)
			{
				int j = i + 1;
				if (blocks2[i] != blocks2[j] || blocks2[i] != Blocks.end_stone)
				{
					result = false;
					break;
				}
			}
		return result;
	}	
}
