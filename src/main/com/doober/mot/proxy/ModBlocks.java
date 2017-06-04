package com.doober.mot.proxy;

import com.doober.mot.BlockBase;
import com.doober.mot.BlockMotManager;
import com.doober.mot.BlockMotRedSink;
import com.doober.mot.BlockMotRedSource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

	public static BlockMotRedSink blockMotRedSink;
	public static BlockMotRedSource blockRedSource;
	public static BlockMotManager blockMotManager;
	//public static BlockHueManager blockHueManager;
	
	public static void init() {
		blockMotRedSink = new BlockMotRedSink();
		blockRedSource = new BlockMotRedSource();
		blockMotManager = new BlockMotManager();
		//blockHueManager = new BlockHueManager();
	}
	
	private static <T extends Block> T register(T block, ItemBlock itemBlock) {
		GameRegistry.register(block);
		GameRegistry.register(itemBlock);

		if (block instanceof BlockBase) {
			((BlockBase)block).registerItemModel(itemBlock);
		}
		
		if (block instanceof BlockTileEntity) {
			GameRegistry.registerTileEntity(((BlockTileEntity<?>)block).getTileEntityClass(), block.getRegistryName().toString());
		}		

		return block;
	}

	private static <T extends Block> T register(T block) {
		ItemBlock itemBlock = new ItemBlock(block);
		itemBlock.setRegistryName(block.getRegistryName());
		return register(block, itemBlock);
	}
}
