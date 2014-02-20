package ssr.events;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import ssr.gameObjs.ObjHandler;
import ssr.utils.EntityWhitelist;
import ssr.utils.InvUtils;
import ssr.utils.NameTranslation;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerKill
{
	@SubscribeEvent
	public void killEvent(LivingDeathEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayerMP && event.entityLiving instanceof EntityLiving)
		{
			EntityPlayerMP player = (EntityPlayerMP) event.source.getEntity();
			EntityLiving ent = (EntityLiving) event.entityLiving;
			String entName = NameTranslation.entityName(ent);
			if (entName.equals("Skeleton") && ent instanceof EntitySkeleton)
			{
				EntitySkeleton skele = (EntitySkeleton)ent;
				if (skele.getSkeletonType() == 1)
					entName = "Wither Skeleton";
			}
			
			if (!ent.getEntityData().getBoolean("fromSSR") && EntityWhitelist.isEntityAccepted(entName) && InvUtils.HasShard(player, entName))
			{
				ItemStack stack = InvUtils.getStack(player, entName);
				if (stack != null)
				{
					if (!stack.hasTagCompound())
					{
						stack.setTagCompound(new NBTTagCompound());
						stack.stackTagCompound.setInteger("KillCount", 0);
						stack.stackTagCompound.setInteger("Tier", 0);
						stack.stackTagCompound.setString("EntityType", entName);
						stack.stackTagCompound.setString("EntityId", EntityList.getEntityString(ent));
					}
					
					if (stack.stackTagCompound.getString("EntityType").equals("empty"))
					{
						stack.stackTagCompound.setString("EntityType", entName);
						stack.stackTagCompound.setString("EntityId", EntityList.getEntityString(ent));
					}
					
					int kills = stack.stackTagCompound.getInteger("KillCount");
					if (kills < 1024)
						stack.stackTagCompound.setInteger("KillCount", killBonus(player.getHeldItem(), kills));
				}
			}
		}
	}
	
	private int killBonus(ItemStack stack, int kills)
	{
		if (stack == null)
			return kills + 1;
		else
		{
			int result;
			int SSLevel = EnchantmentHelper.getEnchantmentLevel(ObjHandler.sStealer.effectId, stack);
			if (SSLevel == 0)
				return kills + 1;
			else
			{
				result = kills + SSLevel;
				if (result > 1024)
					return 1024;
				else return result;
			}
		}
	}
}
