package com.doober.mot;

import java.util.Map;

import com.doober.mot.manager.MotManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class MotManagerTileEntity extends TileEntity implements ITickable {
	
	public static final int SIZE = 9;
	public String server;
	public String port;

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
	

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("server")) {
        	// should check if changed and reconnect?
            this.server = (String) compound.getTag("server").toString();
        }
        if (compound.hasKey("portr")) {
            this.port = (String) compound.getTag("port").toString();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("server", new NBTTagString(this.server));
        compound.setTag("port", new NBTTagString(this.port));
        return compound;
    }



    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }



}
