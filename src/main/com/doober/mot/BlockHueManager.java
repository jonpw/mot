package com.doober.mot;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHueManager extends BlockTileEntity<HueManagerTileEntity> {

	public static String name = "HueManager";
	
	public BlockHueManager() {
		super(Material.ROCK, name);
       }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		HueManagerTileEntity tile = getTileEntity(world, pos);
		System.out.println(tile.toString());
	    return true;
	}
	
	@Override
	public Class<HueManagerTileEntity> getTileEntityClass() {
		return HueManagerTileEntity.class;
	}
	
	@Nullable
	@Override
	public HueManagerTileEntity createTileEntity(World world, IBlockState state) {
		return new HueManagerTileEntity();
	}	
	
}
