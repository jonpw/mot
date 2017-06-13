package com.doober.mot;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;

public class HueManagerTileEntity extends TileEntity {
	
	public String apID;
	public String lightID;
	public boolean isOn;
	public UUID owner;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("apID")) {
        	// should check if changed and reconnect?
            this.apID = (String) compound.getTag("apID").toString();
        }
        if (compound.hasKey("lightID")) {
            this.lightID = (String) compound.getTag("lightID").toString();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("apID", new NBTTagString(this.apID));
        compound.setTag("lightID", new NBTTagString(this.lightID));
        return super.writeToNBT(compound);
    }



    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

}
