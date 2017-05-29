package com.doober.mot.proxy;

import com.doober.mot.MotMod;
import com.doober.mot.network.ModGuiHandler;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {

	
	public void preInit(FMLPreInitializationEvent event) {

	}
	
	public void init(FMLInitializationEvent e) {
	    NetworkRegistry.INSTANCE.registerGuiHandler(MotMod.instance, new ModGuiHandler());
	}

	public void registerItemRenderer(Item item, int meta, String id) {

	}

	/*public String localize(String unlocalized, Object... args) {
		return I18n.translateToLocalFormatted(unlocalized, args);
	}*/

	public void registerRenderers() {
	}

	public void postInit(FMLPostInitializationEvent e) {
		// TODO Auto-generated method stub
		
	}

}