package ssr.gameObjs;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ssr.config.SoulConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class SoulCage extends Block implements ITileEntityProvider
{
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	
	protected SoulCage() 
	{
		super(Material.rock);
		this.setBlockName("soul_cage");
		this.setCreativeTab(ObjHandler.sTab);
		this.blockHardness = 3.0F;
		this.blockResistance = 3.0F;
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			if (world.getBlockMetadata(x, y, z) != 0)
				world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		CageTile tile = (CageTile) world.getTileEntity(x, y, z);
		if (tile != null && (tile.tier != 0 && SoulConfig.enableRS[tile.tier - 1]))
		{
			if (world.isBlockIndirectlyGettingPowered(x, y, z))
				tile.isPowered = true;
			else
				tile.isPowered = false;
		}else if (tile != null)System.out.println(tile.tier);		
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			if (player.getHeldItem() != null)
			{
				CageTile tile = (CageTile) world.getTileEntity(x, y, z);

				if (player.getHeldItem().getItem() == ObjHandler.sShard && tile != null)
				{
					ItemStack stack = player.getHeldItem();
					if (stack.hasTagCompound())
					{
						NBTTagCompound nbt = stack.stackTagCompound;
						int tier = nbt.getInteger("Tier");
						String ent = nbt.getString("EntityType");
						String entId = nbt.getString("EntityId");
						if (tier > 0 && !ent.equals("empty") && !entId.equals("empty") && tile.tier == 0)
						{
							tile.entName = ent;
							tile.entId = entId;
							tile.tier = tier;
							if (nbt.getBoolean("HasItem"))
								tile.heldItem = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Item"));
							if (!player.capabilities.isCreativeMode)
								stack.stackSize--;
							world.setBlockMetadataWithNotify(x, y, z, 1, 2);
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs cTab, List list)
	{
		for (int i = 0; i < 3; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		String[] names = {"soulCage", "cageUnlit", "cageLit"};
		icons = new IIcon[names.length];
		for (int i = 0; i < names.length; i++)
			icons[i] = iconRegister.registerIcon("ssr:"+names[i]);
	}
	  
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return icons[meta];
	} 
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new CageTile();
	}
}
