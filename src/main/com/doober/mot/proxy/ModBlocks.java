package com.doober.mot.proxy;

import com.doober.mot.BlockMotManager;
import com.doober.mot.BlockMotRedSink;
import com.doober.mot.BlockMotRedSource;

public class ModBlocks {

	public static BlockMotRedSink blockMotRedSink;
	public static BlockMotRedSource blockRedSource;
	public static BlockMotManager blockMotManager;
	
	public static void init() {
		blockMotRedSink = new BlockMotRedSink();
		blockRedSource = new BlockMotRedSource();
		blockMotManager = new BlockMotManager();
	}
}
