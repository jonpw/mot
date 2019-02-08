package com.doober.mot;

import java.util.UUID;

import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;

public class HueSinkTileEntity extends TileEntity {
	
	public String lightID;
	public PHLight light;
	public PHLightState lightStateOn;
	public PHLightState lightStateOff;
	public boolean isOn;
	public UUID owner;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("lightID")) {
            this.lightID = (String) compound.getTag("lightID").toString();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("lightID", new NBTTagString(this.lightID));
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }
    
    // only the clients should call this
    public void setOn(boolean newOn) {
    	this.isOn = newOn;
    	if (owner == Minecraft.getMinecraft().player.getUniqueID()) {
    		System.out.println("HUE: Settting your light");
    		MotMod.motHue.set(lightID, this.isOn? this.lightStateOn: this.lightStateOff);
    	} else {
    		System.out.println("HUE: wasn't your light");
    	}
    }

}
