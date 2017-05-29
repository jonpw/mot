package com.doober.mot;

import com.doober.mot.item.ModItems;
import com.doober.mot.manager.MotManager;
import com.doober.mot.proxy.CommonProxy;
import com.doober.mot.proxy.ModBlocks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MotMod.modId, name = MotMod.name, version = MotMod.version, acceptedMinecraftVersions = "[1.11.2]")
public class MotMod {

	public static final String modId = "mot";
	public static final String name = "Mot Mod";
	public static final String version = "1.0";

	@Mod.Instance(modId)
	public static MotMod instance;
	
	@SidedProxy(serverSide = "com.doober.mot.proxy.CommonProxy", clientSide = "com.doober.mot.proxy.ClientProxy")
	public static CommonProxy proxy;
	
	public static MotManager motManager;
		
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {			
		System.out.println(name + " is loading2!");
		
		ModBlocks.init();
		System.out.println("registered blocks");
		
		motManager = new MotManager("testPlayer");
      	ModItems.init();
      	
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}