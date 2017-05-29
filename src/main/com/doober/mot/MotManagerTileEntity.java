package com.doober.mot;

import java.util.Map;

import com.doober.mot.manager.MotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MotManagerTileEntity extends TileEntity implements ITickable {

	public void update() {
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worlds[0];
		//System.out.println("1");
		if (!world.isRemote)
		{
			//System.out.println("2");
			for(Map.Entry<String, String> entry : MotManager.datamap.entrySet())
			{
				//System.out.println("3");
				// will crash if topic not perfectly formed
				String key = entry.getKey();
				String [] strpos, bits;
				bits = key.split("/");
				strpos = bits[bits.length-1].split(",");
				BlockPos pos = new BlockPos(Integer.parseInt(strpos[0]), Integer.parseInt(strpos[1]), Integer.parseInt(strpos[2]));
				System.out.println(Integer.toString(Integer.parseInt(strpos[0]))+':'+Integer.toString(Integer.parseInt(strpos[1]))+':'+Integer.toString(Integer.parseInt(strpos[2])));
				world.scheduleUpdate(pos, world.getBlockState(pos).getBlock(), 4);
			}			
		}
	}


}
