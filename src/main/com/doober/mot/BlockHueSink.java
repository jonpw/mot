package com.doober.mot;

import java.util.List;

import javax.annotation.Nullable;
import java.util.UUID;

import com.doober.mot.hue.HueController;
import com.doober.mot.network.ModGuiHandler;
import com.philips.lighting.model.PHLight;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHueSink extends BlockTileEntity<HueSinkTileEntity> {
	
	public BlockHueSink() {
		super(Material.ROCK, "HueSink");
       }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		HueSinkTileEntity tile = getTileEntity(world, pos);
		if ((tile.owner == null) || (tile.owner.toString() == "")) {
			return false;
		}
		
		// block is set to a local light
		if (!player.isSneaking() {				
			if (tile.owner == player.getUniqueID()) {
				MotMod.motHue.set(tile.apID, tile.lightID, !tile.isOn);
			} else {
				// TODO: control other people's lights
				// send a network message to the server, to send a message to whoever owns the block to send the hue command
				//TODO: control the server's lights? (how is that even set?)
			}
		} else {
			List<PHLight> lights = MotMod.motHue.getLights();
			PHLight newLight;
			int index = 0;
			for (int i; i<lights.size(); i++) {
				if (tile.lightID == lights.get(i).getUniqueId()) {
					index = i;
				}
			}
			newLight = lights.get((index+1) % (lights.size()+1));
			tile.lightID = newLight.getUniqueId();
			System.out.print("HUE: block set to "+tile.lightID);
		}
			
	    return true;
	}
	
	@Override
	public Class<HueSinkTileEntity> getTileEntityClass() {
		return HueSinkTileEntity.class;
	}
	
	@Nullable
	@Override
	public HueSinkTileEntity createTileEntity(World world, IBlockState state) {
		return new HueSinkTileEntity();
	}	
	
}
