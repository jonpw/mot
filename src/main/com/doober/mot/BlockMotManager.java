package com.doober.mot;

import com.doober.mot.network.ModGuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMotManager extends Block implements ITileEntityProvider {

	public String name = "Manager";
	
	public BlockMotManager() {
		super(Material.ROCK);
        setUnlocalizedName(MotMod.modId+"."+name);
        setRegistryName(name);
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(MotManagerTileEntity.class, MotMod.modId+"_"+name);
        this.isBlockContainer = true;
       }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO Auto-generated method stub
		return new MotManagerTileEntity();
	}

	private MotManagerTileEntity getTE(IBlockAccess world, BlockPos pos) {
		return (MotManagerTileEntity) world.getTileEntity(pos);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (world.isRemote) {
	        player.openGui(MotMod.instance, ModGuiHandler.MANAGER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	
}
