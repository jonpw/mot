package com.doober.mot.network;

import com.doober.mot.HueManagerTileEntity;
import com.doober.mot.MotManagerTileEntity;
import com.doober.mot.client.gui.GuiManager;
import com.doober.mot.container.HueManagerContainer;
import com.doober.mot.container.MotManagerContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler {

	public static final int MANAGER_GUI = 0;
	public static final int HUEMANAGER_GUI = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
	    if (ID == MANAGER_GUI)
	    {
	        BlockPos pos = new BlockPos(x, y, z);
	        TileEntity te = world.getTileEntity(pos);
	        if (te instanceof MotManagerTileEntity) {
	            MotManagerTileEntity containerTileEntity = (MotManagerTileEntity) te;
	            return new GuiManager(containerTileEntity, new MotManagerContainer(player.inventory, containerTileEntity));
	        }
	    }
	    /*if (ID == HUEMANAGER_GUI)
	    {
	        BlockPos pos = new BlockPos(x, y, z);
	        TileEntity te = world.getTileEntity(pos);
	        if (te instanceof HueManagerTileEntity) {
	            HueManagerTileEntity containerTileEntity = (HueManagerTileEntity) te;
	            return new GuiManager(containerTileEntity, new HueManagerContainer(player.inventory, containerTileEntity));
	        }
	    }*/

	    return null;
	}
}