package com.doober.mot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLever;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Random;

import com.doober.mot.manager.MotManager;

public class BlockMotRedSource extends Block {

	public String name = "redsource";
	public static final PropertyBool ISON = PropertyBool.create("ison");
		
	public BlockMotRedSource () {
		super(Material.ROCK);
		setUnlocalizedName(MotMod.modId + "."+name);
		setRegistryName(name);
		setTickRandomly(true);
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
        setDefaultState(blockState.getBaseState().withProperty(ISON, false));
	}

	@Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		MotManager.register(worldIn, pos);
		System.out.println("subbed");
	}

     @Override
	 public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	 {
    	 System.out.println("tick!");
		if (!worldIn.isRemote)
        {
            if (MotMod.motManager.newUpdate(worldIn, pos)) {
            	String update = MotMod.motManager.getUpdate(pos);
            	System.out.println("got:"+update);
            	if (update.contains("true")) {
                    worldIn.setBlockState(pos, state.withProperty(ISON, true), 3);
            	} else {
                    worldIn.setBlockState(pos, state.withProperty(ISON, false), 3);
            	}
                worldIn.notifyNeighborsOfStateChange(pos, this, true);
            }
        }
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            worldIn.scheduleUpdate(pos, this, 4);
        	return true;
        }
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return (Boolean) blockState.getProperties().get(ISON) ? 15 : 0;
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
    	System.out.println("check power");
        return (Boolean) blockState.getProperties().get(ISON) ? 15 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return meta == 1? getDefaultState().withProperty(ISON, true) : getDefaultState().withProperty(ISON, false);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (Boolean)state.getValue(ISON)? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ISON);
    }

}
