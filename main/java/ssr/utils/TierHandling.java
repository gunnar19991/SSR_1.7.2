package ssr.utils;

public class TierHandling
{
	private final static int[] min = {0,64,128,256,512,1024};
	private final static int[] max = {63,127,255,511,1023,1024};
	
	public static int getMin(int tier)
	{
		return min[tier];
	}
	
	public static int getMax(int tier)
	{
		return max[tier];
	}
	
	public static boolean isInBounds(int tier, int kills)
	{
		boolean result = true;
		if (kills < min[tier] || kills > max[tier])
			result = false;
		return result;
	}
	
	public static int updateTier(int kills)
	{
		int result = 0;
		for (int i = 0; i < min.length; i++)
			if (kills >= min[i] && kills <= max[i])
			{
				result = i;
				break;
			}
		return result;
	}
	
	public static int updateKills(int tier)
	{
		return getMin(tier); 
	}
}
