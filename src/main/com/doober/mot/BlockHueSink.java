package com.doober.mot;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;
import java.util.UUID;

import com.doober.mot.hue.HueController;
import com.doober.mot.manager.MotManager;
import com.doober.mot.network.ModGuiHandler;
import com.philips.lighting.model.PHLight;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHueSink extends BlockTileEntity<HueSinkTileEntity> {
	
	public BlockHueSink(String name) {
		super(Material.ROCK, name);
       }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		HueSinkTileEntity tile = getTileEntity(world, pos);		
		// block is set to a local light
		if (!player.isSneaking()) {				
			if (tile.owner == player.getUniqueID()) {
				System.out.println("HUE: clicked your tile");
				tile.setOn(!tile.isOn);
			} else {
				System.out.println("HUE: clicked NOT your tile");
				// TODO: control other people's lights
				// send a network message to the server, to send a message to whoever owns the block to send the hue command
				//TODO: control the server's lights? (how is that even set?)
			}
		} else {
			if (tile.owner != player.getUniqueID()) {
				tile.owner = player.getUniqueID();	
				System.out.println("HUE: Claimed tile");
			}
			
			List<PHLight> lights = MotMod.motHue.getLights();
			System.out.println("HUE: got # lights: "+lights.size());
			if (lights.size() > 0) {
				PHLight newLight;
				PHLight oldLight = null;
				int oldIndex = -1;
				
				for (PHLight light: lights) {
					if (tile.lightID == light.getIdentifier()) {
						oldLight = light;
						oldIndex = lights.indexOf(oldLight);
					}
				}
				
				newLight = lights.get((oldIndex+1) % (lights.size()));
				tile.lightID = newLight.getIdentifier();
				System.out.print("HUE: block set to "+tile.lightID);
			}
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

	@Override
	public BlockHueSink setCreativeTab(CreativeTabs tab) {
		super.setCreativeTab(tab);
		return this;
	}
	
	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
		HueSinkTileEntity tile = getTileEntity(worldIn, pos);
        if (!worldIn.isRemote)
        {
            if (tile.isOn && !worldIn.isBlockPowered(pos))
            {
                worldIn.scheduleUpdate(pos, this, 4);
            }
            else if (!tile.isOn && worldIn.isBlockPowered(pos))
            {
            	worldIn.scheduleUpdate(pos, this, 4);
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
    	HueSinkTileEntity tile = getTileEntity(worldIn, pos);
        if (!worldIn.isRemote)
        {
            if (tile.isOn && !worldIn.isBlockPowered(pos))
            {
            	tile.setOn(false);
                worldIn.setBlockState(pos, state, 2);
            }
            else if (!tile.isOn && worldIn.isBlockPowered(pos))
            {
            	tile.setOn(true);
                worldIn.setBlockState(pos, state, 2);
            }

        }
    }	
}
